package com.tfg.servicio_etl.model.derived;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - COHORT_DEFINITION table
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CohortDefinition {

    private Long cohortDefinitionId;

    private String cohortDefinitionName;

    private String cohortDefinitionDescription;

    /** FK to the concept describing the cohort definition type. */
    private Integer definitionTypeConceptId;

    private String cohortDefinitionSyntax;

    private Integer subjectConceptId;

    private LocalDate cohortInitiationDate;
}