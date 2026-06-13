package com.tfg.servicio_etl.model.health_system_data;

public class Location {

    private Long locationId;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String county;
    private String locationSourceValue;
    private Long countryConceptId;
    private String countrySourceValue;
    private Double latitude;
    private Double longitude;

    public Location() {
    }

    public Location(Long locationId, String address1, String address2, String city, String state, String zip, String county, String locationSourceValue, Long countryConceptId, String countrySourceValue, Double latitude, Double longitude) {
        this.locationId = locationId;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.county = county;
        this.locationSourceValue = locationSourceValue;
        this.countryConceptId = countryConceptId;
        this.countrySourceValue = countrySourceValue;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLocationSourceValue() {
        return locationSourceValue;
    }

    public void setLocationSourceValue(String locationSourceValue) {
        this.locationSourceValue = locationSourceValue;
    }

    public Long getCountryConceptId() {
        return countryConceptId;
    }

    public void setCountryConceptId(Long countryConceptId) {
        this.countryConceptId = countryConceptId;
    }

    public String getCountrySourceValue() {
        return countrySourceValue;
    }

    public void setCountrySourceValue(String countrySourceValue) {
        this.countrySourceValue = countrySourceValue;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}