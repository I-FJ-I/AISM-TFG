package com.tfg.servicio_fhir.rest;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@WebServlet(urlPatterns = "/fhir/*", loadOnStartup = 1)
public class FhirRestfulServer extends RestfulServer {

    private static final long serialVersionUID = 1L;

    private final PatientResourceProvider           patientProvider;
    private final EncounterResourceProvider         encounterProvider;
    private final ConditionResourceProvider         conditionProvider;
    private final MedicationRequestResourceProvider medicationRequestProvider;
    private final ObservationResourceProvider       observationProvider;
    private final ProcedureResourceProvider         procedureProvider;
    private final DeviceResourceProvider            deviceProvider;
    private final SpecimenResourceProvider          specimenProvider;
    private final FhirContext 						fhirContext;

    @Autowired
    public FhirRestfulServer(PatientResourceProvider patientProvider, EncounterResourceProvider encounterProvider, ConditionResourceProvider conditionProvider,
    		MedicationRequestResourceProvider medicationRequestProvider, ObservationResourceProvider observationProvider, ProcedureResourceProvider procedureProvider, 
    		DeviceResourceProvider deviceProvider, SpecimenResourceProvider specimenProvider, FhirContext fhirContext) {
		this.patientProvider = patientProvider;
		this.encounterProvider = encounterProvider;
		this.conditionProvider = conditionProvider;
		this.medicationRequestProvider = medicationRequestProvider;
		this.observationProvider = observationProvider;
		this.procedureProvider = procedureProvider;
		this.deviceProvider = deviceProvider;
		this.specimenProvider = specimenProvider;
		this.fhirContext = fhirContext;
	}
    
    @Override
    protected void initialize() throws ServletException {
        super.initialize();
        
        setFhirContext(fhirContext);
        
        setResourceProviders(List.of(
                patientProvider,
                encounterProvider,
                conditionProvider,
                medicationRequestProvider,
                observationProvider,
                procedureProvider,
                deviceProvider,
                specimenProvider
        ));
    }
}