package com.tfg.servicio_etl.services.fhir;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.servicio_etl.rest.dto.ObservationCsvRow;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObservationEtlService {

    private final JdbcTemplate jdbcTemplate;
    private final MappingCacheService mappingCache;

    public ObservationEtlService(JdbcTemplate jdbcTemplate, MappingCacheService mappingCache) {
        this.jdbcTemplate = jdbcTemplate;
        this.mappingCache = mappingCache;
    }

    @Transactional
    public void processObservations(List<ObservationCsvRow> rows) {
    	List<ObservationCsvRow> measurements = rows.stream()
                .filter(r -> ("numeric".equalsIgnoreCase(r.getType()) || "vital-signs".equalsIgnoreCase(r.getCategory())) 
                          && isNumeric(r.getValue()))
                .collect(Collectors.toList());

        List<ObservationCsvRow> observations = rows.stream()
                .filter(r -> (!"numeric".equalsIgnoreCase(r.getType()) && !"vital-signs".equalsIgnoreCase(r.getCategory())) 
                          || !isNumeric(r.getValue()))
                .collect(Collectors.toList());

        if (!measurements.isEmpty()) {
            String sqlMeas = "INSERT INTO cdm_v54.measurement (" +
                    "measurement_id, person_id, measurement_concept_id, measurement_date, " +
                    "measurement_datetime, measurement_type_concept_id, value_as_number, " +
                    "measurement_source_value, unit_source_value) " +
                    "VALUES (nextval('cdm_v54.measurement_seq'), ?, 0, ?::date, ?::timestamp, 32817, ?, ?, ?)";

            jdbcTemplate.batchUpdate(sqlMeas, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ObservationCsvRow row = measurements.get(i);
                    Long personId = mappingCache.getPersonId(row.getPatient());

                    if (personId == null) {
                        throw new RuntimeException("Patient ID not found for UUID: " + row.getPatient());
                    }

                    ps.setLong(1, personId);
                    ps.setString(2, row.getDate().substring(0, 10));
                    ps.setString(3, row.getDate().replace("T", " ").replace("Z", ""));

                    if (row.getValue() != null && !row.getValue().trim().isEmpty()) {
                        ps.setDouble(4, Double.parseDouble(row.getValue()));
                    } else {
                        ps.setNull(4, Types.DOUBLE);
                    }
                    ps.setString(5, row.getCode());
                    ps.setString(6, row.getUnits());
                }

                @Override
                public int getBatchSize() {
                    return measurements.size();
                }
            });
        }

        if (!observations.isEmpty()) {
            String sqlObs = "INSERT INTO cdm_v54.observation (" +
                    "observation_id, person_id, observation_concept_id, observation_date, " +
                    "observation_type_concept_id, observation_source_value) " +
                    "VALUES (nextval('cdm_v54.observation_seq'), ?, 0, ?::date, 32817, ?)";

            jdbcTemplate.batchUpdate(sqlObs, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ObservationCsvRow row = observations.get(i);
                    Long personId = mappingCache.getPersonId(row.getPatient());

                    if (personId == null) {
                        throw new RuntimeException("Patient ID not found for UUID: " + row.getPatient());
                    }

                    ps.setLong(1, personId);
                    ps.setString(2, row.getDate().substring(0, 10));
                    ps.setString(3, row.getCode());
                }

                @Override
                public int getBatchSize() {
                    return observations.size();
                }
            });
        }
    }
    
    private boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}