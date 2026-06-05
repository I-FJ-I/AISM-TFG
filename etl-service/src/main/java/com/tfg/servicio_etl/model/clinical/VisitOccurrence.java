package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - VISIT_OCCURRENCE table
 *
 * Contains the spans of time a Person continuously receives medical
 * services from one or more providers at a Care Site in a given
 * Visit Type.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitOccurrence {

    private Long visitOccurrenceId;

    private Long personId;

    /** FK to visit concept (inpatient, outpatient, ER, etc.). */
    private Integer visitConceptId;

    private LocalDate visitStartDate;

    private LocalDateTime visitStartDatetime;

    private LocalDate visitEndDate;

    private LocalDateTime visitEndDatetime;

    /** FK to the type concept (e.g., Administrative Claim, EHR). */
    private Integer visitTypeConceptId;

    private Long providerId;

    private Long careSiteId;

    private String visitSourceValue;

    private Integer visitSourceConceptId;

    /** FK to the admitting source concept. */
    private Integer admittedFromConceptId;

    private String admittedFromSourceValue;

    /** FK to the discharge to concept. */
    private Integer dischargedToConceptId;

    private String dischargedToSourceValue;

    /** FK to the preceding visit occurrence. */
    private Long precedingVisitOccurrenceId;
}
