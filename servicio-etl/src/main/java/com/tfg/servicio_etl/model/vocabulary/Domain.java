package com.tfg.servicio_etl.model.vocabulary;

import lombok.*;

/**
 * OMOP CDM v5.4 - DOMAIN table
 * Sets the allowable concept_id values that can populate the DOMAIN_ID field.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Domain {

    private String domainId;

    private String domainName;

    private Integer domainConceptId;
}
