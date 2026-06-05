package com.tfg.servicio_fhir.documents;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "specimens")
public class SpecimenDocument {

    @Id
    private String id;
    private String resourceType;
    private List<Map<String, Object>> identifier;
    private String status;
    private Map<String, Object> type;              
    private Map<String, Object> subject;         
    private String receivedTime;
    private Map<String, Object> collection;        
    private List<Map<String, Object>> processing;
    private List<Map<String, Object>> container;
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
	public Map<String, Object> getType() {
		return type;
	}
	public void setType(Map<String, Object> type) {
		this.type = type;
	}
	public Map<String, Object> getSubject() {
		return subject;
	}
	public void setSubject(Map<String, Object> subject) {
		this.subject = subject;
	}
	public String getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}
	public Map<String, Object> getCollection() {
		return collection;
	}
	public void setCollection(Map<String, Object> collection) {
		this.collection = collection;
	}
	public List<Map<String, Object>> getProcessing() {
		return processing;
	}
	public void setProcessing(List<Map<String, Object>> processing) {
		this.processing = processing;
	}
	public List<Map<String, Object>> getContainer() {
		return container;
	}
	public void setContainer(List<Map<String, Object>> container) {
		this.container = container;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
    
}