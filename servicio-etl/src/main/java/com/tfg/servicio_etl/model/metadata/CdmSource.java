package com.tfg.servicio_etl.model.metadata;

import lombok.*;
import java.time.LocalDate;

/**
 * OMOP CDM v5.4 - CDM_SOURCE table
 *
 * Contains detail about the source database and the process used to
 * transform the data into the OMOP CDM.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CdmSource {

    private String cdmSourceName;

    private String cdmSourceAbbreviation;

    private String cdmHolder;

    private String sourceDescription;

    private String sourceDocumentationReference;

    private String cdmEtlReference;

    private LocalDate sourceReleaseDate;

    private LocalDate cdmReleaseDate;

    /** CDM version identifier (e.g., 'v5.4'). */
    private String cdmVersion;

    private Integer cdmVersionConceptId;

    /** FK to the vocabulary version used. */
    private String vocabularyVersion;
}
