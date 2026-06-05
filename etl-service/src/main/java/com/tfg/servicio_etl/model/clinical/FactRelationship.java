package com.tfg.servicio_etl.model.clinical;

import lombok.*;

/**
 * OMOP CDM v5.4 - FACT_RELATIONSHIP table
 *
 * Contains records about the relationships between facts stored
 * as records in any table of the CDM. Relationships can be
 * defined between facts from the same domain or different domains.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FactRelationship {

    /** FK to the domain concept for the domain of the primary fact. */
    private Integer domainConceptId1;

    /** The unique identifier in the table of the primary fact. */
    private Long factId1;

    /** FK to the domain concept for the domain of the secondary fact. */
    private Integer domainConceptId2;

    /** The unique identifier in the table of the secondary fact. */
    private Long factId2;

    /** FK to the relationship concept describing the relationship between primary and secondary facts. */
    private Integer relationshipConceptId;
}
