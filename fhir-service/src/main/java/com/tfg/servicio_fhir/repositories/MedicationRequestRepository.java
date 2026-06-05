package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.MedicationRequestDocument;

@Repository
public interface MedicationRequestRepository extends MongoRepository<MedicationRequestDocument, String> {

    @Query("{ 'subject.reference': ?0 }")
    List<MedicationRequestDocument> findByPatientReference(String patientReference);

    @Query("{ 'encounter.reference': ?0 }")
    List<MedicationRequestDocument> findByEncounterReference(String encounterReference);

    List<MedicationRequestDocument> findByStatus(String status);

    @Query("{ 'medicationCodeableConcept.coding.code': ?0 }")
    List<MedicationRequestDocument> findByMedicationCode(String code);
}