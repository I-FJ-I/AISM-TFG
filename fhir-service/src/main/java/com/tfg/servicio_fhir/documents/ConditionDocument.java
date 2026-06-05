package com.tfg.servicio_fhir.documents;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conditions")
public class ConditionDocument {

    @Id
    private String id;
    private String resourceType;
    private List<Map<String, Object>> identifier;
    private Map<String, Object> clinicalStatus;
    private Map<String, Object> verificationStatus;
    private List<Map<String, Object>> category;
    private Map<String, Object> severity;
    private Map<String, Object> code;              
    private Map<String, Object> subject;           
    private Map<String, Object> encounter;         
    private String onsetDateTime;
    private String abatementDateTime;
    private String recordedDate;
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
	public Map<String, Object> getClinicalStatus() {
		return clinicalStatus;
	}
	public void setClinicalStatus(Map<String, Object> clinicalStatus) {
		this.clinicalStatus = clinicalStatus;
	}
	public Map<String, Object> getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(Map<String, Object> verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public List<Map<String, Object>> getCategory() {
		return category;
	}
	public void setCategory(List<Map<String, Object>> category) {
		this.category = category;
	}
	public Map<String, Object> getSeverity() {
		return severity;
	}
	public void setSeverity(Map<String, Object> severity) {
		this.severity = severity;
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
	public String getOnsetDateTime() {
		return onsetDateTime;
	}
	public void setOnsetDateTime(String onsetDateTime) {
		this.onsetDateTime = onsetDateTime;
	}
	public String getAbatementDateTime() {
		return abatementDateTime;
	}
	public void setAbatementDateTime(String abatementDateTime) {
		this.abatementDateTime = abatementDateTime;
	}
	public String getRecordedDate() {
		return recordedDate;
	}
	public void setRecordedDate(String recordedDate) {
		this.recordedDate = recordedDate;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
    
}