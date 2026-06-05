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

import com.tfg.servicio_fhir.documents.DeviceDocument;
import com.tfg.servicio_fhir.repositories.DeviceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HAPI FHIR Plain Server — Device Resource Provider
 *
 * Operaciones soportadas:
 *   GET    /Device/{id}
 *   GET    /Device?patient=Patient/{id}
 *   GET    /Device?status=...
 *   GET    /Device?type=...                     (SNOMED device type)
 *   GET    /Device?udi-di=...                   (Unique Device Identifier)
 *   POST   /Device
 *   PUT    /Device/{id}
 *   DELETE /Device/{id}
 */
@Component
public class DeviceResourceProvider implements IResourceProvider {

    private final DeviceRepository deviceRepository;
    
    @Autowired
    public DeviceResourceProvider(DeviceRepository deviceRepository) {
    	this.deviceRepository = deviceRepository;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Device.class;
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    @Read
    public Device read(@IdParam IdType id) {
        DeviceDocument doc = deviceRepository.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toFhir(doc);
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────

    @Search
    public List<Device> searchAll() {
        return deviceRepository.findAll().stream().map(this::toFhir).toList();
    }

    @Search
    public List<Device> search(
            @OptionalParam(name = Device.SP_PATIENT) ReferenceParam patient,
            @OptionalParam(name = Device.SP_STATUS)  TokenParam status,
            @OptionalParam(name = Device.SP_TYPE)    TokenParam type,
            @OptionalParam(name = Device.SP_UDI_DI)  TokenParam udiDi) {

        if (patient != null) {
            return deviceRepository.findByPatientReference("Patient/" + patient.getIdPart())
                    .stream().map(this::toFhir).toList();
        }
        if (status != null) {
            return deviceRepository.findByStatus(status.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (type != null) {
            return deviceRepository.findByTypeCode(type.getValue())
                    .stream().map(this::toFhir).toList();
        }
        if (udiDi != null) {
            return deviceRepository.findByUdi(udiDi.getValue())
                    .stream().map(this::toFhir).toList();
        }
        return searchAll();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Create
    public MethodOutcome create(@ResourceParam Device device) {
        DeviceDocument saved = deviceRepository.save(toDocument(device));
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Device", saved.getId()));
        outcome.setCreated(true);
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Device device) {
        if (!deviceRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        DeviceDocument doc = toDocument(device);
        doc.setId(id.getIdPart());
        DeviceDocument saved = deviceRepository.save(doc);
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Device", saved.getId()));
        outcome.setResource(toFhir(saved));
        return outcome;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Delete
    public void delete(@IdParam IdType id) {
        if (!deviceRepository.existsById(id.getIdPart())) {
            throw new ResourceNotFoundException(id);
        }
        deviceRepository.deleteById(id.getIdPart());
    }

    // ── CONVERSION ───────────────────────────────────────────────────────────

    private Device toFhir(DeviceDocument doc) {
        Device device = new Device();
        device.setId(doc.getId());

        if (doc.getStatus() != null) {
            device.setStatus(Device.FHIRDeviceStatus.fromCode(doc.getStatus()));
        }
        if (doc.getPatient() != null) {
            device.setPatient(new Reference((String) doc.getPatient().get("reference")));
        }
        if (doc.getManufacturer() != null) {
            device.setManufacturer(doc.getManufacturer());
        }
        if (doc.getLotNumber() != null) {
            device.setLotNumber(doc.getLotNumber());
        }
        if (doc.getSerialNumber() != null) {
            device.setSerialNumber(doc.getSerialNumber());
        }
        if (doc.getManufactureDate() != null) {
            device.setManufactureDateElement(new DateTimeType(doc.getManufactureDate()));
        }
        if (doc.getExpirationDate() != null) {
            device.setExpirationDateElement(new DateTimeType(doc.getExpirationDate()));
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
            device.setType(type);
        }
        if (doc.getUdiCarrier() != null) {
            Device.DeviceUdiCarrierComponent udi = new Device.DeviceUdiCarrierComponent();
            udi.setDeviceIdentifier((String) doc.getUdiCarrier().get("deviceIdentifier"));
            udi.setCarrierHRF((String) doc.getUdiCarrier().get("carrierHRF"));
            device.addUdiCarrier(udi);
        }
        return device;
    }

    private DeviceDocument toDocument(Device device) {
        DeviceDocument doc = new DeviceDocument();
        doc.setId(device.hasId() ? device.getIdElement().getIdPart() : null);
        doc.setResourceType("Device");

        if (device.hasStatus()) doc.setStatus(device.getStatus().toCode());
        if (device.hasPatient()) doc.setPatient(Map.of("reference", device.getPatient().getReference()));
        if (device.hasManufacturer()) doc.setManufacturer(device.getManufacturer());
        if (device.hasLotNumber()) doc.setLotNumber(device.getLotNumber());
        if (device.hasSerialNumber()) doc.setSerialNumber(device.getSerialNumber());
        if (device.hasManufactureDate()) {
            doc.setManufactureDate(device.getManufactureDateElement().getValueAsString());
        }
        if (device.hasExpirationDate()) {
            doc.setExpirationDate(device.getExpirationDateElement().getValueAsString());
        }
        if (device.hasType()) {
            Map<String, Object> type = new HashMap<>();
            type.put("coding", device.getType().getCoding().stream()
                    .map(c -> Map.<String, Object>of(
                            "system",  c.getSystem()  != null ? c.getSystem()  : "",
                            "code",    c.getCode()    != null ? c.getCode()    : "",
                            "display", c.getDisplay() != null ? c.getDisplay() : ""))
                    .toList());
            doc.setType(type);
        }
        if (device.hasUdiCarrier() && !device.getUdiCarrier().isEmpty()) {
            Device.DeviceUdiCarrierComponent udi = device.getUdiCarrierFirstRep();
            Map<String, Object> udiMap = new HashMap<>();
            udiMap.put("deviceIdentifier", udi.getDeviceIdentifier());
            udiMap.put("carrierHRF", udi.getCarrierHRF());
            doc.setUdiCarrier(udiMap);
        }
        return doc;
    }
}