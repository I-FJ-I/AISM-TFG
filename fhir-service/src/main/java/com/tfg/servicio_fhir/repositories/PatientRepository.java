package com.tfg.servicio_fhir.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.PatientDocument;

@Repository
public interface PatientRepository extends MongoRepository<PatientDocument, String> {

    @Query("{ 'identifier.value': ?0 }")
    List<PatientDocument> findByIdentifierValue(String value);

    @Query("{ 'name.family': { $regex: ?0, $options: 'i' } }")
    List<PatientDocument> findByFamilyName(String familyName);

    List<PatientDocument> findByBirthDate(String birthDate);

    List<PatientDocument> findByGender(String gender);
}