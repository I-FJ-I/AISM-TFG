package com.tfg.servicio_etl.model.health_systems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - PROVIDER table
 *
 * Contains a list of uniquely identified healthcare providers.
 * These are individuals providing hands-on healthcare to patients,
 * such as physicians, nurses, midwives, physical therapists, etc.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Provider {

    private Long providerId;

    private String providerName;

    /** National Provider Identifier. */
    private String npi;

    /** Drug Enforcement Agency number. */
    private String dea;

    /** FK to specialty concept (SNOMED). */
    private Integer specialtyConceptId;

    private Long careSiteId;

    private Integer yearOfBirth;

    private Integer genderConceptId;

    private String providerSourceValue;

    private String specialtySourceValue;

    private Integer specialtySourceConceptId;

    private String genderSourceValue;

    private Integer genderSourceConceptId;
}
