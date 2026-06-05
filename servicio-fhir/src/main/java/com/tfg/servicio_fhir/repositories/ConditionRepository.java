package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.ConditionDocument;

@Repository
public interface ConditionRepository extends MongoRepository<ConditionDocument, String> {

    @Query("{ 'subject.reference': ?0 }")
    List<ConditionDocument> findByPatientReference(String patientReference);

    @Query("{ 'encounter.reference': ?0 }")
    List<ConditionDocument> findByEncounterReference(String encounterReference);

    @Query("{ 'code.coding.code': ?0 }")
    List<ConditionDocument> findByCode(String code);

    @Query("{ 'clinicalStatus.coding.code': ?0 }")
    List<ConditionDocument> findByClinicalStatus(String status);
}
