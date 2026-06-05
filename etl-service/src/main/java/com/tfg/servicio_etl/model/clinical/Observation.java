package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - OBSERVATION table
 *
 * Captures clinical facts about a Person obtained in the context of
 * examination, questioning or a procedure. Any data that cannot be
 * represented by any other standard clinical data table is contained
 * in the OBSERVATION table.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Observation {
	
    private Long observationId;

    private Long personId;

    /** FK to standard observation concept. */
    private Integer observationConceptId;

    private LocalDate observationDate;

    private LocalDateTime observationDatetime;

    /** FK indicating how the observation was recorded. */
    private Integer observationTypeConceptId;

    private BigDecimal valueAsNumber;

    private String valueAsString;

    private Integer valueAsConceptId;

    private Integer qualifierConceptId;

    private Integer unitConceptId;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String observationSourceValue;

    private Integer observationSourceConceptId;

    private String unitSourceValue;

    private String qualifierSourceValue;

    private String valueSourceValue;

    /** FK to EVENT table. New in CDM v5.4. */
    private Long observationEventId;

    /** FK to concept indicating the event field. New in CDM v5.4. */
    private Integer obsEventFieldConceptId;
}
