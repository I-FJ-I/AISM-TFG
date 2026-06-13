package com.tfg.servicio_etl.services.fhir;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MappingCacheService {

    private final JdbcTemplate jdbcTemplate;
    private Map<String, Long> patientUuidToIdMap;

    public MappingCacheService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void reloadPatientMappings() {
        String sql = "SELECT person_source_value, person_id FROM cdm_v54.person";
        
        patientUuidToIdMap = jdbcTemplate.query(sql, rs -> {
            Map<String, Long> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("person_source_value"), rs.getLong("person_id"));
            }
            return map;
        });
    }

    public Long getPersonId(String patientUuid) {
        return patientUuidToIdMap != null ? patientUuidToIdMap.get(patientUuid) : null;
    }
}