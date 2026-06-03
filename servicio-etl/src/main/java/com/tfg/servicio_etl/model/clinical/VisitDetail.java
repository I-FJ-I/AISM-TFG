package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - VISIT_DETAIL table
 *
 * An optional table used to represent a more granular description of
 * a Visit (i.e. visit sub-type). Each record in the VISIT_DETAIL table
 * is linked to a single VISIT_OCCURRENCE record.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitDetail {

    private Long visitDetailId;

    private Long personId;

    private Integer visitDetailConceptId;

    private LocalDate visitDetailStartDate;

    private LocalDateTime visitDetailStartDatetime;

    private LocalDate visitDetailEndDate;

    private LocalDateTime visitDetailEndDatetime;

    private Integer visitDetailTypeConceptId;

    private Long providerId;

    private Long careSiteId;

    private String visitDetailSourceValue;

    private Integer visitDetailSourceConceptId;

    private Integer admittedFromConceptId;

    private String admittedFromSourceValue;

    private String dischargedToSourceValue;

    private Integer dischargedToConceptId;

    private Long precedingVisitDetailId;

    /** FK to the parent visit_detail record (for hierarchical visit structures). */
    private Long parentVisitDetailId;

    /** FK to the VISIT_OCCURRENCE this detail belongs to. */
    private Long visitOccurrenceId;
}
