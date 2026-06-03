package com.tfg.servicio_etl.model.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - CONCEPT_ANCESTOR table
 * Contains a simplified hierarchical relationship between concepts.
 * Stores the distance from each concept to all its descendants and ancestors.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConceptAncestor {

    private Integer ancestorConceptId;

    private Integer descendantConceptId;

    private Integer minLevelsOfSeparation;

    private Integer maxLevelsOfSeparation;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @lombok.EqualsAndHashCode
class ConceptAncestorId implements java.io.Serializable {
    private Integer ancestorConceptId;
    private Integer descendantConceptId;
}
