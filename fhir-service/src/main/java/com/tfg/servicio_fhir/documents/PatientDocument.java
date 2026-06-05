package com.tfg.servicio_fhir.documents;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
public class PatientDocument {

	@Id
	private String id;
	private String resourceType;
	private List<Map<String, Object>> identifier;
   	private Boolean active;
   	private List<Map<String, Object>> name;
   	private List<Map<String, Object>> telecom;
   	private String gender;
   	private String birthDate;
   	private List<Map<String, Object>> address;
   	private Map<String, Object> maritalStatus;
   	private List<Map<String, Object>> communication;
   	private List<Map<String, Object>> extension;   // race, ethnicity, etc.
   	private String deceasedDateTime;
   	private Boolean deceasedBoolean;
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
 	public Boolean getActive() {
 		return active;
 	}
 	public void setActive(Boolean active) {
 		this.active = active;
 	}
 	public List<Map<String, Object>> getName() {
 		return name;
 	}
 	public void setName(List<Map<String, Object>> name) {
 		this.name = name;
 	}
 	public List<Map<String, Object>> getTelecom() {
 		return telecom;
 	}
 	public void setTelecom(List<Map<String, Object>> telecom) {
 		this.telecom = telecom;
 	}
 	public String getGender() {
 		return gender;
 	}
 	public void setGender(String gender) {
 		this.gender = gender;
 	}
 	public String getBirthDate() {
 		return birthDate;
 	}
 	public void setBirthDate(String birthDate) {
 		this.birthDate = birthDate;
 	}
 	public List<Map<String, Object>> getAddress() {
 		return address;
 	}
 	public void setAddress(List<Map<String, Object>> address) {
 		this.address = address;
 	}
 	public Map<String, Object> getMaritalStatus() {
 		return maritalStatus;
 	}
 	public void setMaritalStatus(Map<String, Object> maritalStatus) {
 		this.maritalStatus = maritalStatus;
 	}
 	public List<Map<String, Object>> getCommunication() {
 		return communication;
 	}
 	public void setCommunication(List<Map<String, Object>> communication) {
 		this.communication = communication;
 	}
 	public List<Map<String, Object>> getExtension() {
 		return extension;
 	}
 	public void setExtension(List<Map<String, Object>> extension) {
 		this.extension = extension;
 	}
 	public String getDeceasedDateTime() {
 		return deceasedDateTime;
 	}
 	public void setDeceasedDateTime(String deceasedDateTime) {
 		this.deceasedDateTime = deceasedDateTime;
 	}
 	public Boolean getDeceasedBoolean() {
 		return deceasedBoolean;
 	}
 	public void setDeceasedBoolean(Boolean deceasedBoolean) {
 		this.deceasedBoolean = deceasedBoolean;
 	}
 	public Map<String, Object> getMeta() {
 		return meta;
 	}
 	public void setMeta(Map<String, Object> meta) {
 		this.meta = meta;
 	}
}
