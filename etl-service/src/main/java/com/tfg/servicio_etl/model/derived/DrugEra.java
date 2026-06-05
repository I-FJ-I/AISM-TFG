package com.tfg.servicio_etl.model.derived;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - DRUG_ERA table
 *
 * A Drug Era is defined as a span of time when the Person is assumed
 * to be exposed to a particular active ingredient. Derived from
 * DRUG_EXPOSURE, rolled up to ingredient level.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DrugEra {

    private Long drugEraId;

    private Long personId;

    /** FK to ingredient-level drug concept (RxNorm Ingredient). */
    private Integer drugConceptId;

    private LocalDate drugEraStartDate;

    private LocalDate drugEraEndDate;

    private Integer drugExposureCount;

    /** Number of days between individual exposures (gap tolerance applied). */
    private Integer gapDays;
}
