package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - SPECIMEN table
 *
 * Contains the record of biological samples obtained from a Person
 * for the purpose of laboratory testing or research.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Specimen {

    private Long specimenId;

    private Long personId;

    /** FK to the specimen concept (SNOMED). */
    private Integer specimenConceptId;

    private Integer specimenTypeConceptId;

    private LocalDate specimenDate;

    private LocalDateTime specimenDatetime;

    private BigDecimal quantity;

    private Integer unitConceptId;

    /** FK to the anatomic site concept (SNOMED). */
    private Integer anatomicSiteConceptId;

    private Integer diseaseStatusConceptId;

    private String specimenSourceId;

    private String specimenSourceValue;

    private String unitSourceValue;

    private String anatomicSiteSourceValue;

    private String diseaseStatusSourceValue;
}
