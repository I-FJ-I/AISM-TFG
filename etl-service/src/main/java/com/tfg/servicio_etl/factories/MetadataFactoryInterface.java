package com.tfg.servicio_etl.factories;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tfg.servicio_etl.model.metadata.Metadata;

@Component
public interface MetadataFactoryInterface {
	
	public List<Metadata> createMetadata();

}
