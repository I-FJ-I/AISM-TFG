package com.tfg.servicio_fhir.documents;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "procedures")
public class ProcedureDocument {

    @Id
    private String id;
    private String resourceType;
    private List<Map<String, Object>> identifier;
    private String status;
    private Map<String, Object> code;             
    private Map<String, Object> subject;
    private Map<String, Object> encounter;
    private String performedDateTime;
    private Map<String, Object> performedPeriod;
    private List<Map<String, Object>> performer;
    private List<Map<String, Object>> reasonCode;
    private List<Map<String, Object>> bodySite;
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
	public Map<String, Object> getCode() {
		return code;
	}
	public void setCode(Map<String, Object> code) {
		this.code = code;
	}
	public Map<String, Object> getSubject() {
		return subject;
	}
	public void setSubject(Map<String, Object> subject) {
		this.subject = subject;
	}
	public Map<String, Object> getEncounter() {
		return encounter;
	}
	public void setEncounter(Map<String, Object> encounter) {
		this.encounter = encounter;
	}
	public String getPerformedDateTime() {
		return performedDateTime;
	}
	public void setPerformedDateTime(String performedDateTime) {
		this.performedDateTime = performedDateTime;
	}
	public Map<String, Object> getPerformedPeriod() {
		return performedPeriod;
	}
	public void setPerformedPeriod(Map<String, Object> performedPeriod) {
		this.performedPeriod = performedPeriod;
	}
	public List<Map<String, Object>> getPerformer() {
		return performer;
	}
	public void setPerformer(List<Map<String, Object>> performer) {
		this.performer = performer;
	}
	public List<Map<String, Object>> getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(List<Map<String, Object>> reasonCode) {
		this.reasonCode = reasonCode;
	}
	public List<Map<String, Object>> getBodySite() {
		return bodySite;
	}
	public void setBodySite(List<Map<String, Object>> bodySite) {
		this.bodySite = bodySite;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
    
}