package com.tfg.servicio_etl.rest.dto;

public class PatientCsvRow {

    private String id;
    private String birthdate;
    private String deathdate;
    private String ssn;
    private String drivers;
    private String passport;
    private String prefix;
    private String first;
    private String middle;
    private String last;
    private String suffix;
    private String maiden;
    private String marital;
    private String race;
    private String ethnicity;
    private String gender;
    private String birthplace;
    private String address;
    private String city;
    private String state;
    private String county;
    private String fips;
    private String zip;
    private String lat;
    private String lon;
    private String healthcareExpenses;
    private String healthcareCoverage;
    private String income;

    public PatientCsvRow() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    public String getDeathdate() { return deathdate; }
    public void setDeathdate(String deathdate) { this.deathdate = deathdate; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getDrivers() { return drivers; }
    public void setDrivers(String drivers) { this.drivers = drivers; }

    public String getPassport() { return passport; }
    public void setPassport(String passport) { this.passport = passport; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public String getFirst() { return first; }
    public void setFirst(String first) { this.first = first; }

    public String getMiddle() { return middle; }
    public void setMiddle(String middle) { this.middle = middle; }

    public String getLast() { return last; }
    public void setLast(String last) { this.last = last; }

    public String getSuffix() { return suffix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }

    public String getMaiden() { return maiden; }
    public void setMaiden(String maiden) { this.maiden = maiden; }

    public String getMarital() { return marital; }
    public void setMarital(String marital) { this.marital = marital; }

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }

    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthplace() { return birthplace; }
    public void setBirthplace(String birthplace) { this.birthplace = birthplace; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    public String getFips() { return fips; }
    public void setFips(String fips) { this.fips = fips; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public String getLat() { return lat; }
    public void setLat(String lat) { this.lat = lat; }

    public String getLon() { return lon; }
    public void setLon(String lon) { this.lon = lon; }

    public String getHealthcareExpenses() { return healthcareExpenses; }
    public void setHealthcareExpenses(String healthcareExpenses) { this.healthcareExpenses = healthcareExpenses; }

    public String getHealthcareCoverage() { return healthcareCoverage; }
    public void setHealthcareCoverage(String healthcareCoverage) { this.healthcareCoverage = healthcareCoverage; }

    public String getIncome() { return income; }
    public void setIncome(String income) { this.income = income; }
}