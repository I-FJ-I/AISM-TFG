package com.tfg.servicio_etl.rest.dto;

public class ObservationCsvRow {

    private String date;
    private String patient;
    private String encounter;
    private String category;
    private String code;
    private String description;
    private String value;
    private String units;
    private String type;

    public ObservationCsvRow() {
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }

    public String getEncounter() { return encounter; }
    public void setEncounter(String encounter) { this.encounter = encounter; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getUnits() { return units; }
    public void setUnits(String units) { this.units = units; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}