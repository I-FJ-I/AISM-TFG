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

import com.tfg.servicio_fhir.documents.EncounterDocument;
import com.tfg.servicio_fhir.repositories.EncounterRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HAPI FHIR Plain Server — Encounter Resource Provider
 *
 * Operaciones soportadas:
 *   GET  /Encounter/{id}
 *   GET  /Encounter?patient=Patient/{id}
 *   GET  /Encounter?status=...
 *   GET  /Encounter?date=...
 *   POST /Encounter
 *   PUT  /Encounter/{id}
 *   DELETE /Encounter/{id}
 */
@Component
public class EncounterResourceProvider implements IResourceProvider {

    private final EncounterRepository encounterRepository;
    
    @Autowired
    public EncounterResourceProvider(EncounterRepository encounterRepository) {
    	this.encounterRepository = encounterRepository;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Encounter.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public Encounter read(@IdParam IdType id) {

        EncounterDocument doc = encounterRepository.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return toFhir(doc);
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<Encounter> searchAll() {
        return encounterRepository.findAll().stream()
                .map(this::toFhir)
                .toList();
    }

    @Search
    public List<Encounter> searchByPatient(
            @OptionalParam(name = Encounter.SP_PATIENT) ReferenceParam patient,
            @OptionalParam(name = Encounter.SP_STATUS)  TokenParam status) {

        if (patient != null) {
            String ref = "Patient/" + patient.getIdPart();
            return encounterRepository.findByPatientReference(ref).stream()
                    .map(this::toFhir).toList();
        }
        if (status != null) {
            return encounterRepository.findByStatus(status.getValue()).stream()
                    .map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam Encounter encounter) {
        EncounterDocument doc = toDocument(encounter);
        EncounterDocument saved = encounterRepository.save(doc);

        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Encounter", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Encounter encounter) {
        if (!encounterRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        EncounterDocument doc = toDocument(encounter);
        doc.setId(id.getIdPart());
        EncounterDocument saved = encounterRepository.save(doc);

        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Encounter", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!encounterRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        encounterRepository.deleteById(id.getIdPart());
    }

    // ── CONVERSION ───────────────────────────────────────────────────────────

    private Encounter toFhir(EncounterDocument doc) {
        Encounter encounter = new Encounter();
        encounter.setId(doc.getId());

        if (doc.getStatus() != null) {
            encounter.setStatus(Encounter.EncounterStatus.fromCode(doc.getStatus()));
        }
        if (doc.getSubject() != null) {
            String ref = (String) doc.getSubject().get("reference");
            if (ref != null) encounter.setSubject(new Reference(ref));
        }
        if (doc.getPeriod() != null) {
            Period period = new Period();
            if (doc.getPeriod().get("start") != null) {
                period.setStartElement(new DateTimeType((String) doc.getPeriod().get("start")));
            }
            if (doc.getPeriod().get("end") != null) {
                period.setEndElement(new DateTimeType((String) doc.getPeriod().get("end")));
            }
            encounter.setPeriod(period);
        }

        return encounter;
    }

    private EncounterDocument toDocument(Encounter encounter) {
        EncounterDocument doc = new EncounterDocument();
        doc.setId(encounter.hasId() ? encounter.getIdElement().getIdPart() : null);
        doc.setResourceType("Encounter");
        doc.setStatus(encounter.hasStatus() ? encounter.getStatus().toCode() : null);

        if (encounter.hasSubject()) {
            doc.setSubject(Map.of("reference", encounter.getSubject().getReference()));
        }
        if (encounter.hasPeriod()) {
            Map<String, Object> period = new HashMap<>();
            if (encounter.getPeriod().hasStart()) {
                period.put("start", encounter.getPeriod().getStartElement().getValueAsString());
            }
            if (encounter.getPeriod().hasEnd()) {
                period.put("end", encounter.getPeriod().getEndElement().getValueAsString());
            }
            doc.setPeriod(period);
        }
        return doc;
    }
}