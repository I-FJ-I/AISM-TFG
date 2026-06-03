package com.tfg.servicio_etl.model.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - CONCEPT_SYNONYM table
 * Contains alternative names and descriptions for Concepts.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConceptSynonym {

    private Integer conceptId;

    private String conceptSynonymName;

    private Integer languageConceptId;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @lombok.EqualsAndHashCode
class ConceptSynonymId implements java.io.Serializable {
    private Integer conceptId;
    private String conceptSynonymName;
    private Integer languageConceptId;
}
