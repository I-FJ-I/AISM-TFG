package com.tfg.servicio_etl.factories;

import org.springframework.stereotype.Component;

import com.tfg.servicio_etl.configurations.CdmProperties;
import com.tfg.servicio_etl.model.metadata.CdmSource;

@Component
public class CdmSourceFactory implements CdmSourceFactoryInterface {
	private final CdmProperties cdmProperties;
	
	public CdmSourceFactory(CdmProperties cdmProperties) {
		this.cdmProperties = cdmProperties;
	}
	
	public CdmSource createCdmSource() {
		CdmSource cdmSource = new CdmSource();
		cdmSource.setCdmSourceName(cdmProperties.getCdmSourceName());
		cdmSource.setCdmSourceAbbreviation(cdmProperties.getCdmSourceAbbreviation());
		cdmSource.setCdmHolder(cdmProperties.getCdmHolder());
		cdmSource.setSourceDescription(cdmProperties.getSourceDescription());
		cdmSource.setSourceDocumentationReference(cdmProperties.getSourceDocumentationReference());
		cdmSource.setCdmEtlReference(cdmProperties.getCdmEtlReference());
		cdmSource.setSourceReleaseDate(cdmProperties.getSourceReleaseDate());
		cdmSource.setCdmReleaseDate(cdmProperties.getCdmReleaseDate());
		cdmSource.setCdmVersion(cdmProperties.getCdmVersion());
		cdmSource.setCdmVersionConceptId(cdmProperties.getCdmVersionConceptId());
		cdmSource.setVocabularyVersion(cdmProperties.getVocabularyVersion());
		return cdmSource;
	}

}
