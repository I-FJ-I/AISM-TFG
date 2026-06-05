package com.tfg.servicio_fhir.client;

import java.util.List;
import java.util.Map;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Component;

import com.tfg.servicio_fhir.repositories.*;

import ca.uhn.fhir.context.FhirContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final EncounterRepository         encounterRepository;
    private final ConditionRepository         conditionRepository;
    private final MedicationRequestRepository medicationRequestRepository;
    private final ObservationRepository       observationRepository;
    private final ProcedureRepository         procedureRepository;
    private final DeviceRepository            deviceRepository;
    private final SpecimenRepository          specimenRepository;
    
    private final FhirContext				  fhirContext;

    
    
    public MongoFhirResourcePersister(PatientRepository patientRepository, EncounterRepository encounterRepository,
			ConditionRepository conditionRepository, MedicationRequestRepository medicationRequestRepository,
			ObservationRepository observationRepository, ProcedureRepository procedureRepository,
			DeviceRepository deviceRepository, SpecimenRepository specimenRepository, FhirContext fhirContext) {
		super();
		this.patientRepository = patientRepository;
		this.encounterRepository = encounterRepository;
		this.conditionRepository = conditionRepository;
		this.medicationRequestRepository = medicationRequestRepository;
		this.observationRepository = observationRepository;
		this.procedureRepository = procedureRepository;
		this.deviceRepository = deviceRepository;
		this.specimenRepository = specimenRepository;
		this.fhirContext = fhirContext;
	}

    /**
     * Detects the FHIR resource type and delegates it to the corresponding 
     * persistence method.
     */
    public void persist(IBaseResource resource) {
    	if (resource instanceof Patient) {
    	    persistPatient((Patient) resource);
    	} else if (resource instanceof Encounter) {
    	    persistEncounter((Encounter) resource);
    	} else if (resource instanceof Condition) {
    	    persistCondition((Condition) resource);
    	} else if (resource instanceof MedicationRequest) {
    	    persistMedicationRequest((MedicationRequest) resource);
    	} else if (resource instanceof Observation) {
    	    persistObservation((Observation) resource);
    	} else if (resource instanceof Procedure) {
    	    persistProcedure((Procedure) resource);
    	} else if (resource instanceof Device) {
    	    persistDevice((Device) resource);
    	} else if (resource instanceof Specimen) {
    	    persistSpecimen((Specimen) resource);
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

    // ── ENCOUNTER ─────────────────────────────────────────────────────────────

    private void persistEncounter(Encounter encounter) {
        EncounterDocument doc = new EncounterDocument();
        doc.setId(encounter.getIdElement().getIdPart());
        doc.setResourceType("Encounter");
        doc.setStatus(encounter.hasStatus() ? encounter.getStatus().toCode() : null);
        if (encounter.hasSubject()) {
            doc.setSubject(java.util.Map.of("reference", encounter.getSubject().getReference()));
        }
        if (encounter.hasPeriod()) {
            java.util.Map<String, Object> period = new java.util.HashMap<>();
            if (encounter.getPeriod().hasStart()) {
                period.put("start", encounter.getPeriod().getStartElement().getValueAsString());
            }
            if (encounter.getPeriod().hasEnd()) {
                period.put("end", encounter.getPeriod().getEndElement().getValueAsString());
            }
            doc.setPeriod(period);
        }
        encounterRepository.save(doc);
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

    // ── MEDICATION REQUEST ────────────────────────────────────────────────────

    private void persistMedicationRequest(MedicationRequest mr) {
        MedicationRequestDocument doc = new MedicationRequestDocument();
        doc.setId(mr.getIdElement().getIdPart());
        doc.setResourceType("MedicationRequest");
        doc.setStatus(mr.hasStatus() ? mr.getStatus().toCode() : null);
        doc.setIntent(mr.hasIntent() ? mr.getIntent().toCode() : null);
        if (mr.hasSubject()) {
            doc.setSubject(java.util.Map.of("reference", mr.getSubject().getReference()));
        }
        if (mr.hasAuthoredOn()) {
            doc.setAuthoredOn(mr.getAuthoredOnElement().getValueAsString());
        }
        medicationRequestRepository.save(doc);
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

    // ── PROCEDURE ─────────────────────────────────────────────────────────────

    private void persistProcedure(Procedure procedure) {
        ProcedureDocument doc = new ProcedureDocument();
        doc.setId(procedure.getIdElement().getIdPart());
        doc.setResourceType("Procedure");
        doc.setStatus(procedure.hasStatus() ? procedure.getStatus().toCode() : null);
        if (procedure.hasSubject()) {
            doc.setSubject(java.util.Map.of("reference", procedure.getSubject().getReference()));
        }
        if (procedure.hasPerformedDateTimeType()) {
            doc.setPerformedDateTime(procedure.getPerformedDateTimeType().getValueAsString());
        }
        procedureRepository.save(doc);
    }

    // ── DEVICE ────────────────────────────────────────────────────────────────

    private void persistDevice(Device device) {
        DeviceDocument doc = new DeviceDocument();
        doc.setId(device.getIdElement().getIdPart());
        doc.setResourceType("Device");
        doc.setStatus(device.hasStatus() ? device.getStatus().toCode() : null);
        if (device.hasPatient()) {
            doc.setPatient(java.util.Map.of("reference", device.getPatient().getReference()));
        }
        if (device.hasManufacturer()) doc.setManufacturer(device.getManufacturer());
        if (device.hasLotNumber())    doc.setLotNumber(device.getLotNumber());
        if (device.hasSerialNumber()) doc.setSerialNumber(device.getSerialNumber());
        deviceRepository.save(doc);
    }

    // ── SPECIMEN ──────────────────────────────────────────────────────────────

    private void persistSpecimen(Specimen specimen) {
        SpecimenDocument doc = new SpecimenDocument();
        doc.setId(specimen.getIdElement().getIdPart());
        doc.setResourceType("Specimen");
        doc.setStatus(specimen.hasStatus() ? specimen.getStatus().toCode() : null);
        if (specimen.hasSubject()) {
            doc.setSubject(java.util.Map.of("reference", specimen.getSubject().getReference()));
        }
        if (specimen.hasReceivedTime()) {
            doc.setReceivedTime(specimen.getReceivedTimeElement().getValueAsString());
        }
        specimenRepository.save(doc);
    }
    
    // ── UTILS ──────────────────────────────────────────────────────────────
    
    public List<Map<String, Object>> convertIdentifiersToMap(List<Identifier> identifiers) {
        try {
            String json = fhirContext.newJsonParser().encodeResourceToString(
                new Bundle().addEntry(new Bundle.BundleEntryComponent().setResource(new Patient().setIdentifier(identifiers)))
            );

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> bundleMap = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
            
            List<Map<String, Object>> entries = (List<Map<String, Object>>) bundleMap.get("entry");
            Map<String, Object> firstEntry = entries.get(0);
            Map<String, Object> resource = (Map<String, Object>) firstEntry.get("resource");
            
            return (List<Map<String, Object>>) resource.get("identifier");
        } catch (Exception e) {
            throw new RuntimeException("Error converting identifiers to map", e);
        }
    }
}