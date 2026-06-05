package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.SpecimenDocument;

@Repository
public interface SpecimenRepository extends MongoRepository<SpecimenDocument, String> {

    @Query("{ 'subject.reference': ?0 }")
    List<SpecimenDocument> findByPatientReference(String patientReference);

    List<SpecimenDocument> findByStatus(String status);

    @Query("{ 'type.coding.code': ?0 }")
    List<SpecimenDocument> findByTypeCode(String typeCode);
}