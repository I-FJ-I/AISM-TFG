package com.tfg.servicio_etl.services;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
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
        Path path = resourceLoader.getResource("classpath:vocabularies/DICTIONARY.csv")
                                  .getFile()
                                  .toPath();

        sourceToConceptMap = Files.lines(path)
            .skip(1)
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
                    map.setInvalidReason(fields.length > 8 ? fields[8] : null); // Seguridad extra
                    return map;
                }
            ));
    }

    public SourceToConceptMap lookup(String sourceCode) {
        return sourceToConceptMap.get(sourceCode);
    }
}