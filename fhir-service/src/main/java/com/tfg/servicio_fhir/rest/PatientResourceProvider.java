package com.tfg.servicio_fhir.rest;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tfg.servicio_fhir.client.ExternalClient;
import com.tfg.servicio_fhir.client.FhirResourcePersister;
import com.tfg.servicio_fhir.documents.PatientDocument;
import com.tfg.servicio_fhir.repositories.PatientRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HAPI FHIR Plain Server — Patient Resource Provider
 *
 * Operaciones soportadas:
 *   GET  /Patient/{id}                  → read
 *   GET  /Patient?identifier=...        → search by identifier
 *   GET  /Patient?family=...            → search by family name
 *   GET  /Patient?birthdate=...         → search by birth date
 *   GET  /Patient?gender=...            → search by gender
 *   GET  /Patient                       → search all
 *   GET  /Patient/$sync-omop			 → retrieve OMOP records
 *   POST /Patient                       → create
 *   PUT  /Patient/{id}                  → update
 *   DELETE /Patient/{id}                → delete
 */
@Component
public class PatientResourceProvider implements IResourceProvider {

    private final PatientRepository patientRepository;
    private final FhirContext fhirContext;
    private final ObjectMapper objectMapper;
    private final ExternalClient externalClient;
    
    @Autowired
    public PatientResourceProvider(PatientRepository patientRepository, FhirContext fhirContext, ObjectMapper objectMapper, 
    		ExternalClient externalClient) {
		this.patientRepository = patientRepository;
		this.fhirContext = fhirContext;
		this.objectMapper = objectMapper;
		this.externalClient = externalClient;
	}

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public Patient read(@IdParam IdType id) {
        return patientRepository.findById(id.getIdPart())
                .map(this::toFhir)
                
                .orElseGet(() -> {
                    Patient externalResource = externalClient.fetchResource("Patient", id.getIdPart(), Patient.class)
                            .map(Patient.class::cast)
                            .orElseThrow(() -> new ResourceNotFoundException(id));
                    
                    try {
                        PatientDocument doc = toDocument(externalResource);
                        doc.setId(id.getIdPart());
                        PatientDocument saved = patientRepository.save(doc);
                    } catch (Exception e) {
                        e.printStackTrace(); 
                    }
                    
                    return externalResource;
                });
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<Patient> searchAll() {
    	return patientRepository.findAll().stream()
    			.map(this::toFhir)
    			.toList();
    }
    
    @Operation(name = "$sync-omop", idempotent = true)
    public Bundle forceOmopSync() {
    	
    	patientRepository.deleteAll();
        
        List<Patient> syncedPatients = externalClient.fetchResourceList("Patient?limit=200", Patient.class)
                .map(externalPatients -> {
                    for (Patient patient : externalPatients) {
                        try {
                        	PatientDocument doc = toDocument(patient);
                            //doc.setId(patient.getId());
                            patientRepository.save(doc);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return externalPatients;
                })
                .orElseGet(Collections::emptyList);

        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.COLLECTION);
        bundle.setTotal(syncedPatients.size());       

        for (Patient p : syncedPatients) {
            bundle.addEntry().setResource(p);
        }

        return bundle;
    }

    @Search
    public Patient searchById(
            @RequiredParam(name = Patient.SP_IDENTIFIER) String id) {

    	return patientRepository.findById(id)
                .map(this::toFhir)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Search
    public List<Patient> searchByFamilyName(
            @OptionalParam(name = Patient.SP_FAMILY) StringParam family,
            @OptionalParam(name = Patient.SP_BIRTHDATE) StringParam birthDate,
            @OptionalParam(name = Patient.SP_GENDER) StringParam gender) {

        if (family != null) {
            return patientRepository.findByFamilyName(family.getValue()).stream()
                    .map(this::toFhir).toList();
        }
        if (birthDate != null) {
            return patientRepository.findByBirthDate(birthDate.getValue()).stream()
                    .map(this::toFhir).toList();
        }
        if (gender != null) {
            return patientRepository.findByGender(gender.getValue()).stream()
                    .map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam Patient patient) {
        PatientDocument doc = toDocument(patient);
        PatientDocument saved = patientRepository.save(doc);

        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Patient", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Patient patient) {
        if (!patientRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }

        PatientDocument doc = toDocument(patient);
        doc.setId(id.getIdPart());
        PatientDocument saved = patientRepository.save(doc);

        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Patient", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!patientRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        patientRepository.deleteById(id.getIdPart());
    }

    private Patient toFhir(PatientDocument doc) {
        // Llamamos directamente a nuestra función manual, sin usar el parseador JSON
        return buildPatientFromDoc(doc);
    }

    private Patient buildPatientFromDoc(PatientDocument doc) {
        Patient patient = new Patient();
        patient.setId(doc.getId());

        if (doc.getGender() != null) {
            patient.setGender(Enumerations.AdministrativeGender.fromCode(doc.getGender()));
        }
        if (doc.getBirthDate() != null) {
            try {
                patient.setBirthDateElement(new DateType(doc.getBirthDate()));
            } catch (Exception e) {
                // Ignorar error de formato de fecha
            }
        }
        if (doc.getActive() != null) {
            patient.setActive(doc.getActive());
        }
        if (doc.getDeceasedDateTime() != null) {
            patient.setDeceased(new DateTimeType(doc.getDeceasedDateTime()));
        }
        
        if (doc.getIdentifier() != null) {
            for (Map<String, Object> idMap : doc.getIdentifier()) {
                Identifier identifier = new Identifier();
                if (idMap.containsKey("system")) identifier.setSystem((String) idMap.get("system"));
                if (idMap.containsKey("value")) identifier.setValue((String) idMap.get("value"));
                patient.addIdentifier(identifier);
            }
        }
        
        //System.out.println(doc.getExtension());

        if (doc.getExtension() != null) {
            for (Map<String, Object> extMap : doc.getExtension()) {
                Extension ext = new Extension();
                
                if (extMap.containsKey("url")) {
                    ext.setUrl((String) extMap.get("url"));
                }

                if (extMap.containsKey("extension")) {
                    List<Map<String, Object>> subExts = (List<Map<String, Object>>) extMap.get("extension");
                    
                    for (Map<String, Object> sub : subExts) {
                        Extension subExt = new Extension();
                        if (sub.containsKey("url")) {
                            subExt.setUrl((String) sub.get("url"));
                        }

                        // 1. Comprobamos si es un texto simple (valueString)
                        if (sub.containsKey("valueString") && sub.get("valueString") != null) {
                            subExt.setValue(new StringType((String) sub.get("valueString")));
                        } 
                        // 2. Comprobamos si es un código complejo (valueCoding)
                        else if (sub.containsKey("valueCoding") && sub.get("valueCoding") != null) {
                            Map<String, Object> codingMap = (Map<String, Object>) sub.get("valueCoding");
                            Coding coding = new Coding();
                            
                            if (codingMap.containsKey("system")) coding.setSystem((String) codingMap.get("system"));
                            if (codingMap.containsKey("code")) coding.setCode((String) codingMap.get("code"));
                            if (codingMap.containsKey("display")) coding.setDisplay((String) codingMap.get("display"));
                            
                            subExt.setValue(coding);
                        }

                        // Solo añadimos la sub-extensión si realmente le hemos puesto un valor
                        if (subExt.getValue() != null) {
                            ext.addExtension(subExt);
                        }
                    }
                }
                
                // Añadimos la extensión principal al paciente
                patient.addExtension(ext);
            }
        }

        if (doc.getName() != null) {
            for (Map<String, Object> nameMap : doc.getName()) {
                HumanName humanName = new HumanName();
                
                if (nameMap.containsKey("family")) {
                    humanName.setFamily((String) nameMap.get("family"));
                }
                
                if (nameMap.containsKey("given")) {
                    Object givenObj = nameMap.get("given");
                    
                    if (givenObj instanceof List) {
                        List<String> givens = (List<String>) givenObj;
                        for (String given : givens) {
                            humanName.addGiven(given);
                        }
                    } else if (givenObj instanceof String) {
                        humanName.addGiven((String) givenObj);
                    }
                }
                patient.addName(humanName);
            }
        }

        return patient;
    }
    
    private PatientDocument toDocument(Patient patient) {
        try {
            String jsonFhir = fhirContext.newJsonParser().encodeResourceToString(patient);

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            PatientDocument doc = objectMapper.readValue(jsonFhir, PatientDocument.class);

            if (patient.hasId()) {
                doc.setId(patient.getIdElement().getIdPart());
            }

            return doc;

        } catch (Exception e) {
            throw new RuntimeException("Error interno al procesar el recurso FHIR", e);
        }
    }
}
