package com.tfg.servicio_etl.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.tfg.servicio_etl.rest.dto.OMOPBundle;

public interface ETLApi {
	@GetMapping("/export")
	@Operation(summary = "Exportar datos en formato OMOP", description = "Exporta los datos transformados en formato OMOP para su uso en análisis y estudios.",
	responses = {
		@ApiResponse(responseCode = "200", description = "Datos exportados correctamente"),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public EntityModel<OMOPBundle> exportData();
	
	@PostMapping(value = "/load", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Ejecutar proceso ETL hacia OMOP CDM", description = "Procesa los ficheros CSV de pacientes, condiciones y observaciones para transformarlos e insertarlos masivamente en el esquema OMOP.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Proceso ETL completado con éxito"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor durante la ejecución")
    })
	public ResponseEntity<String> loadData(@RequestParam("patientsFile") MultipartFile patientsFile,
							            @RequestParam("conditionsFile") MultipartFile conditionsFile,
							            @RequestParam("observationsFile") MultipartFile observationsFile);
}
