package com.tfg.servicio_etl.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tfg.servicio_etl.model.vocabulary.SourceToConceptMap;

import jakarta.annotation.PostConstruct;

@Component
public class ConceptLookupService {

    private Map<String, SourceToConceptMap> sourceToConceptMap;

    @PostConstruct
    public void load() throws IOException {
        sourceToConceptMap = Files.lines(Path.of("vocabularies/DICTIONARY.csv"))
            .skip(1)  								// header			
            .map(line -> line.split("\t"))
            .collect(Collectors.toMap(
                fields -> fields[0],  				// source_code
				fields -> {
					SourceToConceptMap map = new SourceToConceptMap();
					map.setSourceCode(fields[0]);
					map.setSourceConceptId(Integer.parseInt(fields[1]));
					map.setSourceVocabularyId(fields[2]);
					map.setSourceCodeDescription(fields[3]);
					map.setTargetConceptId(Integer.parseInt(fields[4]));
					map.setTargetVocabularyId(fields[5]);
					map.setValidStartDate(LocalDate.parse(fields[6]));
					map.setValidEndDate(LocalDate.parse(fields[7]));
					map.setInvalidReason(fields[8]);
					return map;
				}
            ));
    }

    public SourceToConceptMap lookup(String sourceCode) {
        return sourceToConceptMap.getOrDefault(sourceCode, null);
    }
}