package com.tfg.servicio_etl.model.health_systems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - CARE_SITE table
 *
 * Contains a list of uniquely identified institutional (physical or
 * organizational) units where healthcare delivery is practiced.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CareSite {

    private Long careSiteId;

    private String careSiteName;

    /** FK to the place of service concept (office, hospital, home). */
    private Integer placeOfServiceConceptId;

    private Long locationId;

    private String careSiteSourceValue;

    private String placeOfServiceSourceValue;
}
