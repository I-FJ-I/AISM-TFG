package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.EncounterDocument;

@Repository
public interface EncounterRepository extends MongoRepository<EncounterDocument, String> {

    @Query("{ 'subject.reference': ?0 }")
    List<EncounterDocument> findByPatientReference(String patientReference);

    List<EncounterDocument> findByStatus(String status);

    @Query("{ 'period.start': { $gte: ?0 }, 'period.end': { $lte: ?1 } }")
    List<EncounterDocument> findByPeriod(String start, String end);
}
