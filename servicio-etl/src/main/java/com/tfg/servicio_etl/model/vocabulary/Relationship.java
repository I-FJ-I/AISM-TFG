package com.tfg.servicio_etl.model.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - RELATIONSHIP table
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Relationship {

    private String relationshipId;

    private String relationshipName;

    private String isHierarchical;

    private String definesAncestry;

    private String reverseRelationshipId;

    private Integer relationshipConceptId;
}
