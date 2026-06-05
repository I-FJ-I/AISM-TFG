package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - NOTE table
 *
 * Contains an unstructured text note recorded for a Person during
 * a healthcare encounter.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Note {

    private Long noteId;

    private Long personId;

    private LocalDate noteDate;

    private LocalDateTime noteDatetime;

    /** FK to the concept that identifies the type of note being recorded (LOINC). */
    private Integer noteTypeConceptId;

    /** FK to the concept that identifies the class of note (LOINC). */
    private Integer noteClassConceptId;

    private String noteTitle;

    private String noteText;

    /** FK to the encoding concept for the note text (UTF-8). */
    private Integer encodingConceptId;

    /** FK to the language concept for the note text. */
    private Integer languageConceptId;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String noteSourceValue;

    /** FK to EVENT table. New in CDM v5.4. */
    private Long noteEventId;

    /** FK to concept indicating the event field. New in CDM v5.4. */
    private Integer noteEventFieldConceptId;
}
