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

import com.tfg.servicio_fhir.documents.SpecimenDocument;
import com.tfg.servicio_fhir.repositories.SpecimenRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HAPI FHIR Plain Server — Specimen Resource Provider
 *
 * Operaciones soportadas:
 *   GET    /Specimen/{id}
 *   GET    /Specimen?patient=Patient/{id}
 *   GET    /Specimen?status=...
 *   GET    /Specimen?type=...                   (SNOMED specimen type)
 *   POST   /Specimen
 *   PUT    /Specimen/{id}
 *   DELETE /Specimen/{id}
 */
@Component
public class SpecimenResourceProvider implements IResourceProvider {

    private final SpecimenRepository specimenRepository;
    
    @Autowired
    public SpecimenResourceProvider(SpecimenRepository specimenRepository) {
    	this.specimenRepository = specimenRepository;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Specimen.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public Specimen read(@IdParam IdType id) {
        SpecimenDocument doc = specimenRepository.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toFhir(doc);
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<Specimen> searchAll() {
        return specimenRepository.findAll().stream().map(this::toFhir).toList();
    }

    @Search
    public List<Specimen> search(
            @OptionalParam(name = Specimen.SP_PATIENT) ReferenceParam patient,
            @OptionalParam(name = Specimen.SP_STATUS)  TokenParam status,
            @OptionalParam(name = Specimen.SP_TYPE)    TokenParam type) {

        if (patient != null) {
            return specimenRepository.findByPatientReference("Patient/" + patient.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (status != null) {
            return specimenRepository.findByStatus(status.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (type != null) {
            return specimenRepository.findByTypeCode(type.getValue())
                    .stream().map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam Specimen specimen) {
        SpecimenDocument saved = specimenRepository.save(toDocument(specimen));
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Specimen", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Specimen specimen) {
        if (!specimenRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        SpecimenDocument doc = toDocument(specimen);
        doc.setId(id.getIdPart());
        SpecimenDocument saved = specimenRepository.save(doc);
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Specimen", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!specimenRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        specimenRepository.deleteById(id.getIdPart());
    }

    // ── CONVERSION ───────────────────────────────────────────────────────────

    private Specimen toFhir(SpecimenDocument doc) {
        Specimen specimen = new Specimen();
        specimen.setId(doc.getId());

        if (doc.getStatus() != null) {
            specimen.setStatus(Specimen.SpecimenStatus.fromCode(doc.getStatus()));
        }
        if (doc.getSubject() != null) {
            specimen.setSubject(new Reference((String) doc.getSubject().get("reference")));
        }
        if (doc.getReceivedTime() != null) {
            specimen.setReceivedTimeElement(new DateTimeType(doc.getReceivedTime()));
        }
        if (doc.getType() != null) {
            CodeableConcept type = new CodeableConcept();
            Object codings = doc.getType().get("coding");
            if (codings instanceof List<?> codingList) {
                for (Object o : codingList) {
                    Map<?, ?> c = (Map<?, ?>) o;
                    type.addCoding(new Coding()
                            .setSystem((String) c.get("system"))
                            .setCode((String) c.get("code"))
                            .setDisplay((String) c.get("display")));
                }
            }
            specimen.setType(type);
        }
        if (doc.getCollection() != null) {
            Specimen.SpecimenCollectionComponent collection = new Specimen.SpecimenCollectionComponent();
            String collectedDateTime = (String) doc.getCollection().get("collectedDateTime");
            if (collectedDateTime != null) {
                collection.setCollected(new DateTimeType(collectedDateTime));
            }
            String collector = (String) doc.getCollection().get("collector");
            if (collector != null) {
                collection.setCollector(new Reference(collector));
            }
            Map<?, ?> bodySite = (Map<?, ?>) doc.getCollection().get("bodySite");
            if (bodySite != null) {
                CodeableConcept site = new CodeableConcept();
                site.addCoding(new Coding()
                        .setSystem((String) bodySite.get("system"))
                        .setCode((String) bodySite.get("code"))
                        .setDisplay((String) bodySite.get("display")));
                collection.setBodySite(site);
            }
            specimen.setCollection(collection);
        }
        return specimen;
    }

    private SpecimenDocument toDocument(Specimen specimen) {
        SpecimenDocument doc = new SpecimenDocument();
        doc.setId(specimen.hasId() ? specimen.getIdElement().getIdPart() : null);
        doc.setResourceType("Specimen");

        if (specimen.hasStatus()) doc.setStatus(specimen.getStatus().toCode());
        if (specimen.hasSubject()) doc.setSubject(Map.of("reference", specimen.getSubject().getReference()));
        if (specimen.hasReceivedTime()) {
            doc.setReceivedTime(specimen.getReceivedTimeElement().getValueAsString());
        }
        if (specimen.hasType()) {
            Map<String, Object> type = new HashMap<>();
            type.put("coding", specimen.getType().getCoding().stream()
                    .map(c -> Map.<String, Object>of(
                            "system",  c.getSystem()  != null ? c.getSystem()  : "",
                            "code",    c.getCode()    != null ? c.getCode()    : "",
                            "display", c.getDisplay() != null ? c.getDisplay() : ""))
                    .toList());
            doc.setType(type);
        }
        if (specimen.hasCollection()) {
            Map<String, Object> collection = new HashMap<>();
            Specimen.SpecimenCollectionComponent col = specimen.getCollection();
            if (col.hasCollectedDateTimeType()) {
                collection.put("collectedDateTime", col.getCollectedDateTimeType().getValueAsString());
            }
            if (col.hasCollector()) {
                collection.put("collector", col.getCollector().getReference());
            }
            if (col.hasBodySite() && col.getBodySite().hasCoding()) {
                Coding bs = col.getBodySite().getCodingFirstRep();
                collection.put("bodySite", Map.of(
                        "system",  bs.getSystem()  != null ? bs.getSystem()  : "",
                        "code",    bs.getCode()    != null ? bs.getCode()    : "",
                        "display", bs.getDisplay() != null ? bs.getDisplay() : ""));
            }
            doc.setCollection(collection);
        }
        return doc;
    }
}