package com.tfg.servicio_etl.model.vocabulary;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - SOURCE_TO_CONCEPT_MAP table
 * Contains non-standard, source-specific codes and their relationship to
 * standard concepts in the CONCEPT table.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SourceToConceptMap {

    private String sourceCode;

    private Integer sourceConceptId;

    private String sourceVocabularyId;

    private String sourceCodeDescription;

    private Integer targetConceptId;

    private String targetVocabularyId;

    private LocalDate validStartDate;

    private LocalDate validEndDate;

    private String invalidReason;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @lombok.EqualsAndHashCode
class SourceToConceptMapId implements java.io.Serializable {
    private String sourceCode;
    private Integer sourceConceptId;
    private String sourceVocabularyId;
    private Integer targetConceptId;
    private LocalDate validStartDate;
}

