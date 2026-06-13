package com.tfg.servicio_etl.model.clinical;

import java.time.LocalDateTime;


/**
 * OMOP CDM v5.4 - PERSON table
 *
 * Central identity management for all Persons in the database.
 * Each Person record has associated demographic attributes which are
 * assumed to be temporally invariant.
 */
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

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getGenderConceptId() {
		return genderConceptId;
	}

	public void setGenderConceptId(Integer genderConceptId) {
		this.genderConceptId = genderConceptId;
	}

	public Integer getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(Integer yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public Integer getMonthOfBirth() {
		return monthOfBirth;
	}

	public void setMonthOfBirth(Integer monthOfBirth) {
		this.monthOfBirth = monthOfBirth;
	}

	public Integer getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(Integer dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public LocalDateTime getBirthDatetime() {
		return birthDatetime;
	}

	public void setBirthDatetime(LocalDateTime birthDatetime) {
		this.birthDatetime = birthDatetime;
	}

	public Integer getRaceConceptId() {
		return raceConceptId;
	}

	public void setRaceConceptId(Integer raceConceptId) {
		this.raceConceptId = raceConceptId;
	}

	public Integer getEthnicityConceptId() {
		return ethnicityConceptId;
	}

	public void setEthnicityConceptId(Integer ethnicityConceptId) {
		this.ethnicityConceptId = ethnicityConceptId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getCareSiteId() {
		return careSiteId;
	}

	public void setCareSiteId(Long careSiteId) {
		this.careSiteId = careSiteId;
	}

	public String getPersonSourceValue() {
		return personSourceValue;
	}

	public void setPersonSourceValue(String personSourceValue) {
		this.personSourceValue = personSourceValue;
	}

	public String getGenderSourceValue() {
		return genderSourceValue;
	}

	public void setGenderSourceValue(String genderSourceValue) {
		this.genderSourceValue = genderSourceValue;
	}

	public Integer getGenderSourceConceptId() {
		return genderSourceConceptId;
	}

	public void setGenderSourceConceptId(Integer genderSourceConceptId) {
		this.genderSourceConceptId = genderSourceConceptId;
	}

	public String getRaceSourceValue() {
		return raceSourceValue;
	}

	public void setRaceSourceValue(String raceSourceValue) {
		this.raceSourceValue = raceSourceValue;
	}

	public Integer getRaceSourceConceptId() {
		return raceSourceConceptId;
	}

	public void setRaceSourceConceptId(Integer raceSourceConceptId) {
		this.raceSourceConceptId = raceSourceConceptId;
	}

	public String getEthnicitySourceValue() {
		return ethnicitySourceValue;
	}

	public void setEthnicitySourceValue(String ethnicitySourceValue) {
		this.ethnicitySourceValue = ethnicitySourceValue;
	}

	public Integer getEthnicitySourceConceptId() {
		return ethnicitySourceConceptId;
	}

	public void setEthnicitySourceConceptId(Integer ethnicitySourceConceptId) {
		this.ethnicitySourceConceptId = ethnicitySourceConceptId;
	}
    
    
}
