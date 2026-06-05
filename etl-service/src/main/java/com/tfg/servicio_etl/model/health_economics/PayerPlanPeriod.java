package com.tfg.servicio_etl.model.health_economics;

import lombok.*;
import java.time.LocalDate;

/**
 * OMOP CDM v5.4 - PAYER_PLAN_PERIOD table
 *
 * Contains the span of time when a Person is continuously enrolled
 * under a specific health Plan offered by a specific Payer.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PayerPlanPeriod {

    private Long payerPlanPeriodId;

    private Long personId;

    private LocalDate payerPlanPeriodStartDate;

    private LocalDate payerPlanPeriodEndDate;

    private Integer payerConceptId;

    private String payerSourceValue;

    private Integer payerSourceConceptId;

    private Integer planConceptId;

    private String planSourceValue;

    private Integer planSourceConceptId;

    private Integer sponsorConceptId;

    private String sponsorSourceValue;

    private Integer sponsorSourceConceptId;

    private String familySourceValue;

    private Integer stopReasonConceptId;

    private String stopReasonSourceValue;

    private Integer stopReasonSourceConceptId;
}
