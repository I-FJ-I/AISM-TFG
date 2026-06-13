package com.tfg.servicio_etl.model.clinical;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - OBSERVATION table
 *
 * Captures clinical facts about a Person obtained in the context of
 * examination, questioning or a procedure. Any data that cannot be
 * represented by any other standard clinical data table is contained
 * in the OBSERVATION table.
 */
public class Observation {
	
    private Long observationId;

    private Long personId;

    /** FK to standard observation concept. */
    private Integer observationConceptId;

    private LocalDate observationDate;

    private LocalDateTime observationDatetime;

    /** FK indicating how the observation was recorded. */
    private Integer observationTypeConceptId;

    private BigDecimal valueAsNumber;

    private String valueAsString;

    private Integer valueAsConceptId;

    private Integer qualifierConceptId;

    private Integer unitConceptId;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String observationSourceValue;

    private Integer observationSourceConceptId;

    private String unitSourceValue;

    private String qualifierSourceValue;

    private String valueSourceValue;

    /** FK to EVENT table. New in CDM v5.4. */
    private Long observationEventId;

    /** FK to concept indicating the event field. New in CDM v5.4. */
    private Integer obsEventFieldConceptId;

	public Long getObservationId() {
		return observationId;
	}

	public void setObservationId(Long observationId) {
		this.observationId = observationId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getObservationConceptId() {
		return observationConceptId;
	}

	public void setObservationConceptId(Integer observationConceptId) {
		this.observationConceptId = observationConceptId;
	}

	public LocalDate getObservationDate() {
		return observationDate;
	}

	public void setObservationDate(LocalDate observationDate) {
		this.observationDate = observationDate;
	}

	public LocalDateTime getObservationDatetime() {
		return observationDatetime;
	}

	public void setObservationDatetime(LocalDateTime observationDatetime) {
		this.observationDatetime = observationDatetime;
	}

	public Integer getObservationTypeConceptId() {
		return observationTypeConceptId;
	}

	public void setObservationTypeConceptId(Integer observationTypeConceptId) {
		this.observationTypeConceptId = observationTypeConceptId;
	}

	public BigDecimal getValueAsNumber() {
		return valueAsNumber;
	}

	public void setValueAsNumber(BigDecimal valueAsNumber) {
		this.valueAsNumber = valueAsNumber;
	}

	public String getValueAsString() {
		return valueAsString;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}

	public Integer getValueAsConceptId() {
		return valueAsConceptId;
	}

	public void setValueAsConceptId(Integer valueAsConceptId) {
		this.valueAsConceptId = valueAsConceptId;
	}

	public Integer getQualifierConceptId() {
		return qualifierConceptId;
	}

	public void setQualifierConceptId(Integer qualifierConceptId) {
		this.qualifierConceptId = qualifierConceptId;
	}

	public Integer getUnitConceptId() {
		return unitConceptId;
	}

	public void setUnitConceptId(Integer unitConceptId) {
		this.unitConceptId = unitConceptId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getVisitOccurrenceId() {
		return visitOccurrenceId;
	}

	public void setVisitOccurrenceId(Long visitOccurrenceId) {
		this.visitOccurrenceId = visitOccurrenceId;
	}

	public Long getVisitDetailId() {
		return visitDetailId;
	}

	public void setVisitDetailId(Long visitDetailId) {
		this.visitDetailId = visitDetailId;
	}

	public String getObservationSourceValue() {
		return observationSourceValue;
	}

	public void setObservationSourceValue(String observationSourceValue) {
		this.observationSourceValue = observationSourceValue;
	}

	public Integer getObservationSourceConceptId() {
		return observationSourceConceptId;
	}

	public void setObservationSourceConceptId(Integer observationSourceConceptId) {
		this.observationSourceConceptId = observationSourceConceptId;
	}

	public String getUnitSourceValue() {
		return unitSourceValue;
	}

	public void setUnitSourceValue(String unitSourceValue) {
		this.unitSourceValue = unitSourceValue;
	}

	public String getQualifierSourceValue() {
		return qualifierSourceValue;
	}

	public void setQualifierSourceValue(String qualifierSourceValue) {
		this.qualifierSourceValue = qualifierSourceValue;
	}

	public String getValueSourceValue() {
		return valueSourceValue;
	}

	public void setValueSourceValue(String valueSourceValue) {
		this.valueSourceValue = valueSourceValue;
	}

	public Long getObservationEventId() {
		return observationEventId;
	}

	public void setObservationEventId(Long observationEventId) {
		this.observationEventId = observationEventId;
	}

	public Integer getObsEventFieldConceptId() {
		return obsEventFieldConceptId;
	}

	public void setObsEventFieldConceptId(Integer obsEventFieldConceptId) {
		this.obsEventFieldConceptId = obsEventFieldConceptId;
	}
    
}
