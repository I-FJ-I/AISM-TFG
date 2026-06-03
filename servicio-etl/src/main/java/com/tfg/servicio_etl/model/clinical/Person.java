package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDateTime;


/**
 * OMOP CDM v5.4 - PERSON table
 *
 * Central identity management for all Persons in the database.
 * Each Person record has associated demographic attributes which are
 * assumed to be temporally invariant.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Person {

    /** A unique identifier for each person. */
    private Long personId;

    /** A foreign key to the gender concept. */
    private Integer genderConceptId;

    /** The year of birth of the person. */
    private Integer yearOfBirth;

    /** The month of birth of the person. */
    private Integer monthOfBirth;

    /** The day of birth of the person. */
    private Integer dayOfBirth;

    /** The date of birth of the person. */
    private LocalDateTime birthDatetime;

    /** A foreign key to the race concept. */
    private Integer raceConceptId;

    /** A foreign key to the ethnicity concept. */
    private Integer ethnicityConceptId;

    /** The location id where the person resides. */
    private Long locationId;

    /** The care site id where the person receives primary care. */
    private Long providerId;

    /** A foreign key to the care site in the care_site table. */
    private Long careSiteId;

    /** An encrypted key given to a person. */
    private String personSourceValue;

    /** The source value of the gender. */
    private String genderSourceValue;

    /** FK to the gender source concept. */
    private Integer genderSourceConceptId;

    /** The source value of the race. */
    private String raceSourceValue;

    /** FK to the race source concept. */
    private Integer raceSourceConceptId;

    /** The source value of the ethnicity. */
    private String ethnicitySourceValue;

    /** FK to the ethnicity source concept. */
    private Integer ethnicitySourceConceptId;
}
