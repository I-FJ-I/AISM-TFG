package com.tfg.servicio_etl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.servicio_etl.factories.CdmSourceFactoryInterface;
import com.tfg.servicio_etl.factories.MetadataFactoryInterface;
import com.tfg.servicio_etl.rest.dto.OMOPBundle;

import com.tfg.servicio_etl.services.*;
import com.tfg.servicio_etl.services.omop.ConditionService;
import com.tfg.servicio_etl.services.omop.DeathService;
import com.tfg.servicio_etl.services.omop.LocationService;
import com.tfg.servicio_etl.services.omop.MeasurementService;
import com.tfg.servicio_etl.services.omop.ObservationService;
import com.tfg.servicio_etl.services.omop.PersonService;

@Service
public class ExportService {
	private final CdmSourceFactoryInterface cdmSourceFactory;
	private final MetadataFactoryInterface metadataFactory;
	
	private final PersonService personService;
    private final ConditionService conditionService;
    private final ObservationService observationService;
    private final MeasurementService measurementService;
    private final DeathService deathService;
    private final LocationService locationService;
	
	@Autowired
	public ExportService(CdmSourceFactoryInterface cdmSourceFactory, MetadataFactoryInterface metadataFactory,
							PersonService personService, 
				            ConditionService conditionService, 
				            ObservationService observationService, 
				            MeasurementService measurementService, 
				            DeathService deathService, 
				            LocationService locationService) {
		this.cdmSourceFactory = cdmSourceFactory;
		this.metadataFactory = metadataFactory;
		this.personService = personService;
        this.conditionService = conditionService;
        this.observationService = observationService;
        this.measurementService = measurementService;
        this.deathService = deathService;
        this.locationService = locationService;
	}
	
	public OMOPBundle export() {
		OMOPBundle omopBundle = new OMOPBundle();
		
		omopBundle.setPersonList(personService.getAllPersons());
		omopBundle.setConditionOccurrenceList(conditionService.getAllConditions());
		omopBundle.setObservationList(observationService.getAllObservations());
		omopBundle.setMeasurementList(measurementService.getAllMeasurements());
		omopBundle.setDeathList(deathService.getAllDeaths());
		omopBundle.setLocationList(locationService.getAllLocations());
		
		omopBundle.setMetadataList(metadataFactory.createMetadata());
		omopBundle.setCdmSource(cdmSourceFactory.createCdmSource());
		return omopBundle;
	}
}
