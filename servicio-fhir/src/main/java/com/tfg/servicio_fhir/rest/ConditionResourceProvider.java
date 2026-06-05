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

import com.tfg.servicio_fhir.documents.ConditionDocument;
import com.tfg.servicio_fhir.repositories.ConditionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HAPI FHIR Plain Server — Condition Resource Provider
 *
 * Operaciones soportadas:
 *   GET    /Condition/{id}
 *   GET    /Condition?patient=Patient/{id}
 *   GET    /Condition?encounter=Encounter/{id}
 *   GET    /Condition?code=...
 *   GET    /Condition?clinical-status=...
 *   POST   /Condition
 *   PUT    /Condition/{id}
 *   DELETE /Condition/{id}
 */
@Component
public class ConditionResourceProvider implements IResourceProvider {

    private final ConditionRepository conditionRepository;
    
    public ConditionResourceProvider(ConditionRepository conditionRepository) {
    	this.conditionRepository = conditionRepository;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Condition.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public Condition read(@IdParam IdType id) {
        ConditionDocument doc = conditionRepository.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toFhir(doc);
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<Condition> searchAll() {
        return conditionRepository.findAll().stream().map(this::toFhir).toList();
    }

    @Search
    public List<Condition> search(
            @OptionalParam(name = Condition.SP_PATIENT)          ReferenceParam patient,
            @OptionalParam(name = Condition.SP_ENCOUNTER)        ReferenceParam encounter,
            @OptionalParam(name = Condition.SP_CODE)             TokenParam code,
            @OptionalParam(name = Condition.SP_CLINICAL_STATUS)  TokenParam clinicalStatus) {

        if (patient != null) {
            return conditionRepository.findByPatientReference("Patient/" + patient.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (encounter != null) {
            return conditionRepository.findByEncounterReference("Encounter/" + encounter.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (code != null) {
            return conditionRepository.findByCode(code.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (clinicalStatus != null) {
            return conditionRepository.findByClinicalStatus(clinicalStatus.getValue())
                    .stream().map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam Condition condition) {
        ConditionDocument saved = conditionRepository.save(toDocument(condition));
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Condition", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Condition condition) {
        if (!conditionRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        ConditionDocument doc = toDocument(condition);
        doc.setId(id.getIdPart());
        ConditionDocument saved = conditionRepository.save(doc);
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Condition", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!conditionRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        conditionRepository.deleteById(id.getIdPart());
    }

    // ── CONVERSION ───────────────────────────────────────────────────────────

    private Condition toFhir(ConditionDocument doc) {
        Condition condition = new Condition();
        condition.setId(doc.getId());

        if (doc.getSubject() != null) {
            condition.setSubject(new Reference((String) doc.getSubject().get("reference")));
        }
        if (doc.getEncounter() != null) {
            condition.setEncounter(new Reference((String) doc.getEncounter().get("reference")));
        }
        if (doc.getOnsetDateTime() != null) {
            condition.setOnset(new DateTimeType(doc.getOnsetDateTime()));
        }
        if (doc.getAbatementDateTime() != null) {
            condition.setAbatement(new DateTimeType(doc.getAbatementDateTime()));
        }
        if (doc.getRecordedDate() != null) {
            condition.setRecordedDateElement(new DateTimeType(doc.getRecordedDate()));
        }
        if (doc.getCode() != null) {
            CodeableConcept code = new CodeableConcept();
            Object coding = doc.getCode().get("coding");
            if (coding instanceof List<?> codings && !codings.isEmpty()) {
                Map<?, ?> first = (Map<?, ?>) codings.get(0);
                Coding c = new Coding();
                c.setSystem((String) first.get("system"));
                c.setCode((String) first.get("code"));
                c.setDisplay((String) first.get("display"));
                code.addCoding(c);
            }
            condition.setCode(code);
        }
        if (doc.getClinicalStatus() != null) {
            CodeableConcept status = new CodeableConcept();
            status.addCoding(new Coding()
                    .setSystem("http://terminology.hl7.org/CodeSystem/condition-clinical")
                    .setCode((String) ((List<?>) doc.getClinicalStatus().get("coding")).stream()
                            .map(o -> ((Map<?, ?>) o).get("code")).findFirst().orElse(null)));
            condition.setClinicalStatus(status);
        }
        return condition;
    }

    private ConditionDocument toDocument(Condition condition) {
        ConditionDocument doc = new ConditionDocument();
        doc.setId(condition.hasId() ? condition.getIdElement().getIdPart() : null);
        doc.setResourceType("Condition");

        if (condition.hasSubject()) {
            doc.setSubject(Map.of("reference", condition.getSubject().getReference()));
        }
        if (condition.hasEncounter()) {
            doc.setEncounter(Map.of("reference", condition.getEncounter().getReference()));
        }
        if (condition.hasOnsetDateTimeType()) {
            doc.setOnsetDateTime(condition.getOnsetDateTimeType().getValueAsString());
        }
        if (condition.hasAbatementDateTimeType()) {
            doc.setAbatementDateTime(condition.getAbatementDateTimeType().getValueAsString());
        }
        if (condition.hasRecordedDate()) {
            doc.setRecordedDate(condition.getRecordedDateElement().getValueAsString());
        }
        if (condition.hasCode()) {
            Map<String, Object> code = new HashMap<>();
            List<Map<String, Object>> codings = condition.getCode().getCoding().stream()
                    .map(c -> Map.<String, Object>of(
                            "system", c.getSystem() != null ? c.getSystem() : "",
                            "code", c.getCode() != null ? c.getCode() : "",
                            "display", c.getDisplay() != null ? c.getDisplay() : ""))
                    .toList();
            code.put("coding", codings);
            doc.setCode(code);
        }
        if (condition.hasClinicalStatus()) {
            Map<String, Object> status = new HashMap<>();
            List<Map<String, Object>> codings = condition.getClinicalStatus().getCoding().stream()
                    .map(c -> Map.<String, Object>of("code", c.getCode() != null ? c.getCode() : ""))
                    .toList();
            status.put("coding", codings);
            doc.setClinicalStatus(status);
        }
        return doc;
    }
}