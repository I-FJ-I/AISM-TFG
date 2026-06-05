package com.tfg.servicio_etl.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.tfg.servicio_etl.rest.dto.OMOPBundle;

@RestController
@RequestMapping("/etl")
public class ETLController implements ETLApi {

	@Override
	@GetMapping("/export")
	public EntityModel<OMOPBundle> exportData() {
		// TODO Auto-generated method stub
		return null;
	}

}
