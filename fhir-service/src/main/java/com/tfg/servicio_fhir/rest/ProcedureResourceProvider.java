package com.tfg.servicio_fhir.rest;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tfg.servicio_fhir.documents.ProcedureDocument;
import com.tfg.servicio_fhir.repositories.ProcedureRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HAPI FHIR Plain Server — Procedure Resource Provider
 *
 * Operaciones soportadas:
 *   GET    /Procedure/{id}
 *   GET    /Procedure?patient=Patient/{id}
 *   GET    /Procedure?encounter=Encounter/{id}
 *   GET    /Procedure?code=...                  (SNOMED / CPT-4)
 *   GET    /Procedure?status=...
 *   POST   /Procedure
 *   PUT    /Procedure/{id}
 *   DELETE /Procedure/{id}
 */
@Component
public class ProcedureResourceProvider implements IResourceProvider {

    private final ProcedureRepository procedureRepository;
    
    @Autowired
    public ProcedureResourceProvider(ProcedureRepository procedureRepository) {
    	this.procedureRepository = procedureRepository;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Procedure.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public Procedure read(@IdParam IdType id) {
        ProcedureDocument doc = procedureRepository.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toFhir(doc);
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<Procedure> searchAll() {
        return procedureRepository.findAll().stream().map(this::toFhir).toList();
    }

    @Search
    public List<Procedure> search(
            @OptionalParam(name = Procedure.SP_PATIENT)   ReferenceParam patient,
            @OptionalParam(name = Procedure.SP_ENCOUNTER) ReferenceParam encounter,
            @OptionalParam(name = Procedure.SP_CODE)      TokenParam code,
            @OptionalParam(name = Procedure.SP_STATUS)    TokenParam status) {

        if (patient != null) {
            return procedureRepository.findByPatientReference("Patient/" + patient.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (encounter != null) {
            return procedureRepository.findByEncounterReference("Encounter/" + encounter.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (code != null) {
            return procedureRepository.findByCode(code.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (status != null) {
            return procedureRepository.findByStatus(status.getValue())
                    .stream().map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam Procedure procedure) {
        ProcedureDocument saved = procedureRepository.save(toDocument(procedure));
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Procedure", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Procedure procedure) {
        if (!procedureRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        ProcedureDocument doc = toDocument(procedure);
        doc.setId(id.getIdPart());
        ProcedureDocument saved = procedureRepository.save(doc);
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Procedure", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!procedureRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        procedureRepository.deleteById(id.getIdPart());
    }

    // ── CONVERSION ───────────────────────────────────────────────────────────

    private Procedure toFhir(ProcedureDocument doc) {
        Procedure procedure = new Procedure();
        procedure.setId(doc.getId());

        if (doc.getStatus() != null) {
            procedure.setStatus(Procedure.ProcedureStatus.fromCode(doc.getStatus()));
        }
        if (doc.getSubject() != null) {
            procedure.setSubject(new Reference((String) doc.getSubject().get("reference")));
        }
        if (doc.getEncounter() != null) {
            procedure.setEncounter(new Reference((String) doc.getEncounter().get("reference")));
        }
        if (doc.getPerformedDateTime() != null) {
            procedure.setPerformed(new DateTimeType(doc.getPerformedDateTime()));
        }
        if (doc.getPerformedPeriod() != null) {
            Period period = new Period();
            if (doc.getPerformedPeriod().get("start") != null) {
                period.setStartElement(new DateTimeType((String) doc.getPerformedPeriod().get("start")));
            }
            if (doc.getPerformedPeriod().get("end") != null) {
                period.setEndElement(new DateTimeType((String) doc.getPerformedPeriod().get("end")));
            }
            procedure.setPerformed(period);
        }
        if (doc.getCode() != null) {
            CodeableConcept code = new CodeableConcept();
            Object codings = doc.getCode().get("coding");
            if (codings instanceof List<?> codingList) {
                for (Object o : codingList) {
                    Map<?, ?> c = (Map<?, ?>) o;
                    code.addCoding(new Coding()
                            .setSystem((String) c.get("system"))
                            .setCode((String) c.get("code"))
                            .setDisplay((String) c.get("display")));
                }
            }
            procedure.setCode(code);
        }
        if (doc.getReasonCode() != null) {
            for (Map<String, Object> rc : doc.getReasonCode()) {
                CodeableConcept reason = new CodeableConcept();
                Object codings = rc.get("coding");
                if (codings instanceof List<?> codingList) {
                    for (Object o : codingList) {
                        Map<?, ?> c = (Map<?, ?>) o;
                        reason.addCoding(new Coding()
                                .setSystem((String) c.get("system"))
                                .setCode((String) c.get("code")));
                    }
                }
                procedure.addReasonCode(reason);
            }
        }
        return procedure;
    }

    private ProcedureDocument toDocument(Procedure procedure) {
        ProcedureDocument doc = new ProcedureDocument();
        doc.setId(procedure.hasId() ? procedure.getIdElement().getIdPart() : null);
        doc.setResourceType("Procedure");

        if (procedure.hasStatus()) doc.setStatus(procedure.getStatus().toCode());
        if (procedure.hasSubject()) doc.setSubject(Map.of("reference", procedure.getSubject().getReference()));
        if (procedure.hasEncounter()) doc.setEncounter(Map.of("reference", procedure.getEncounter().getReference()));
        if (procedure.hasPerformedDateTimeType()) {
            doc.setPerformedDateTime(procedure.getPerformedDateTimeType().getValueAsString());
        }
        if (procedure.hasPerformedPeriod()) {
            Map<String, Object> period = new HashMap<>();
            if (procedure.getPerformedPeriod().hasStart()) {
                period.put("start", procedure.getPerformedPeriod().getStartElement().getValueAsString());
            }
            if (procedure.getPerformedPeriod().hasEnd()) {
                period.put("end", procedure.getPerformedPeriod().getEndElement().getValueAsString());
            }
            doc.setPerformedPeriod(period);
        }
        if (procedure.hasCode()) {
            Map<String, Object> code = new HashMap<>();
            code.put("coding", procedure.getCode().getCoding().stream()
                    .map(c -> Map.<String, Object>of(
                            "system",  c.getSystem()  != null ? c.getSystem()  : "",
                            "code",    c.getCode()    != null ? c.getCode()    : "",
                            "display", c.getDisplay() != null ? c.getDisplay() : ""))
                    .toList());
            doc.setCode(code);
        }
        return doc;
    }
}