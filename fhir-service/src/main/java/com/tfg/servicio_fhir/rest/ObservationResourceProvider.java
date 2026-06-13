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

import com.tfg.servicio_fhir.client.ExternalClient;
import com.tfg.servicio_fhir.client.FhirResourcePersister;
import com.tfg.servicio_fhir.documents.ObservationDocument;
import com.tfg.servicio_fhir.repositories.ObservationRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HAPI FHIR Plain Server — Observation Resource Provider
 *
 * Cubre tanto mediciones de laboratorio (LOINC) como observaciones
 * clínicas generales, signos vitales, etc.
 *
 * Operaciones soportadas:
 *   GET    /Observation/{id}
 *   GET    /Observation?patient=Patient/{id}
 *   GET    /Observation?encounter=Encounter/{id}
 *   GET    /Observation?code=...                (LOINC)
 *   GET    /Observation?category=...            (laboratory, vital-signs...)
 *   GET    /Observation?status=...
 *   POST   /Observation
 *   PUT    /Observation/{id}
 *   DELETE /Observation/{id}
 */
@Component
public class ObservationResourceProvider implements IResourceProvider {

	private final ExternalClient externalClient;
	 private final FhirResourcePersister persister;
    private final ObservationRepository observationRepository;
    
    @Autowired
    public ObservationResourceProvider(ObservationRepository observationRepository,
							    		ExternalClient externalClient, 
							            FhirResourcePersister persister) {
    	this.observationRepository = observationRepository;
    	this.externalClient = externalClient;
    	this.persister = persister;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Observation.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public Observation read(@IdParam IdType id) {
        ObservationDocument doc = observationRepository.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toFhir(doc);
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<Observation> searchAll() {
        return observationRepository.findAll().stream().map(this::toFhir).toList();
    }

    @Search
    public List<Observation> search(
            @OptionalParam(name = Observation.SP_PATIENT)   ReferenceParam patient,
            @OptionalParam(name = Observation.SP_ENCOUNTER) ReferenceParam encounter,
            @OptionalParam(name = Observation.SP_CODE)      TokenParam code,
            @OptionalParam(name = Observation.SP_CATEGORY)  TokenParam category,
            @OptionalParam(name = Observation.SP_STATUS)    TokenParam status) {

    	if (patient != null) {
            String patientId = patient.getIdPart();
            
            List<ObservationDocument> localDocs = observationRepository.findByPatientReference("Patient/" + patientId);
           
            if (!localDocs.isEmpty()) {
                return localDocs.stream().map(this::toFhir).toList();
            }
            String resourcePath = "Observation?patient_id=" + patientId;
            
            return externalClient.fetchResourceList(resourcePath, Observation.class)
                    .map(externalObservations -> {
                        for (Observation ob : externalObservations) {
                            try {
                                persister.persist(ob);
                            } catch (Exception e) {
                                e.printStackTrace(); 
                            }
                        }
                        return externalObservations;
                    })
                    .orElseGet(Collections::emptyList);
        }
        if (encounter != null) {
            return observationRepository
                    .findByEncounterReference("Encounter/" + encounter.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (code != null) {
            return observationRepository.findByLoincCode(code.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (category != null) {
            return observationRepository.findByCategory(category.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (status != null) {
            return observationRepository.findByStatus(status.getValue())
                    .stream().map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam Observation observation) {
        ObservationDocument saved = observationRepository.save(toDocument(observation));
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Observation", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Observation observation) {
        if (!observationRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        ObservationDocument doc = toDocument(observation);
        doc.setId(id.getIdPart());
        ObservationDocument saved = observationRepository.save(doc);
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Observation", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!observationRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        observationRepository.deleteById(id.getIdPart());
    }

    // ── CONVERSION ───────────────────────────────────────────────────────────

    private Observation toFhir(ObservationDocument doc) {
        Observation obs = new Observation();
        obs.setId(doc.getId());

        if (doc.getStatus() != null) {
            obs.setStatus(Observation.ObservationStatus.fromCode(doc.getStatus()));
        }
        if (doc.getSubject() != null) {
            obs.setSubject(new Reference((String) doc.getSubject().get("reference")));
        }
        if (doc.getEncounter() != null) {
            obs.setEncounter(new Reference((String) doc.getEncounter().get("reference")));
        }
        if (doc.getEffectiveDateTime() != null) {
            obs.setEffective(new DateTimeType(doc.getEffectiveDateTime()));
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
            obs.setCode(code);
        }
        if (doc.getValueQuantity() != null) {
            Quantity q = new Quantity();
            Object valueObj = doc.getValueQuantity().get("value");
            if (valueObj != null) {
                q.setValue(new java.math.BigDecimal(valueObj.toString()));
            }
            // -----------------------
            q.setUnit((String) doc.getValueQuantity().get("unit"));
            q.setSystem((String) doc.getValueQuantity().get("system"));
            q.setCode((String) doc.getValueQuantity().get("code"));
            obs.setValue(q);
        }
        if (doc.getValueString() != null) {
            obs.setValue(new StringType(doc.getValueString()));
        }
        if (doc.getCategory() != null) {
            for (Map<String, Object> cat : doc.getCategory()) {
                CodeableConcept catConcept = new CodeableConcept();
                Object codings = cat.get("coding");
                if (codings instanceof List<?> codingList) {
                    for (Object o : codingList) {
                        Map<?, ?> c = (Map<?, ?>) o;
                        catConcept.addCoding(new Coding()
                                .setSystem((String) c.get("system"))
                                .setCode((String) c.get("code")));
                    }
                }
                obs.addCategory(catConcept);
            }
        }
        return obs;
    }

    private ObservationDocument toDocument(Observation obs) {
        ObservationDocument doc = new ObservationDocument();
        doc.setId(obs.hasId() ? obs.getIdElement().getIdPart() : null);
        doc.setResourceType("Observation");

        if (obs.hasStatus()) doc.setStatus(obs.getStatus().toCode());
        if (obs.hasSubject()) doc.setSubject(Map.of("reference", obs.getSubject().getReference()));
        if (obs.hasEncounter()) doc.setEncounter(Map.of("reference", obs.getEncounter().getReference()));
        if (obs.hasEffectiveDateTimeType()) {
            doc.setEffectiveDateTime(obs.getEffectiveDateTimeType().getValueAsString());
        }
        if (obs.hasCode()) {
            Map<String, Object> code = new HashMap<>();
            code.put("coding", obs.getCode().getCoding().stream()
                    .map(c -> Map.<String, Object>of(
                            "system",  c.getSystem()  != null ? c.getSystem()  : "",
                            "code",    c.getCode()    != null ? c.getCode()    : "",
                            "display", c.getDisplay() != null ? c.getDisplay() : ""))
                    .toList());
            doc.setCode(code);
        }
        if (obs.hasValueQuantity()) {
            Map<String, Object> q = new HashMap<>();
            q.put("value",  obs.getValueQuantity().getValue());
            q.put("unit",   obs.getValueQuantity().getUnit());
            q.put("system", obs.getValueQuantity().getSystem());
            q.put("code",   obs.getValueQuantity().getCode());
            doc.setValueQuantity(q);
        }
        if (obs.hasValueStringType()) {
            doc.setValueString(obs.getValueStringType().getValue());
        }
        return doc;
    }
}