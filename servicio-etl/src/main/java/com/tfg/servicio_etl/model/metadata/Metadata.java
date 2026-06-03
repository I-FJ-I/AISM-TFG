package com.tfg.servicio_etl.model.metadata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - METADATA table
 *
 * Contains metadata information about a CDM instance, particularly with
 * regard to the provenance of the data and the process by which it was
 * transformed to the OMOP CDM.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Metadata {

    private Long metadataId;

    private Integer metadataConceptId;

    private Integer metadataTypeConceptId;

    private String name;

    private String valueAsString;

    private Integer valueAsConceptId;

    private BigDecimal valueAsNumber;

    private LocalDate metadataDate;

    private LocalDateTime metadataDatetime;
}