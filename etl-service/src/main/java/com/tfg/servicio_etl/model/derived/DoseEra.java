package com.tfg.servicio_etl.model.derived;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - DOSE_ERA table
 *
 * A Dose Era is defined as a span of time when the Person is assumed
 * to be exposed to a constant dose of a specific active ingredient.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DoseEra {

    private Long doseEraId;

    private Long personId;

    private Integer drugConceptId;

    private Integer unitConceptId;

    private BigDecimal doseValue;

    private LocalDate doseEraStartDate;

    private LocalDate doseEraEndDate;
}
