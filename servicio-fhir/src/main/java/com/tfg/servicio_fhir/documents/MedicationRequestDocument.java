package com.tfg.servicio_fhir.documents;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medication_requests")
public class MedicationRequestDocument {

    @Id
    private String id;
    private String resourceType;
    private List<Map<String, Object>> identifier;
    private String status;
    private String intent;
    private Map<String, Object> medicationCodeableConcept;  
    private Map<String, Object> subject;                    
    private Map<String, Object> encounter;
    private String authoredOn;
    private Map<String, Object> requester;
    private List<Map<String, Object>> dosageInstruction;
    private Map<String, Object> dispenseRequest;
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
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public Map<String, Object> getMedicationCodeableConcept() {
		return medicationCodeableConcept;
	}
	public void setMedicationCodeableConcept(Map<String, Object> medicationCodeableConcept) {
		this.medicationCodeableConcept = medicationCodeableConcept;
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
	public String getAuthoredOn() {
		return authoredOn;
	}
	public void setAuthoredOn(String authoredOn) {
		this.authoredOn = authoredOn;
	}
	public Map<String, Object> getRequester() {
		return requester;
	}
	public void setRequester(Map<String, Object> requester) {
		this.requester = requester;
	}
	public List<Map<String, Object>> getDosageInstruction() {
		return dosageInstruction;
	}
	public void setDosageInstruction(List<Map<String, Object>> dosageInstruction) {
		this.dosageInstruction = dosageInstruction;
	}
	public Map<String, Object> getDispenseRequest() {
		return dispenseRequest;
	}
	public void setDispenseRequest(Map<String, Object> dispenseRequest) {
		this.dispenseRequest = dispenseRequest;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
    
}

