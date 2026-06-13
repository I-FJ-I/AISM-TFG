package com.tfg.servicio_etl.rest.dto;

public class ConditionCsvRow {

    private String start;
    private String stop;
    private String patient;
    private String encounter;
    private String system;
    private String code;
    private String description;

    public ConditionCsvRow() {
    }

    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }

    public String getStop() { return stop; }
    public void setStop(String stop) { this.stop = stop; }

    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }

    public String getEncounter() { return encounter; }
    public void setEncounter(String encounter) { this.encounter = encounter; }

    public String getSystem() { return system; }
    public void setSystem(String system) { this.system = system; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}