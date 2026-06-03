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

import lombok.*;

@Getter @Setter @Builder
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
	private List<Location> 				metadataList;
	private List<Provider> 				providerList;
	
	// Metadata tables
	private CdmSource					cdmSource;
	private Metadata					metadata;

	// Vocabulary tables are not included in the bundle as they are typically large and static, and can be accessed separately if needed.
}
