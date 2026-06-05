package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.ObservationDocument;

@Repository
public interface ObservationRepository extends MongoRepository<ObservationDocument, String> {

    @Query("{ 'subject.reference': ?0 }")
    List<ObservationDocument> findByPatientReference(String patientReference);

    @Query("{ 'encounter.reference': ?0 }")
    List<ObservationDocument> findByEncounterReference(String encounterReference);

    @Query("{ 'code.coding.code': ?0 }")
    List<ObservationDocument> findByLoincCode(String loincCode);

    @Query("{ 'category.coding.code': ?0 }")
    List<ObservationDocument> findByCategory(String category);

    List<ObservationDocument> findByStatus(String status);
}
