package com.tfg.servicio_fhir.rest;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@WebServlet(urlPatterns = "/fhir/*", loadOnStartup = 1)
public class FhirRestfulServer extends RestfulServer {

    private static final long serialVersionUID = 1L;

    private final PatientResourceProvider patientResourceProvider;
    private final FhirContext fhirContext;

    @Autowired
    public FhirRestfulServer(PatientResourceProvider patientResourceProvider, FhirContext fhirContext) {
		this.patientResourceProvider = patientResourceProvider;
		this.fhirContext = fhirContext;
	}
    
    @Override
    protected void initialize() throws ServletException {
        super.initialize();
        
        setFhirContext(fhirContext);
        
        registerProvider(patientResourceProvider);
    }
}