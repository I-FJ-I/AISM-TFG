package com.tfg.servicio_etl.model.vocabulary;

import lombok.*;

/**
 * OMOP CDM v5.4 - VOCABULARY table
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vocabulary {

    private String vocabularyId;

    private String vocabularyName;

    private String vocabularyReference;

    private String vocabularyVersion;

    /** FK to CONCEPT for the vocabulary concept. */
    private Integer vocabularyConceptId;
}
