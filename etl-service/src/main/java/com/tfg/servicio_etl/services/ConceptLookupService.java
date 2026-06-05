package com.tfg.servicio_etl.services;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import com.tfg.servicio_etl.model.vocabulary.SourceToConceptMap;

@Component
public class ConceptLookupService {

    @Autowired
    private ResourceLoader resourceLoader; 

    private Map<String, SourceToConceptMap> sourceToConceptMap;

    @PostConstruct
    public void load() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:dictionary/DICTIONARY.csv");

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            sourceToConceptMap = reader.lines()
                .skip(1) // Saltamos la cabecera
                .map(line -> line.split("\t"))
                .collect(Collectors.toMap(
                    fields -> fields[0],
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
                        map.setInvalidReason(fields.length > 8 ? fields[8] : null);
                        return map;
                    }
                ));
        }
    }

    public SourceToConceptMap lookup(String sourceCode) {
        return sourceToConceptMap.get(sourceCode);
    }
}