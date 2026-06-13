package com.tfg.servicio_etl.services.omop;

import com.tfg.servicio_etl.model.health_system_data.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final JdbcTemplate jdbcTemplate;

    public LocationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Location> getAllLocations() {
        String sql = "SELECT * FROM cdm_v54.location";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Location l = new Location();
            l.setLocationId(rs.getLong("location_id"));
            l.setAddress1(rs.getString("address_1"));
            l.setAddress2(rs.getString("address_2"));
            l.setCity(rs.getString("city"));
            l.setState(rs.getString("state"));
            l.setZip(rs.getString("zip"));
            l.setCounty(rs.getString("county"));
            l.setLocationSourceValue(rs.getString("location_source_value"));
            l.setCountryConceptId(rs.getLong("country_concept_id"));
            l.setCountrySourceValue(rs.getString("country_source_value"));
            l.setLatitude(rs.getDouble("latitude"));
            if (rs.wasNull()) {
                l.setLatitude(null);
            }
            l.setLongitude(rs.getDouble("longitude"));
            if (rs.wasNull()) {
                l.setLongitude(null);
            }
            return l;
        });
    }
}