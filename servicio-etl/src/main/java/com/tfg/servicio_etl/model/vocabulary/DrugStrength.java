package com.tfg.servicio_etl.model.vocabulary;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - DRUG_STRENGTH table
 * Contains structured content about the amount or concentration of
 * ingredient in a drug product.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DrugStrength {

    private Integer drugConceptId;

    private Integer ingredientConceptId;

    private BigDecimal amountValue;

    private Integer amountUnitConceptId;

    private BigDecimal numeratorValue;

    private Integer numeratorUnitConceptId;

    private BigDecimal denominatorValue;

    private Integer denominatorUnitConceptId;

    private Integer boxSize;

    private LocalDate validStartDate;

    private LocalDate validEndDate;

    private String invalidReason;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @lombok.EqualsAndHashCode
class DrugStrengthId implements java.io.Serializable {
    private Integer drugConceptId;
    private Integer ingredientConceptId;
}
