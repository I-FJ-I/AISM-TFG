package com.tfg.servicio_fhir.interceptors;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tfg.servicio_fhir.client.ExternalClient;
import com.tfg.servicio_fhir.client.FhirResourcePersister;

import java.util.Map;
import java.util.Optional;


/*
 * HAPI FHIR Interceptor — Cache-aside Pattern
 * Triggered when a provider throws a ResourceNotFoundException.
 
 * Workflow:
 * 1. Detects the resource type and ID from the original request.
 * 2. Requests the resource from the external FHIR service.
 * 3. If found, persists it in MongoDB.
 * 4. Re-executes the request, returning the resource to the client.
 * 5. If the external service also does not have it, propagates the original 404.

 * Registration in FhirServerConfig:
 * registerInterceptor(cacheAsideInterceptor);
 */
@Component
@Interceptor
public class ExceptionInterceptor {

    private final ExternalClient externalClient;
    private final FhirResourcePersister persister;
    
    @Autowired
    public ExceptionInterceptor(ExternalClient externalFhirClient, FhirResourcePersister persister) {
    	this.externalClient = externalFhirClient;
    	this.persister = persister;
    }

    /**
     * resourceType → FHIR Java class.
     */
    private static final Map<String, Class<? extends IBaseResource>> RESOURCE_TYPES = Map.of(
            "Patient",           Patient.class,
            "Encounter",         Encounter.class,
            "Condition",         Condition.class,
            "MedicationRequest", MedicationRequest.class,
            "Observation",       Observation.class,
            "Procedure",         Procedure.class,
            "Device",            Device.class,
            "Specimen",          Specimen.class
    );

    /**
     * Hook that intercepts exceptions before HAPI sends them to the client.
     *
     * Pointcut.SERVER_PRE_PROCESS_OUTGOING_EXCEPTION is triggered when 
     * a provider throws any exception, including ResourceNotFoundException.
     *
     * Returns:
     * - The resource retrieved from the external service if found (overrides the 404).
     * - null to let HAPI propagate the original exception.
     */
    @Hook(Pointcut.SERVER_PRE_PROCESS_OUTGOING_EXCEPTION)
    public IBaseResource handleResourceNotFound(
            RequestDetails requestDetails,
            Throwable exception) {

        if (!(exception instanceof ResourceNotFoundException)) {
            return null;
        }

        String resourceType = requestDetails.getResourceName();
        String resourceId   = requestDetails.getId() != null
                ? requestDetails.getId().getIdPart()
                : null;

        if (resourceType == null || resourceId == null) {
            return null;
        }

        Class<? extends IBaseResource> targetClass = RESOURCE_TYPES.get(resourceType);
        if (targetClass == null) {
            return null;
        }

        Optional<? extends IBaseResource> externalResource =
                externalClient.fetchResource(resourceType, resourceId, targetClass);

        if (externalResource.isEmpty()) {
            return null;
        }

        IBaseResource resource = externalResource.get();

        try {
            persister.persist(resource);
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return resource;
    }
}