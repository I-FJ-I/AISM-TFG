package com.tfg.servicio_fhir.documents;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "observations")
public class ObservationDocument {

    @Id
    private String id;
    private String resourceType;
    private List<Map<String, Object>> identifier;
    private String status;
    private List<Map<String, Object>> category;   
    private Map<String, Object> code;             
    private Map<String, Object> subject;
    private Map<String, Object> encounter;
    private String effectiveDateTime;
    private String issued;
    private Map<String, Object> valueQuantity;
    private Map<String, Object> valueCodeableConcept;
    private String valueString;
    private Map<String, Object> referenceRange;
    private List<Map<String, Object>> interpretation;
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
	public List<Map<String, Object>> getCategory() {
		return category;
	}
	public void setCategory(List<Map<String, Object>> category) {
		this.category = category;
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
	public String getEffectiveDateTime() {
		return effectiveDateTime;
	}
	public void setEffectiveDateTime(String effectiveDateTime) {
		this.effectiveDateTime = effectiveDateTime;
	}
	public String getIssued() {
		return issued;
	}
	public void setIssued(String issued) {
		this.issued = issued;
	}
	public Map<String, Object> getValueQuantity() {
		return valueQuantity;
	}
	public void setValueQuantity(Map<String, Object> valueQuantity) {
		this.valueQuantity = valueQuantity;
	}
	public Map<String, Object> getValueCodeableConcept() {
		return valueCodeableConcept;
	}
	public void setValueCodeableConcept(Map<String, Object> valueCodeableConcept) {
		this.valueCodeableConcept = valueCodeableConcept;
	}
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}
	public Map<String, Object> getReferenceRange() {
		return referenceRange;
	}
	public void setReferenceRange(Map<String, Object> referenceRange) {
		this.referenceRange = referenceRange;
	}
	public List<Map<String, Object>> getInterpretation() {
		return interpretation;
	}
	public void setInterpretation(List<Map<String, Object>> interpretation) {
		this.interpretation = interpretation;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
    
}