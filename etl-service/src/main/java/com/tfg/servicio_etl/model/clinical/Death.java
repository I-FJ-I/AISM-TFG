package com.tfg.servicio_etl.model.clinical;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - DEATH table
 *
 * Contains the clinical event for how and when a Person dies.
 * A person can have up to one record per death in the source data.
 */
public class Death {

    private Long personId;

    private LocalDate deathDate;

    private LocalDateTime deathDatetime;

    /** FK indicating how the death was recorded. */
    private Integer deathTypeConceptId;

    /** FK to the cause of death concept (ICD mapped to SNOMED). */
    private Integer causeConceptId;

    private String causeSourceValue;
    
    private Integer causeSourceConceptId;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public LocalDate getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(LocalDate deathDate) {
		this.deathDate = deathDate;
	}

	public LocalDateTime getDeathDatetime() {
		return deathDatetime;
	}

	public void setDeathDatetime(LocalDateTime deathDatetime) {
		this.deathDatetime = deathDatetime;
	}

	public Integer getDeathTypeConceptId() {
		return deathTypeConceptId;
	}

	public void setDeathTypeConceptId(Integer deathTypeConceptId) {
		this.deathTypeConceptId = deathTypeConceptId;
	}

	public Integer getCauseConceptId() {
		return causeConceptId;
	}

	public void setCauseConceptId(Integer causeConceptId) {
		this.causeConceptId = causeConceptId;
	}

	public String getCauseSourceValue() {
		return causeSourceValue;
	}

	public void setCauseSourceValue(String causeSourceValue) {
		this.causeSourceValue = causeSourceValue;
	}

	public Integer getCauseSourceConceptId() {
		return causeSourceConceptId;
	}

	public void setCauseSourceConceptId(Integer causeSourceConceptId) {
		this.causeSourceConceptId = causeSourceConceptId;
	}
    
}
