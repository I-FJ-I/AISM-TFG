package com.tfg.servicio_etl.model.vocabulary;

import lombok.*;
import java.time.LocalDate;

/**
 * OMOP CDM v5.4 - CONCEPT table
 *
 * Contains records that uniquely identify each concept and its
 * semantic value. Concepts are stored with a concept id, a name,
 * a domain, a vocabulary, a concept class and a standard concept flag.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Concept {

    private Integer conceptId;

    private String conceptName;

    /** FK to DOMAIN table. */
    private String domainId;

    /** FK to VOCABULARY table. */
    private String vocabularyId;

    /** FK to CONCEPT_CLASS table. */
    private String conceptClassId;

    /**
     * 'S' = Standard, 'C' = Classification, null = non-standard.
     * Only 'S' concepts should be used to populate CDM fields.
     */
    private String standardConcept;

    private String conceptCode;

    private LocalDate validStartDate;

    private LocalDate validEndDate;

    /** Reason if this concept was deprecated. */
    private String invalidReason;
}
