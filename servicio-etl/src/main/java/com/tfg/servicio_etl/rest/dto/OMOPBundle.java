package com.tfg.servicio_etl.rest.dto;

import java.util.List;

import com.tfg.servicio_etl.model.clinical.ConditionOccurrence;
import com.tfg.servicio_etl.model.clinical.Death;
import com.tfg.servicio_etl.model.clinical.DeviceExposure;
import com.tfg.servicio_etl.model.clinical.DrugExposure;
import com.tfg.servicio_etl.model.clinical.FactRelationship;
import com.tfg.servicio_etl.model.clinical.FactRelationshipId;
import com.tfg.servicio_etl.model.clinical.Measurement;
import com.tfg.servicio_etl.model.clinical.Note;
import com.tfg.servicio_etl.model.clinical.NoteNlp;
import com.tfg.servicio_etl.model.clinical.Observation;
import com.tfg.servicio_etl.model.clinical.ObservationPeriod;
import com.tfg.servicio_etl.model.clinical.Person;
import com.tfg.servicio_etl.model.clinical.ProcedureOccurrence;
import com.tfg.servicio_etl.model.clinical.Specimen;
import com.tfg.servicio_etl.model.clinical.VisitDetail;
import com.tfg.servicio_etl.model.clinical.VisitOccurrence;

import com.tfg.servicio_etl.model.derived.Cohort;
import com.tfg.servicio_etl.model.derived.CohortDefinition;
import com.tfg.servicio_etl.model.derived.ConditionEra;
import com.tfg.servicio_etl.model.derived.DoseEra;
import com.tfg.servicio_etl.model.derived.DrugEra;

import com.tfg.servicio_etl.model.health_economics.Cost;
import com.tfg.servicio_etl.model.health_economics.PayerPlanPeriod;

import com.tfg.servicio_etl.model.health_systems.CareSite;
import com.tfg.servicio_etl.model.health_systems.Location;
import com.tfg.servicio_etl.model.health_systems.Provider;

import com.tfg.servicio_etl.model.metadata.CdmSource;
import com.tfg.servicio_etl.model.metadata.Metadata;

public class OMOPBundle {
	// Clinical tables
	private List<ConditionOccurrence> 	conditionOccurrenceList;
	private List<Death> 				deathList;
	private List<DeviceExposure> 		deviceExposureList;
	private List<DrugExposure> 			drugExposureList;
	private List<FactRelationship> 		factRelationshipList;
	private List<FactRelationshipId> 	factRelationshipIdList;
	private List<Measurement> 			measurementList;
	private List<Note> 					noteList;
	private List<NoteNlp> 				noteNlpList;
	private List<Observation> 			observationList;
	private List<ObservationPeriod> 	observationPeriodList;
	private List<Person> 				personList;
	private List<ProcedureOccurrence> 	procedureOccurrenceList;
	private List<Specimen> 				specimenList;
	private List<VisitDetail> 			visitDetailList;
	private List<VisitOccurrence> 		visitOccurrenceList;
	
	// Derived tables
	private List<Cohort> 				cohortList;
	private List<CohortDefinition> 		cohortDefinitionList;
	private List<ConditionEra> 			conditionEraList;
	private List<DoseEra> 				doseEraList;
	private List<DrugEra> 				drugEraList;
	
	// Health economics tables
	private List<Cost> 					costList;
	private List<PayerPlanPeriod> 		payerPlanPeriodList;
	
