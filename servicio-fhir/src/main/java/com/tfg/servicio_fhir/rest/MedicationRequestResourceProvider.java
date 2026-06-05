package com.tfg.servicio_fhir.rest;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Component;

import com.tfg.servicio_fhir.documents.MedicationRequestDocument;
import com.tfg.servicio_fhir.repositories.MedicationRequestRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HAPI FHIR Plain Server — MedicationRequest Resource Provider
 *
 * Operaciones soportadas:
 *   GET    /MedicationRequest/{id}
 *   GET    /MedicationRequest?patient=Patient/{id}
 *   GET    /MedicationRequest?encounter=Encounter/{id}
 *   GET    /MedicationRequest?status=...
 *   GET    /MedicationRequest?medication=...   (código RxNorm/NDC)
 *   POST   /MedicationRequest
 *   PUT    /MedicationRequest/{id}
 *   DELETE /MedicationRequest/{id}
 */
@Component
public class MedicationRequestResourceProvider implements IResourceProvider {

    private final MedicationRequestRepository medicationRequestRepository;
    
    public MedicationRequestResourceProvider(MedicationRequestRepository medicationRequestRepository) {
    	this.medicationRequestRepository = medicationRequestRepository;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return MedicationRequest.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public MedicationRequest read(@IdParam IdType id) {
        MedicationRequestDocument doc = medicationRequestRepository.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toFhir(doc);
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<MedicationRequest> searchAll() {
        return medicationRequestRepository.findAll().stream().map(this::toFhir).toList();
    }

    @Search
    public List<MedicationRequest> search(
            @OptionalParam(name = MedicationRequest.SP_PATIENT)   ReferenceParam patient,
            @OptionalParam(name = MedicationRequest.SP_ENCOUNTER) ReferenceParam encounter,
            @OptionalParam(name = MedicationRequest.SP_STATUS)    TokenParam status,
            @OptionalParam(name = MedicationRequest.SP_MEDICATION) TokenParam medication) {

        if (patient != null) {
            return medicationRequestRepository
                    .findByPatientReference("Patient/" + patient.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (encounter != null) {
            return medicationRequestRepository
                    .findByEncounterReference("Encounter/" + encounter.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (status != null) {
            return medicationRequestRepository.findByStatus(status.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (medication != null) {
            return medicationRequestRepository.findByMedicationCode(medication.getValue())
                    .stream().map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam MedicationRequest medicationRequest) {
        MedicationRequestDocument saved = medicationRequestRepository.save(toDocument(medicationRequest));
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("MedicationRequest", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam MedicationRequest medicationRequest) {
        if (!medicationRequestRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        MedicationRequestDocument doc = toDocument(medicationRequest);
        doc.setId(id.getIdPart());
        MedicationRequestDocument saved = medicationRequestRepository.save(doc);
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("MedicationRequest", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!medicationRequestRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        medicationRequestRepository.deleteById(id.getIdPart());
    }

    // ── CONVERSION ───────────────────────────────────────────────────────────

    private MedicationRequest toFhir(MedicationRequestDocument doc) {
        MedicationRequest mr = new MedicationRequest();
        mr.setId(doc.getId());

        if (doc.getStatus() != null) {
            mr.setStatus(MedicationRequest.MedicationRequestStatus.fromCode(doc.getStatus()));
        }
        if (doc.getIntent() != null) {
            mr.setIntent(MedicationRequest.MedicationRequestIntent.fromCode(doc.getIntent()));
        }
        if (doc.getSubject() != null) {
            mr.setSubject(new Reference((String) doc.getSubject().get("reference")));
        }
        if (doc.getEncounter() != null) {
            mr.setEncounter(new Reference((String) doc.getEncounter().get("reference")));
        }
        if (doc.getAuthoredOn() != null) {
            mr.setAuthoredOnElement(new DateTimeType(doc.getAuthoredOn()));
        }
        if (doc.getMedicationCodeableConcept() != null) {
            CodeableConcept medication = new CodeableConcept();
            Object codings = doc.getMedicationCodeableConcept().get("coding");
            if (codings instanceof List<?> codingList) {
                for (Object o : codingList) {
                    Map<?, ?> c = (Map<?, ?>) o;
                    medication.addCoding(new Coding()
                            .setSystem((String) c.get("system"))
                            .setCode((String) c.get("code"))
                            .setDisplay((String) c.get("display")));
                }
            }
            mr.setMedication(medication);
        }
        return mr;
    }

    private MedicationRequestDocument toDocument(MedicationRequest mr) {
        MedicationRequestDocument doc = new MedicationRequestDocument();
        doc.setId(mr.hasId() ? mr.getIdElement().getIdPart() : null);
        doc.setResourceType("MedicationRequest");

        if (mr.hasStatus()) {
            doc.setStatus(mr.getStatus().toCode());
        }
        if (mr.hasIntent()) {
            doc.setIntent(mr.getIntent().toCode());
        }
        if (mr.hasSubject()) {
            doc.setSubject(Map.of("reference", mr.getSubject().getReference()));
        }
        if (mr.hasEncounter()) {
            doc.setEncounter(Map.of("reference", mr.getEncounter().getReference()));
        }
        if (mr.hasAuthoredOn()) {
            doc.setAuthoredOn(mr.getAuthoredOnElement().getValueAsString());
        }
        if (mr.hasMedicationCodeableConcept()) {
            Map<String, Object> medication = new HashMap<>();
            List<Map<String, Object>> codings = mr.getMedicationCodeableConcept().getCoding().stream()
                    .map(c -> Map.<String, Object>of(
                            "system",  c.getSystem()  != null ? c.getSystem()  : "",
                            "code",    c.getCode()    != null ? c.getCode()    : "",
                            "display", c.getDisplay() != null ? c.getDisplay() : ""))
                    .toList();
            medication.put("coding", codings);
            doc.setMedicationCodeableConcept(medication);
        }
        return doc;
    }
}