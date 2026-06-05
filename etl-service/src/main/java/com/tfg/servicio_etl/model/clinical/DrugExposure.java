package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - DRUG_EXPOSURE table
 *
 * Records the exposure to a Drug ingested or otherwise introduced
 * into the body. Drugs include prescription and over-the-counter
 * medicines, vaccines, and large-molecule biologic therapies.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DrugExposure {

    private Long drugExposureId;

    private Long personId;

    /** FK to standard drug concept (RxNorm). */
    private Integer drugConceptId;

    private LocalDate drugExposureStartDate;

    private LocalDateTime drugExposureStartDatetime;

    private LocalDate drugExposureEndDate;

    private LocalDateTime drugExposureEndDatetime;

    private LocalDate verbatimEndDate;

    /** FK to the type of drug record (e.g., prescription, dispensing). */
    private Integer drugTypeConceptId;

    private String stopReason;

    /** Number of refills after the initial prescription. */
    private Integer refills;

    private BigDecimal quantity;

    /** Number of days of supply. */
    private Integer daysSupply;

    private String sig;

    /** FK to the drug route concept (oral, intravenous, etc.). */
    private Integer routeConceptId;

    private String lotNumber;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String drugSourceValue;

    /** FK to source drug concept (NDC, ATC, etc.). */
    private Integer drugSourceConceptId;

    private String routeSourceValue;

    private String doseUnitSourceValue;
}
