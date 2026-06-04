package com.tfg.servicio_etl.configurations;

import java.time.LocalDate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "legacy.metadata.cdm")
public class CdmProperties {

	private String cdmSourceName;
	
    private String cdmSourceAbbreviation;

    private String cdmHolder;

    private String sourceDescription;

    private String sourceDocumentationReference;

    private String cdmEtlReference;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sourceReleaseDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cdmReleaseDate;

    /** CDM version identifier (e.g., 'v5.4'). */
    private String cdmVersion;

    private Integer cdmVersionConceptId;

    /** FK to the vocabulary version used. */
    private String vocabularyVersion;

	public String getCdmSourceName() {
		return cdmSourceName;
	}

	public void setCdmSourceName(String cdmSourceName) {
		this.cdmSourceName = cdmSourceName;
	}

	public String getCdmSourceAbbreviation() {
		return cdmSourceAbbreviation;
	}

	public void setCdmSourceAbbreviation(String cdmSourceAbbreviation) {
		this.cdmSourceAbbreviation = cdmSourceAbbreviation;
	}

	public String getCdmHolder() {
		return cdmHolder;
	}

	public void setCdmHolder(String cdmHolder) {
		this.cdmHolder = cdmHolder;
	}

	public String getSourceDescription() {
		return sourceDescription;
	}

	public void setSourceDescription(String sourceDescription) {
		this.sourceDescription = sourceDescription;
	}

	public String getSourceDocumentationReference() {
		return sourceDocumentationReference;
	}

	public void setSourceDocumentationReference(String sourceDocumentationReference) {
		this.sourceDocumentationReference = sourceDocumentationReference;
	}

	public String getCdmEtlReference() {
		return cdmEtlReference;
	}

	public void setCdmEtlReference(String cdmEtlReference) {
		this.cdmEtlReference = cdmEtlReference;
	}

	public LocalDate getSourceReleaseDate() {
		return sourceReleaseDate;
	}

	public void setSourceReleaseDate(LocalDate sourceReleaseDate) {
		this.sourceReleaseDate = sourceReleaseDate;
	}

	public LocalDate getCdmReleaseDate() {
		return cdmReleaseDate;
	}

	public void setCdmReleaseDate(LocalDate cdmReleaseDate) {
		this.cdmReleaseDate = cdmReleaseDate;
	}

	public String getCdmVersion() {
		return cdmVersion;
	}

	public void setCdmVersion(String cdmVersion) {
		this.cdmVersion = cdmVersion;
	}

	public Integer getCdmVersionConceptId() {
		return cdmVersionConceptId;
	}

	public void setCdmVersionConceptId(Integer cdmVersionConceptId) {
		this.cdmVersionConceptId = cdmVersionConceptId;
	}

	public String getVocabularyVersion() {
		return vocabularyVersion;
	}

	public void setVocabularyVersion(String vocabularyVersion) {
		this.vocabularyVersion = vocabularyVersion;
	}
}
