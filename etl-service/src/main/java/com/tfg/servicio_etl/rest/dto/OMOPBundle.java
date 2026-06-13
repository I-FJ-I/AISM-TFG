package com.tfg.servicio_etl.rest.dto;

import java.util.List;

import com.tfg.servicio_etl.model.clinical.ConditionOccurrence;
import com.tfg.servicio_etl.model.clinical.Death;
import com.tfg.servicio_etl.model.clinical.Measurement;
import com.tfg.servicio_etl.model.clinical.Observation;
import com.tfg.servicio_etl.model.clinical.Person;

import com.tfg.servicio_etl.model.health_system_data.Location;

import com.tfg.servicio_etl.model.metadata.CdmSource;
import com.tfg.servicio_etl.model.metadata.Metadata;

public class OMOPBundle {
	// Clinical tables
	private List<ConditionOccurrence> 	conditionOccurrenceList;
	private List<Death> 				deathList;
	private List<Measurement> 			measurementList;
	private List<Observation> 			observationList;
	private List<Person> 				personList;
	
	// Health systems tables
	private List<Location> 				locationList;
	
	// Metadata tables
	private CdmSource					cdmSource;
	private List<Metadata>				metadataList;
	
	// Vocabulary tables are not included in the bundle as they are typically large and static, and can be accessed separately if needed.
	
	public List<ConditionOccurrence> getConditionOccurrenceList() {
		return conditionOccurrenceList;
	}
	public void setConditionOccurrenceList(List<ConditionOccurrence> conditionOccurrenceList) {
		this.conditionOccurrenceList = conditionOccurrenceList;
	}
	public List<Death> getDeathList() {
		return deathList;
	}
	public void setDeathList(List<Death> deathList) {
		this.deathList = deathList;
	}
	public List<Measurement> getMeasurementList() {
		return measurementList;
	}
	public void setMeasurementList(List<Measurement> measurementList) {
		this.measurementList = measurementList;
	}
	public List<Observation> getObservationList() {
		return observationList;
	}
	public void setObservationList(List<Observation> observationList) {
		this.observationList = observationList;
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	public CdmSource getCdmSource() {
		return cdmSource;
	}
	public void setCdmSource(CdmSource cdmSource) {
		this.cdmSource = cdmSource;
	}
	public List<Metadata> getMetadataList() {
		return metadataList;
	}
	public void setMetadataList(List<Metadata> metadataList) {
		this.metadataList = metadataList;
	}
	
}
