package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - MEASUREMENT table
 *
 * Contains records of structured values (numerical or categorical)
 * obtained through systematic and standardized examination or testing
 * of a Person or Person's sample.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Measurement {

    private Long measurementId;

    private Long personId;

    /** FK to standard measurement concept (LOINC). */
    private Integer measurementConceptId;

    private LocalDate measurementDate;

    private LocalDateTime measurementDatetime;

    private String measurementTime;

    /** FK indicating how the measurement was recorded (e.g., lab result). */
    private Integer measurementTypeConceptId;

    /** FK to the operator concept (=, <, >, etc.). */
    private Integer operatorConceptId;

    /** Numeric result. */
    private BigDecimal valueAsNumber;

    /** FK to categorical result concept. */
    private Integer valueAsConceptId;

    /** FK to the unit concept (UCUM). */
    private Integer unitConceptId;

    private BigDecimal rangeLow;

    private BigDecimal rangeHigh;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String measurementSourceValue;

    private Integer measurementSourceConceptId;

    private String unitSourceValue;

    private Integer unitSourceConceptId;

    private String valueSourceValue;

    /** FK to EVENT table. New in CDM v5.4. */
    private Long measurementEventId;

    /** FK to a concept indicating the event type. New in CDM v5.4. */
    private Integer measEventFieldConceptId;
}
