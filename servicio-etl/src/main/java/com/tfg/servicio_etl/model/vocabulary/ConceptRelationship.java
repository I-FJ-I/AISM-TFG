package com.tfg.servicio_etl.model.vocabulary;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - CONCEPT_RELATIONSHIP table
 * Contains all semantic relationships between concepts.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConceptRelationship {

    private Integer conceptId1;

    private Integer conceptId2;

    private String relationshipId;

    private LocalDate validStartDate;

    private LocalDate validEndDate;

    private String invalidReason;
}


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @lombok.EqualsAndHashCode
class ConceptRelationshipId implements java.io.Serializable {
    private Integer conceptId1;
    private Integer conceptId2;
    private String relationshipId;
}
