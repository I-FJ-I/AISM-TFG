package com.tfg.servicio_etl.model.vocabulary;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - SOURCE_TO_CONCEPT_MAP table
 * Contains non-standard, source-specific codes and their relationship to
 * standard concepts in the CONCEPT table.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SourceToConceptMap {

    private String sourceCode;

    private Integer sourceConceptId;

    private String sourceVocabularyId;

    private String sourceCodeDescription;

    private Integer targetConceptId;

    private String targetVocabularyId;

    private LocalDate validStartDate;

    private LocalDate validEndDate;

    private String invalidReason;

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getSourceConceptId() {
		return sourceConceptId;
	}

	public void setSourceConceptId(Integer sourceConceptId) {
		this.sourceConceptId = sourceConceptId;
	}

	public String getSourceVocabularyId() {
		return sourceVocabularyId;
	}

	public void setSourceVocabularyId(String sourceVocabularyId) {
		this.sourceVocabularyId = sourceVocabularyId;
	}

	public String getSourceCodeDescription() {
		return sourceCodeDescription;
	}

	public void setSourceCodeDescription(String sourceCodeDescription) {
		this.sourceCodeDescription = sourceCodeDescription;
	}

	public Integer getTargetConceptId() {
		return targetConceptId;
	}

	public void setTargetConceptId(Integer targetConceptId) {
		this.targetConceptId = targetConceptId;
	}

	public String getTargetVocabularyId() {
		return targetVocabularyId;
	}

	public void setTargetVocabularyId(String targetVocabularyId) {
		this.targetVocabularyId = targetVocabularyId;
	}

	public LocalDate getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(LocalDate validStartDate) {
		this.validStartDate = validStartDate;
	}

	public LocalDate getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(LocalDate validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}
    
    
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @lombok.EqualsAndHashCode
class SourceToConceptMapId implements java.io.Serializable {
    private String sourceCode;
    private Integer sourceConceptId;
    private String sourceVocabularyId;
    private Integer targetConceptId;
    private LocalDate validStartDate;
}

