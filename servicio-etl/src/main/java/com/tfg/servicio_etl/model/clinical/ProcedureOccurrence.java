package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - PROCEDURE_OCCURRENCE table
 *
 * Records activities or processes ordered by, or carried out by,
 * a healthcare provider on the patient with a diagnostic or
 * therapeutic purpose.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProcedureOccurrence {

    private Long procedureOccurrenceId;

    private Long personId;

    /** FK to standard procedure concept (SNOMED, CPT-4, ICD-10-PCS). */
    private Integer procedureConceptId;

    private LocalDate procedureDate;

    private LocalDateTime procedureDatetime;

    private LocalDate procedureEndDate;

    private LocalDateTime procedureEndDatetime;

    /** FK indicating how the procedure record was created. */
    private Integer procedureTypeConceptId;

    /** FK to modifier concept (e.g., CPT-4 modifier). */
    private Integer modifierConceptId;

    private Integer quantity;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String procedureSourceValue;

    private Integer procedureSourceConceptId;

    private String modifierSourceValue;
}
