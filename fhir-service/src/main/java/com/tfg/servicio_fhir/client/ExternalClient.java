package com.tfg.servicio_fhir.client;

import java.util.List;
import java.util.Optional;

import org.hl7.fhir.instance.model.api.IBaseResource;

public interface ExternalClient {
	public <T extends IBaseResource> Optional<T> fetchResource(
            String resourceType,
            String id,
            Class<T> targetClass);
	
	public <T extends IBaseResource> Optional<List<T>> fetchResourceList(
            String resourceType,
            Class<T> targetClass);
}
