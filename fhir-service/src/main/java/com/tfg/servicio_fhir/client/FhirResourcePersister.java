package com.tfg.servicio_fhir.client;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.stereotype.Component;

@Component
public interface FhirResourcePersister {
	public void persist(IBaseResource resource);
}
