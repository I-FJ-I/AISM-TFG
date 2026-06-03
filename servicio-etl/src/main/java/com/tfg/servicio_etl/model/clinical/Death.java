package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - DEATH table
 *
 * Contains the clinical event for how and when a Person dies.
 * A person can have up to one record per death in the source data.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Death {

    private Long personId;

    private LocalDate deathDate;

    private LocalDateTime deathDatetime;

    /** FK indicating how the death was recorded. */
    private Integer deathTypeConceptId;

    /** FK to the cause of death concept (ICD mapped to SNOMED). */
    private Integer causeConceptId;

    private String causeSourceValue;
    
    private Integer causeSourceConceptId;
}
