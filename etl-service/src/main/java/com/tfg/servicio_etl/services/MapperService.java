package com.tfg.servicio_etl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.servicio_etl.factories.CdmSourceFactoryInterface;
import com.tfg.servicio_etl.factories.MetadataFactoryInterface;
import com.tfg.servicio_etl.rest.dto.OMOPBundle;
import com.tfg.servicio_etl.services.dto.LegacyBundle;

@Service
public class MapperService implements MapperInterface {
	private final ConceptLookupService conceptLookup;
	private final CdmSourceFactoryInterface cdmSourceFactory;
	private final MetadataFactoryInterface metadataFactory;
	
	@Autowired
	public MapperService(ConceptLookupService conceptLookup, CdmSourceFactoryInterface cdmSourceFactory, MetadataFactoryInterface metadataFactory) {
		this.conceptLookup = conceptLookup;
		this.cdmSourceFactory = cdmSourceFactory;
		this.metadataFactory = metadataFactory;
	}
	
	@Override
	public OMOPBundle map(LegacyBundle source) {
		OMOPBundle omopBundle = new OMOPBundle();
		
		omopBundle.setMetadataList(metadataFactory.createMetadata());
		omopBundle.setCdmSource(cdmSourceFactory.createCdmSource());
		return omopBundle;
	}
}
