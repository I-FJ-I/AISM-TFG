package com.tfg.servicio_fhir.client;

import java.util.List;
import java.util.Map;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Component;

import com.tfg.servicio_fhir.repositories.*;

import com.tfg.servicio_fhir.documents.*;

/**
 * Persists a FHIR resource obtained from the external service into MongoDB.
 *
 * Acts as the write layer of the Cache-aside pattern: once the 
 * resource is retrieved from the external source, it is stored 
 * locally so that future requests can find it directly in MongoDB.
 */
@Component
public class MongoFhirResourcePersister implements FhirResourcePersister {

    private final PatientRepository           patientRepository;
    private final ConditionRepository         conditionRepository;
    private final ObservationRepository       observationRepository;

    
    
    public MongoFhirResourcePersister(PatientRepository patientRepository,
			ConditionRepository conditionRepository,
			ObservationRepository observationRepository) {
		super();
		this.patientRepository = patientRepository;
		this.conditionRepository = conditionRepository;
		this.observationRepository = observationRepository;
	}

    /**
     * Detects the FHIR resource type and delegates it to the corresponding 
     * persistence method.
     */
    public void persist(IBaseResource resource) {
    	if (resource instanceof Patient) {
    	    persistPatient((Patient) resource);
    	} else if (resource instanceof Condition) {
    	    persistCondition((Condition) resource);
    	} else if (resource instanceof Observation) {
    	    persistObservation((Observation) resource);
    	} 
    }

    // ── PATIENT ──────────────────────────────────────────────────────────────

    private void persistPatient(Patient patient) {
        PatientDocument doc = new PatientDocument();
    	doc.setId(patient.getIdElement().getIdPart());
    	doc.setResourceType("Patient");
    	doc.setGender(patient.hasGender() ? patient.getGender().toCode() : null);
    	doc.setBirthDate(patient.hasBirthDate()
                        ? patient.getBirthDateElement().getValueAsString() : null);
    	doc.setActive(patient.hasActive() ? patient.getActive() : null);
    	doc.setDeceasedDateTime(patient.hasDeceasedDateTimeType()
                        ? patient.getDeceasedDateTimeType().getValueAsString() : null);
    	doc.setIdentifier(convertIdentifiersToMap(patient.getIdentifier()));
        patientRepository.save(doc);
    }

    // ── CONDITION ─────────────────────────────────────────────────────────────

    private void persistCondition(Condition condition) {
        ConditionDocument doc = new ConditionDocument();
        doc.setId(condition.getIdElement().getIdPart());
        doc.setResourceType("Condition");
        if (condition.hasSubject()) {
            doc.setSubject(java.util.Map.of("reference", condition.getSubject().getReference()));
        }
        if (condition.hasOnsetDateTimeType()) {
            doc.setOnsetDateTime(condition.getOnsetDateTimeType().getValueAsString());
        }
        if (condition.hasCode()) {
            java.util.Map<String, Object> code = new java.util.HashMap<>();
            code.put("coding", condition.getCode().getCoding().stream()
                    .map(c -> java.util.Map.<String, Object>of(
                            "system",  c.getSystem()  != null ? c.getSystem()  : "",
                            "code",    c.getCode()    != null ? c.getCode()    : "",
                            "display", c.getDisplay() != null ? c.getDisplay() : ""))
                    .toList());
            doc.setCode(code);
        }
        conditionRepository.save(doc);
    }

    // ── OBSERVATION ───────────────────────────────────────────────────────────

    private void persistObservation(Observation obs) {
        ObservationDocument doc = new ObservationDocument();
        doc.setId(obs.getIdElement().getIdPart());
        doc.setResourceType("Observation");
        doc.setStatus(obs.hasStatus() ? obs.getStatus().toCode() : null);
        if (obs.hasSubject()) {
            doc.setSubject(java.util.Map.of("reference", obs.getSubject().getReference()));
        }
        if (obs.hasEffectiveDateTimeType()) {
            doc.setEffectiveDateTime(obs.getEffectiveDateTimeType().getValueAsString());
        }
        if (obs.hasValueQuantity()) {
            java.util.Map<String, Object> q = new java.util.HashMap<>();
            q.put("value",  obs.getValueQuantity().getValue());
            q.put("unit",   obs.getValueQuantity().getUnit());
            q.put("system", obs.getValueQuantity().getSystem());
            q.put("code",   obs.getValueQuantity().getCode());
            doc.setValueQuantity(q);
        }
        observationRepository.save(doc);
    }
    
    // ── UTILS ──────────────────────────────────────────────────────────────
    
    public List<Map<String, Object>> convertIdentifiersToMap(List<Identifier> identifiers) {
    if (identifiers == null) return List.of();
    
    return identifiers.stream()
        .map(id -> {
            Map<String, Object> map = new java.util.HashMap<>();
            if (id.hasSystem()) map.put("system", id.getSystem());
            if (id.hasValue()) map.put("value", id.getValue());
            if (id.hasUse()) map.put("use", id.getUse().toCode());
            return map;
        })
        .toList();
}
}