package com.tfg.servicio_etl.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
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
	EntityModel<OMOPBundle> exportData();
}
