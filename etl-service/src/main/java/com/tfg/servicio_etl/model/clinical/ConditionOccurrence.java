package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - CONDITION_OCCURRENCE table
 *
 * Contains records of the presence of a disease or medical condition
 * stated as a diagnosis, a sign, or a symptom, which is either
 * observed by a Provider or reported by the patient.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConditionOccurrence {

    private Long conditionOccurrenceId;

    private Long personId;

    /** FK to standard condition concept (ICD mapped to SNOMED). */
    private Integer conditionConceptId;

    private LocalDate conditionStartDate;

    private LocalDateTime conditionStartDatetime;

    private LocalDate conditionEndDate;

    private LocalDateTime conditionEndDatetime;

    /** FK indicating how the condition was recorded (e.g., primary diagnosis). */
    private Integer conditionTypeConceptId;

    /** FK to concept indicating condition status (active, inactive, resolved). */
    private Integer conditionStatusConceptId;

    private String stopReason;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String conditionSourceValue;

    /** FK to the source condition concept (e.g., ICD-10 code). */
    private Integer conditionSourceConceptId;

    private String conditionStatusSourceValue;
}
