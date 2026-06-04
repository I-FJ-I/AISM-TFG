package com.tfg.servicio_etl.model.metadata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OMOP CDM v5.4 - METADATA table
 *
 * Contains metadata information about a CDM instance, particularly with
 * regard to the provenance of the data and the process by which it was
 * transformed to the OMOP CDM.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Metadata {

    private Long metadataId;

    private Integer metadataConceptId;

    private Integer metadataTypeConceptId;

    private String name;

    private String valueAsString;

    private Integer valueAsConceptId;

    private BigDecimal valueAsNumber;

    private LocalDate metadataDate;

    private LocalDateTime metadataDatetime;

	public Long getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(Long metadataId) {
		this.metadataId = metadataId;
	}

	public Integer getMetadataConceptId() {
		return metadataConceptId;
	}

	public void setMetadataConceptId(Integer metadataConceptId) {
		this.metadataConceptId = metadataConceptId;
	}

	public Integer getMetadataTypeConceptId() {
		return metadataTypeConceptId;
	}

	public void setMetadataTypeConceptId(Integer metadataTypeConceptId) {
		this.metadataTypeConceptId = metadataTypeConceptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BigDecimal getValueAsNumber() {
		return valueAsNumber;
	}

	public void setValueAsNumber(BigDecimal valueAsNumber) {
		this.valueAsNumber = valueAsNumber;
	}

	public LocalDate getMetadataDate() {
		return metadataDate;
	}

	public void setMetadataDate(LocalDate metadataDate) {
		this.metadataDate = metadataDate;
	}

	public LocalDateTime getMetadataDatetime() {
		return metadataDatetime;
	}

	public void setMetadataDatetime(LocalDateTime metadataDatetime) {
		this.metadataDatetime = metadataDatetime;
	}
    
}