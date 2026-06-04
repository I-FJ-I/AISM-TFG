package com.tfg.servicio_etl.services;

import com.tfg.servicio_etl.rest.dto.OMOPBundle;
import com.tfg.servicio_etl.services.dto.LegacyBundle;

public interface MapperInterface {
	
	public OMOPBundle map(LegacyBundle source);
	
}
