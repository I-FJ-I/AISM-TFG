package com.tfg.servicio_fhir.rest;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import jakarta.servlet.ServletException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tfg.servicio_fhir.rest.auth.JwtAuthInterceptor;

@Component
public class FhirRestfulServer extends RestfulServer {

    private static final long serialVersionUID = 1L;

    private final PatientResourceProvider           patientProvider;
    private final ConditionResourceProvider         conditionProvider;
    private final ObservationResourceProvider       observationProvider;
    private final FhirContext 						fhirContext;

    @Autowired
    public FhirRestfulServer(PatientResourceProvider patientProvider, ConditionResourceProvider conditionProvider,
    		ObservationResourceProvider observationProvider, FhirContext fhirContext) {
		this.patientProvider = patientProvider;
		this.conditionProvider = conditionProvider;
		this.observationProvider = observationProvider;
		this.fhirContext = fhirContext;
	}
    
    @Override
    protected void initialize() throws ServletException {
        super.initialize();
        
        setFhirContext(fhirContext);
        
        setResourceProviders(List.of(
                patientProvider,
                conditionProvider,
                observationProvider
        ));
        
        JwtAuthInterceptor authInterceptor = new JwtAuthInterceptor();
        this.getInterceptorService().registerInterceptor(authInterceptor);
    }
}