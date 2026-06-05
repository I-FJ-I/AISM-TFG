package com.tfg.servicio_etl.model.health_systems;

import lombok.*;

/**
 * OMOP CDM v5.4 - LOCATION table
 *
 * Represents a generic geographical location, such as the address
 * of a Person or a Care Site.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {

    private Long locationId;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String county;

    private String country;

    /** FK to the geographic area concept (e.g., Census region). */
    private Integer countryConceptId;

    private String locationSourceValue;

    private java.math.BigDecimal latitude;

    private java.math.BigDecimal longitude;
}
