package com.tfg.servicio_etl.factories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tfg.servicio_etl.configurations.OmopProperties;
import com.tfg.servicio_etl.model.metadata.Metadata;

@Component
public class MetadataFactory implements MetadataFactoryInterface {

	private static final int METADATA_CONCEPT_ID = 32803;
	
	private final OmopProperties omopProperties;

	@Autowired
	public MetadataFactory(OmopProperties omopProperties) {
		this.omopProperties = omopProperties;
	}

	@Override
	public List<Metadata> createMetadata() {
		List<Metadata> metadataList = new ArrayList<>();
        
        Map<String, String> map = omopProperties.getMetadata();

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Metadata metadata = new Metadata();
                metadata.setMetadataId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
                metadata.setMetadataConceptId(0);
                metadata.setMetadataTypeConceptId(METADATA_CONCEPT_ID);
                metadata.setName(entry.getKey());
                metadata.setValueAsString(entry.getValue());
                metadata.setValueAsConceptId(0);
                metadata.setValueAsNumber(BigDecimal.valueOf(Long.parseLong(entry.getValue())));
                metadata.setMetadataDate(LocalDate.now());
                metadata.setMetadataDatetime(LocalDateTime.now());
                metadataList.add(metadata);
            }
        }

        return metadataList;
	}

}
