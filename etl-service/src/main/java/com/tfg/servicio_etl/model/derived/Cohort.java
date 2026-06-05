package com.tfg.servicio_etl.model.derived;

import lombok.*;
import java.time.LocalDate;

/**
 * OMOP CDM v5.4 - COHORT table
 *
 * Contains records of subjects that satisfy a given set of criteria
 * for a duration of time. Subjects can be Persons, Providers, or
 * Visits.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cohort {

    private Long cohortDefinitionId;

    private Long subjectId;

    private LocalDate cohortStartDate;

    private LocalDate cohortEndDate;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @lombok.EqualsAndHashCode
class CohortId implements java.io.Serializable {
    private Long cohortDefinitionId;
    private Long subjectId;
    private LocalDate cohortStartDate;
    private LocalDate cohortEndDate;
}
