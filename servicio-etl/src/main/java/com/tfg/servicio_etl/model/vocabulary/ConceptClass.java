package com.tfg.servicio_etl.model.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - CONCEPT_CLASS table
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConceptClass {

    private String conceptClassId;

    private String conceptClassName;

    private Integer conceptClassConceptId;
}
