package com.tfg.servicio_etl.services.fhir;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.servicio_etl.rest.dto.ConditionCsvRow;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Service
public class ConditionEtlService {

    private final JdbcTemplate jdbcTemplate;
    private final MappingCacheService mappingCache;

    public ConditionEtlService(JdbcTemplate jdbcTemplate, MappingCacheService mappingCache) {
        this.jdbcTemplate = jdbcTemplate;
        this.mappingCache = mappingCache;
    }

    @Transactional
    public void processConditions(List<ConditionCsvRow> rows) {
        String sql = "INSERT INTO cdm_v54.condition_occurrence (" +
                "condition_occurrence_id, person_id, condition_concept_id, condition_start_date, " +
                "condition_end_date, condition_type_concept_id, condition_source_value) " +
                "VALUES (nextval('cdm_v54.condition_seq'), ?, 0, ?::date, ?::date, 32020, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ConditionCsvRow row = rows.get(i);
                Long personId = mappingCache.getPersonId(row.getPatient());

                if (personId == null) {
                    throw new RuntimeException("Patient ID not found for UUID: " + row.getPatient());
                }

                ps.setLong(1, personId);
                ps.setString(2, row.getStart());

                if (row.getStop() == null || row.getStop().trim().isEmpty()) {
                    ps.setNull(3, Types.DATE);
                } else {
                    ps.setString(3, row.getStop());
                }
                ps.setString(4, row.getCode());
            }

            @Override
            public int getBatchSize() {
                return rows.size();
            }
        });
    }
}