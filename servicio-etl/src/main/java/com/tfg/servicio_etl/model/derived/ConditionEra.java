package com.tfg.servicio_etl.model.derived;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - CONDITION_ERA table
 *
 * A Condition Era is defined as a span of time when the Person is
 * assumed to have a given condition. Condition Eras are periods of
 * continuous condition exposure. Derived from CONDITION_OCCURRENCE.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConditionEra {

    private Long conditionEraId;

    private Long personId;

    private Integer conditionConceptId;

    private LocalDate conditionEraStartDate;

    private LocalDate conditionEraEndDate;

    /** Number of individual CONDITION_OCCURRENCE records in this era. */
    private Integer conditionOccurrenceCount;
}
