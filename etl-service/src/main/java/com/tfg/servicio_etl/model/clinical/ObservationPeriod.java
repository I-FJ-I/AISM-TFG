package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;

/**
 * OMOP CDM v5.4 - OBSERVATION_PERIOD table
 *
 * Contains the spans of time for which a Person is at-risk to have
 * clinical events recorded within the source systems.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ObservationPeriod {

    private Long observationPeriodId;

    private Long personId;

    private LocalDate observationPeriodStartDate;

    private LocalDate observationPeriodEndDate;

    /** FK indicating the type of event that initiated the observation period. */
    private Integer periodTypeConceptId;
}