	// Health systems tables
	private List<CareSite> 				careSiteList;
	private List<Location> 				locationList;
	private List<Provider> 				providerList;
	
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
	public List<DeviceExposure> getDeviceExposureList() {
		return deviceExposureList;
	}
	public void setDeviceExposureList(List<DeviceExposure> deviceExposureList) {
		this.deviceExposureList = deviceExposureList;
	}
	public List<DrugExposure> getDrugExposureList() {
		return drugExposureList;
	}
	public void setDrugExposureList(List<DrugExposure> drugExposureList) {
		this.drugExposureList = drugExposureList;
	}
	public List<FactRelationship> getFactRelationshipList() {
		return factRelationshipList;
	}
	public void setFactRelationshipList(List<FactRelationship> factRelationshipList) {
		this.factRelationshipList = factRelationshipList;
	}
	public List<FactRelationshipId> getFactRelationshipIdList() {
		return factRelationshipIdList;
	}
	public void setFactRelationshipIdList(List<FactRelationshipId> factRelationshipIdList) {
		this.factRelationshipIdList = factRelationshipIdList;
	}
	public List<Measurement> getMeasurementList() {
		return measurementList;
	}
	public void setMeasurementList(List<Measurement> measurementList) {
		this.measurementList = measurementList;
	}
	public List<Note> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}
	public List<NoteNlp> getNoteNlpList() {
		return noteNlpList;
	}
	public void setNoteNlpList(List<NoteNlp> noteNlpList) {
		this.noteNlpList = noteNlpList;
	}
	public List<Observation> getObservationList() {
		return observationList;
	}
	public void setObservationList(List<Observation> observationList) {
		this.observationList = observationList;
	}
	public List<ObservationPeriod> getObservationPeriodList() {
		return observationPeriodList;
	}
	public void setObservationPeriodList(List<ObservationPeriod> observationPeriodList) {
		this.observationPeriodList = observationPeriodList;
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	public List<ProcedureOccurrence> getProcedureOccurrenceList() {
		return procedureOccurrenceList;
	}
	public void setProcedureOccurrenceList(List<ProcedureOccurrence> procedureOccurrenceList) {
		this.procedureOccurrenceList = procedureOccurrenceList;
	}
	public List<Specimen> getSpecimenList() {
		return specimenList;
	}
	public void setSpecimenList(List<Specimen> specimenList) {
		this.specimenList = specimenList;
	}
	public List<VisitDetail> getVisitDetailList() {
		return visitDetailList;
	}
	public void setVisitDetailList(List<VisitDetail> visitDetailList) {
		this.visitDetailList = visitDetailList;
	}
	public List<VisitOccurrence> getVisitOccurrenceList() {
		return visitOccurrenceList;
	}
	public void setVisitOccurrenceList(List<VisitOccurrence> visitOccurrenceList) {
		this.visitOccurrenceList = visitOccurrenceList;
	}
	public List<Cohort> getCohortList() {
		return cohortList;
	}
	public void setCohortList(List<Cohort> cohortList) {
		this.cohortList = cohortList;
	}
	public List<CohortDefinition> getCohortDefinitionList() {
		return cohortDefinitionList;
	}
	public void setCohortDefinitionList(List<CohortDefinition> cohortDefinitionList) {
		this.cohortDefinitionList = cohortDefinitionList;
	}
	public List<ConditionEra> getConditionEraList() {
		return conditionEraList;
	}
	public void setConditionEraList(List<ConditionEra> conditionEraList) {
		this.conditionEraList = conditionEraList;
	}
	public List<DoseEra> getDoseEraList() {
		return doseEraList;
	}
	public void setDoseEraList(List<DoseEra> doseEraList) {
		this.doseEraList = doseEraList;
	}
	public List<DrugEra> getDrugEraList() {
		return drugEraList;
	}
	public void setDrugEraList(List<DrugEra> drugEraList) {
		this.drugEraList = drugEraList;
	}
	public List<Cost> getCostList() {
		return costList;
	}
	public void setCostList(List<Cost> costList) {
		this.costList = costList;
	}
	public List<PayerPlanPeriod> getPayerPlanPeriodList() {
		return payerPlanPeriodList;
	}
	public void setPayerPlanPeriodList(List<PayerPlanPeriod> payerPlanPeriodList) {
		this.payerPlanPeriodList = payerPlanPeriodList;
	}
	public List<CareSite> getCareSiteList() {
		return careSiteList;
	}
	public void setCareSiteList(List<CareSite> careSiteList) {
		this.careSiteList = careSiteList;
	}
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	public List<Provider> getProviderList() {
		return providerList;
	}
	public void setProviderList(List<Provider> providerList) {
		this.providerList = providerList;
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
