package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;

/**
 * OMOP CDM v5.4 - NOTE_NLP table
 *
 * Encodes all output of NLP on clinical notes. Each row represents
 * a single extracted term from a note.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NoteNlp {

    private Long noteNlpId;

    /** FK to NOTE table. */
    private Long noteId;

    private Integer sectionConceptId;

    private String snippet;

    private String offset;

    private String lexicalVariant;

    /** FK to standard concept resolved by NLP. */
    private Integer noteNlpConceptId;

    private Integer noteNlpSourceConceptId;

    private String nlpSystem;

    private LocalDate nlpDate;

    private java.time.LocalDateTime nlpDatetime;

    /** Term modifiers: negation, uncertainty, subject, etc. */
    private String termExists;

    private String termTemporal;

    private String termModifiers;
}
