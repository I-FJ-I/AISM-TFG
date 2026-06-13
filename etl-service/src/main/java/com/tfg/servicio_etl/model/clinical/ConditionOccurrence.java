package com.tfg.servicio_etl.model.clinical;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - CONDITION_OCCURRENCE table
 *
 * Contains records of the presence of a disease or medical condition
 * stated as a diagnosis, a sign, or a symptom, which is either
 * observed by a Provider or reported by the patient.
 */
public class ConditionOccurrence {

    private Long conditionOccurrenceId;

    private Long personId;

    /** FK to standard condition concept (ICD mapped to SNOMED). */
    private Integer conditionConceptId;

    private LocalDate conditionStartDate;

    private LocalDateTime conditionStartDatetime;

    private LocalDate conditionEndDate;

    private LocalDateTime conditionEndDatetime;

    /** FK indicating how the condition was recorded (e.g., primary diagnosis). */
    private Integer conditionTypeConceptId;

    /** FK to concept indicating condition status (active, inactive, resolved). */
    private Integer conditionStatusConceptId;

    private String stopReason;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String conditionSourceValue;

    /** FK to the source condition concept (e.g., ICD-10 code). */
    private Integer conditionSourceConceptId;

    private String conditionStatusSourceValue;

	public Long getConditionOccurrenceId() {
		return conditionOccurrenceId;
	}

	public void setConditionOccurrenceId(Long conditionOccurrenceId) {
		this.conditionOccurrenceId = conditionOccurrenceId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getConditionConceptId() {
		return conditionConceptId;
	}

	public void setConditionConceptId(Integer conditionConceptId) {
		this.conditionConceptId = conditionConceptId;
	}

	public LocalDate getConditionStartDate() {
		return conditionStartDate;
	}

	public void setConditionStartDate(LocalDate conditionStartDate) {
		this.conditionStartDate = conditionStartDate;
	}

	public LocalDateTime getConditionStartDatetime() {
		return conditionStartDatetime;
	}

	public void setConditionStartDatetime(LocalDateTime conditionStartDatetime) {
		this.conditionStartDatetime = conditionStartDatetime;
	}

	public LocalDate getConditionEndDate() {
		return conditionEndDate;
	}

	public void setConditionEndDate(LocalDate conditionEndDate) {
		this.conditionEndDate = conditionEndDate;
	}

	public LocalDateTime getConditionEndDatetime() {
		return conditionEndDatetime;
	}

	public void setConditionEndDatetime(LocalDateTime conditionEndDatetime) {
		this.conditionEndDatetime = conditionEndDatetime;
	}

	public Integer getConditionTypeConceptId() {
		return conditionTypeConceptId;
	}

	public void setConditionTypeConceptId(Integer conditionTypeConceptId) {
		this.conditionTypeConceptId = conditionTypeConceptId;
	}

	public Integer getConditionStatusConceptId() {
		return conditionStatusConceptId;
	}

	public void setConditionStatusConceptId(Integer conditionStatusConceptId) {
		this.conditionStatusConceptId = conditionStatusConceptId;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
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

	public String getConditionSourceValue() {
		return conditionSourceValue;
	}

	public void setConditionSourceValue(String conditionSourceValue) {
		this.conditionSourceValue = conditionSourceValue;
	}

	public Integer getConditionSourceConceptId() {
		return conditionSourceConceptId;
	}

	public void setConditionSourceConceptId(Integer conditionSourceConceptId) {
		this.conditionSourceConceptId = conditionSourceConceptId;
	}

	public String getConditionStatusSourceValue() {
		return conditionStatusSourceValue;
	}

	public void setConditionStatusSourceValue(String conditionStatusSourceValue) {
		this.conditionStatusSourceValue = conditionStatusSourceValue;
	}
    
}
