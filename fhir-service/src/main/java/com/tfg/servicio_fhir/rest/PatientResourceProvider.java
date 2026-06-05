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

import java.util.List;

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
    private final FhirResourcePersister persister;
    
    @Autowired
    public PatientResourceProvider(PatientRepository patientRepository, FhirContext fhirContext, ObjectMapper objectMapper, 
    		ExternalClient externalClient, FhirResourcePersister persister) {
		this.patientRepository = patientRepository;
		this.fhirContext = fhirContext;
		this.objectMapper = objectMapper;
		this.externalClient = externalClient;
		this.persister = persister;
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
                    
                    System.out.println(externalResource);
                    
                    try {
                        persister.persist(externalResource);
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

    @Search
    public List<Patient> searchByIdentifier(
            @RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam identifier) {

        return patientRepository.findByIdentifierValue(identifier.getValue()).stream()
                .map(this::toFhir)
                .toList();
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
        IParser parser = fhirContext.newJsonParser();
        String json = fhirContext.newJsonParser().encodeResourceToString(buildPatientFromDoc(doc));
        return (Patient) parser.parseResource(json);
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
            }
        }
        if (doc.getActive() != null) {
            patient.setActive(doc.getActive());
        }
        if (doc.getDeceasedDateTime() != null) {
            patient.setDeceased(new DateTimeType(doc.getDeceasedDateTime()));
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
