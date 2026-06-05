package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.DeviceDocument;

@Repository
public interface DeviceRepository extends MongoRepository<DeviceDocument, String> {

    @Query("{ 'patient.reference': ?0 }")
    List<DeviceDocument> findByPatientReference(String patientReference);

    List<DeviceDocument> findByStatus(String status);

    @Query("{ 'udiCarrier.deviceIdentifier': ?0 }")
    List<DeviceDocument> findByUdi(String udi);

    @Query("{ 'type.coding.code': ?0 }")
    List<DeviceDocument> findByTypeCode(String typeCode);
}
