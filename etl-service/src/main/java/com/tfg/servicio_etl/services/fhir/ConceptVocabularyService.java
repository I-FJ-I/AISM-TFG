package com.tfg.servicio_etl.services.fhir;

import org.springframework.stereotype.Service;

@Service
public class ConceptVocabularyService {

    public Long getGenderConceptId(String genderText) {
        if ("M".equalsIgnoreCase(genderText)) return 8507L;
        if ("F".equalsIgnoreCase(genderText)) return 8532L;
        return 0L;
    }

    public Long getRaceConceptId(String raceText) {
        if ("white".equalsIgnoreCase(raceText)) return 8527L;
        if ("black".equalsIgnoreCase(raceText)) return 8516L;
        return 0L;
    }
}