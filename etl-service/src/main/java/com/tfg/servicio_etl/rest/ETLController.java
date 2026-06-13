package com.tfg.servicio_etl.rest;

import java.util.List;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tfg.servicio_etl.rest.dto.ConditionCsvRow;
import com.tfg.servicio_etl.rest.dto.OMOPBundle;
import com.tfg.servicio_etl.rest.dto.ObservationCsvRow;
import com.tfg.servicio_etl.rest.dto.PatientCsvRow;
import com.tfg.servicio_etl.services.ExportService;
import com.tfg.servicio_etl.services.fhir.ConditionEtlService;
import com.tfg.servicio_etl.services.fhir.MappingCacheService;
import com.tfg.servicio_etl.services.fhir.ObservationEtlService;
import com.tfg.servicio_etl.services.fhir.PatientEtlService;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
@RequestMapping("/etl")
public class ETLController implements ETLApi {
	
	private final PatientEtlService patientService;
    private final ConditionEtlService conditionService;
    private final ObservationEtlService observationService;
    private final MappingCacheService mappingCache;
    private final ExportService exportService;
    
    @Autowired
    public ETLController(PatientEtlService patientService, 
				            ConditionEtlService conditionService, 
				            ObservationEtlService observationService, 
				            MappingCacheService mappingCache,
				            ExportService exportService) {
		this.patientService = patientService;
		this.conditionService = conditionService;
		this.observationService = observationService;
		this.mappingCache = mappingCache;
		this.exportService = exportService;
	}

	@Override
	@GetMapping("/export")
	public EntityModel<OMOPBundle> exportData() {
		OMOPBundle bundle = exportService.export();
        return EntityModel.of(bundle);
	}
	
	@PostMapping("/load")
    public ResponseEntity<String> loadData(
            @RequestParam("patientsFile") MultipartFile patientsFile,
            @RequestParam("conditionsFile") MultipartFile conditionsFile,
            @RequestParam("observationsFile") MultipartFile observationsFile) {

        long startTime = System.currentTimeMillis();

        try {
            // PHASE 1: Extract - Parse CSV to Memory
            List<PatientCsvRow> patients = parseCsv(patientsFile, PatientCsvRow.class);
            List<ConditionCsvRow> conditions = parseCsv(conditionsFile, ConditionCsvRow.class);
            List<ObservationCsvRow> observations = parseCsv(observationsFile, ObservationCsvRow.class);

            // PHASE 2: Transform & Load - STRICT EXECUTION ORDER
            
            // 2.1 Load Patients (PERSON and DEATH tables)
            patientService.processPatients(patients);

            // 2.2 Refresh in-memory cache (CRITICAL for foreign keys)
            mappingCache.reloadPatientMappings();

            // 2.3 Load Conditions (CONDITION_OCCURRENCE table)
            conditionService.processConditions(conditions);

            // 2.4 Load Observations (MEASUREMENT and OBSERVATION tables)
            observationService.processObservations(observations);

            // PHASE 3: Completion and Metrics
            long endTime = System.currentTimeMillis();
            long durationSeconds = (endTime - startTime) / 1000;
            
            String successMessage = String.format(
                "ETL successfully completed in %d seconds. Inserted: %d patients, %d conditions, %d observations.",
                durationSeconds, patients.size(), conditions.size(), observations.size()
            );
            
            return ResponseEntity.ok(successMessage);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ETL execution failed: " + e.getMessage());
        }
    }
	
	/**
     * Generic utility method to parse any CSV into a List of DTOs using OpenCSV.
     */
    private <T> List<T> parseCsv(MultipartFile file, Class<T> clazz) throws Exception {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(',') 
                    .build()
                    .parse();
        }
    }

}
