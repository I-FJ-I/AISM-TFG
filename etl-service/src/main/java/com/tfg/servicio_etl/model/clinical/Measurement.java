package com.tfg.servicio_etl.model.clinical;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - MEASUREMENT table
 *
 * Contains records of structured values (numerical or categorical)
 * obtained through systematic and standardized examination or testing
 * of a Person or Person's sample.
 */
public class Measurement {

    private Long measurementId;

    private Long personId;

    /** FK to standard measurement concept (LOINC). */
    private Integer measurementConceptId;

    private LocalDate measurementDate;

    private LocalDateTime measurementDatetime;

    private String measurementTime;

    /** FK indicating how the measurement was recorded (e.g., lab result). */
    private Integer measurementTypeConceptId;

    /** FK to the operator concept (=, <, >, etc.). */
    private Integer operatorConceptId;

    /** Numeric result. */
    private Double valueAsNumber;

    /** FK to categorical result concept. */
    private Integer valueAsConceptId;

    /** FK to the unit concept (UCUM). */
    private Integer unitConceptId;

    private BigDecimal rangeLow;

    private BigDecimal rangeHigh;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String measurementSourceValue;

    private Integer measurementSourceConceptId;

    private String unitSourceValue;

    private Integer unitSourceConceptId;

    private String valueSourceValue;

    /** FK to EVENT table. New in CDM v5.4. */
    private Long measurementEventId;

    /** FK to a concept indicating the event type. New in CDM v5.4. */
    private Integer measEventFieldConceptId;

	public Long getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(Long measurementId) {
		this.measurementId = measurementId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getMeasurementConceptId() {
		return measurementConceptId;
	}

	public void setMeasurementConceptId(Integer measurementConceptId) {
		this.measurementConceptId = measurementConceptId;
	}

	public LocalDate getMeasurementDate() {
		return measurementDate;
	}

	public void setMeasurementDate(LocalDate measurementDate) {
		this.measurementDate = measurementDate;
	}

	public LocalDateTime getMeasurementDatetime() {
		return measurementDatetime;
	}

	public void setMeasurementDatetime(LocalDateTime measurementDatetime) {
		this.measurementDatetime = measurementDatetime;
	}

	public String getMeasurementTime() {
		return measurementTime;
	}

	public void setMeasurementTime(String measurementTime) {
		this.measurementTime = measurementTime;
	}

	public Integer getMeasurementTypeConceptId() {
		return measurementTypeConceptId;
	}

	public void setMeasurementTypeConceptId(Integer measurementTypeConceptId) {
		this.measurementTypeConceptId = measurementTypeConceptId;
	}

	public Integer getOperatorConceptId() {
		return operatorConceptId;
	}

	public void setOperatorConceptId(Integer operatorConceptId) {
		this.operatorConceptId = operatorConceptId;
	}

	public Double getValueAsNumber() {
		return valueAsNumber;
	}

	public void setValueAsNumber(Double d) {
		this.valueAsNumber = d;
	}

	public Integer getValueAsConceptId() {
		return valueAsConceptId;
	}

	public void setValueAsConceptId(Integer valueAsConceptId) {
		this.valueAsConceptId = valueAsConceptId;
	}

	public Integer getUnitConceptId() {
		return unitConceptId;
	}

	public void setUnitConceptId(Integer unitConceptId) {
		this.unitConceptId = unitConceptId;
	}

	public BigDecimal getRangeLow() {
		return rangeLow;
	}

	public void setRangeLow(BigDecimal rangeLow) {
		this.rangeLow = rangeLow;
	}

	public BigDecimal getRangeHigh() {
		return rangeHigh;
	}

	public void setRangeHigh(BigDecimal rangeHigh) {
		this.rangeHigh = rangeHigh;
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

	public String getMeasurementSourceValue() {
		return measurementSourceValue;
	}

	public void setMeasurementSourceValue(String measurementSourceValue) {
		this.measurementSourceValue = measurementSourceValue;
	}

	public Integer getMeasurementSourceConceptId() {
		return measurementSourceConceptId;
	}

	public void setMeasurementSourceConceptId(Integer measurementSourceConceptId) {
		this.measurementSourceConceptId = measurementSourceConceptId;
	}

	public String getUnitSourceValue() {
		return unitSourceValue;
	}

	public void setUnitSourceValue(String unitSourceValue) {
		this.unitSourceValue = unitSourceValue;
	}

	public Integer getUnitSourceConceptId() {
		return unitSourceConceptId;
	}

	public void setUnitSourceConceptId(Integer unitSourceConceptId) {
		this.unitSourceConceptId = unitSourceConceptId;
	}

	public String getValueSourceValue() {
		return valueSourceValue;
	}

	public void setValueSourceValue(String valueSourceValue) {
		this.valueSourceValue = valueSourceValue;
	}

	public Long getMeasurementEventId() {
		return measurementEventId;
	}

	public void setMeasurementEventId(Long measurementEventId) {
		this.measurementEventId = measurementEventId;
	}

	public Integer getMeasEventFieldConceptId() {
		return measEventFieldConceptId;
	}

	public void setMeasEventFieldConceptId(Integer measEventFieldConceptId) {
		this.measEventFieldConceptId = measEventFieldConceptId;
	}
    
}
