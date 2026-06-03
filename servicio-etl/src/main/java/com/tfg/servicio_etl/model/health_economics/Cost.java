package com.tfg.servicio_etl.model.health_economics;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - COST table
 *
 * Captures records containing the cost of any medical entity recorded
 * in one of the OMOP clinical event tables such as drug_exposure,
 * procedure_occurrence, visit_occurrence, etc.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cost {

    private Long costId;

    /** The unique identifier of the event (e.g., drug_exposure_id). */
    private Long costEventId;

    /** FK to the concept indicating the event domain (drug, visit, etc.). */
    private String costDomainId;

    /** FK to the type of cost record. */
    private Integer costTypeConceptId;

    /** FK to the currency concept (USD, EUR, etc.). */
    private Integer currencyConceptId;

    /** The actual cost of the service/product. */
    private BigDecimal totalCharge;

    private BigDecimal totalCost;

    /** Amount paid by payer. */
    private BigDecimal totalPaid;

    private BigDecimal paidByPayer;

    private BigDecimal paidByPatient;

    private BigDecimal paidPatientCopay;

    private BigDecimal paidPatientCoinsurance;

    private BigDecimal paidPatientDeductible;

    private BigDecimal paidByPrimary;

    private BigDecimal paidIngredientCost;

    private BigDecimal paidDispensingFee;

    private Long payerPlanPeriodId;

    private BigDecimal amountAllowed;

    private Integer revenueCodeConceptId;

    private String revenueCodeSourceValue;

    private Integer drgConceptId;

    private String drgSourceValue;
}
