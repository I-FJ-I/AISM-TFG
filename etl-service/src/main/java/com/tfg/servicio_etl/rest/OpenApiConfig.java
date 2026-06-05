package com.tfg.servicio_etl.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Servicio de ETL")
						.description("Transformación de datos desde una base de datos legacy a OMOP")
						.version("1.0.0"));
	}
}
