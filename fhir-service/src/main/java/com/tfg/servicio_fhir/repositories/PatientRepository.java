package com.tfg.servicio_fhir.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.servicio_fhir.documents.PatientDocument;

@Repository
public interface PatientRepository extends MongoRepository<PatientDocument, String> {

	Optional<PatientDocument> findById(String id);

    @Query("{ 'name.family': { $regex: ?0, $options: 'i' } }")
    List<PatientDocument> findByFamilyName(String familyName);

    List<PatientDocument> findByBirthDate(String birthDate);

    List<PatientDocument> findByGender(String gender);
}