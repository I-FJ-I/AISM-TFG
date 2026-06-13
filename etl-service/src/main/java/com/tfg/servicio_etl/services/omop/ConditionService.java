package com.tfg.servicio_etl.services.omop;

import com.tfg.servicio_etl.model.clinical.ConditionOccurrence;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionService {

    private final JdbcTemplate jdbcTemplate;

    public ConditionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConditionOccurrence> getAllConditions() {
        String sql = "SELECT * FROM cdm_v54.condition_occurrence";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ConditionOccurrence c = new ConditionOccurrence();
            c.setConditionOccurrenceId(rs.getLong("condition_occurrence_id"));
            c.setPersonId(rs.getLong("person_id"));
            c.setConditionConceptId((int) rs.getLong("condition_concept_id"));
            if (rs.getDate("condition_start_date") != null) {
                c.setConditionStartDate(rs.getDate("condition_start_date").toLocalDate());
            }
            if (rs.getDate("condition_end_date") != null) {
                c.setConditionEndDate(rs.getDate("condition_end_date").toLocalDate());
            }
            c.setConditionTypeConceptId((int) rs.getLong("condition_type_concept_id"));
            c.setConditionSourceValue(rs.getString("condition_source_value"));
            return c;
        });
    }
}