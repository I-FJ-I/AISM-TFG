package com.tfg.servicio_etl.factories;

import org.springframework.stereotype.Component;

import com.tfg.servicio_etl.model.metadata.CdmSource;

@Component
public interface CdmSourceFactoryInterface {
	
	public CdmSource createCdmSource();
	
}
