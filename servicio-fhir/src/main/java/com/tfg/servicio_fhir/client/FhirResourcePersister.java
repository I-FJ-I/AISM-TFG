package com.tfg.servicio_fhir.client;

import org.hl7.fhir.instance.model.api.IBaseResource;

public interface FhirResourcePersister {
	public void persist(IBaseResource resource);
}
