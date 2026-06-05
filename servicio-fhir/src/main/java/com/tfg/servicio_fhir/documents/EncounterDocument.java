package com.tfg.servicio_fhir.documents;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "encounters")
public class EncounterDocument {
	
	@Id
	private String id;
	private String resourceType;
	private List<Map<String, Object>> identifier;
	private String status;
	private Map<String, Object> encountersClass;             
	private List<Map<String, Object>> type;
	private Map<String, Object> subject;         
	private Map<String, Object> period;        
	private List<Map<String, Object>> participant;
	private List<Map<String, Object>> reasonCode;
	private Map<String, Object> hospitalization;
	private List<Map<String, Object>> location;
	private Map<String, Object> serviceProvider;  
	private Map<String, Object> meta;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public List<Map<String, Object>> getIdentifier() {
		return identifier;
	}
	public void setIdentifier(List<Map<String, Object>> identifier) {
		this.identifier = identifier;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, Object> getEncountersClass() {
		return encountersClass;
	}
	public void setEncountersClass(Map<String, Object> encountersClass) {
		this.encountersClass = encountersClass;
	}
	public List<Map<String, Object>> getType() {
		return type;
	}
	public void setType(List<Map<String, Object>> type) {
		this.type = type;
	}
	public Map<String, Object> getSubject() {
		return subject;
	}
	public void setSubject(Map<String, Object> subject) {
		this.subject = subject;
	}
	public Map<String, Object> getPeriod() {
		return period;
	}
	public void setPeriod(Map<String, Object> period) {
		this.period = period;
	}
	public List<Map<String, Object>> getParticipant() {
		return participant;
	}
	public void setParticipant(List<Map<String, Object>> participant) {
		this.participant = participant;
	}
	public List<Map<String, Object>> getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(List<Map<String, Object>> reasonCode) {
		this.reasonCode = reasonCode;
	}
	public Map<String, Object> getHospitalization() {
		return hospitalization;
	}
	public void setHospitalization(Map<String, Object> hospitalization) {
		this.hospitalization = hospitalization;
	}
	public List<Map<String, Object>> getLocation() {
		return location;
	}
	public void setLocation(List<Map<String, Object>> location) {
		this.location = location;
	}
	public Map<String, Object> getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(Map<String, Object> serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	 
}