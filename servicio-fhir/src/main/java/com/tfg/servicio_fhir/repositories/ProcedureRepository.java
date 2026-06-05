package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.ProcedureDocument;

@Repository
public interface ProcedureRepository extends MongoRepository<ProcedureDocument, String> {

    @Query("{ 'subject.reference': ?0 }")
    List<ProcedureDocument> findByPatientReference(String patientReference);

    @Query("{ 'encounter.reference': ?0 }")
    List<ProcedureDocument> findByEncounterReference(String encounterReference);

    @Query("{ 'code.coding.code': ?0 }")
    List<ProcedureDocument> findByCode(String code);

    List<ProcedureDocument> findByStatus(String status);
}
