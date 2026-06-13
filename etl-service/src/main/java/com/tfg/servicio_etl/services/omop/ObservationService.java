package com.tfg.servicio_etl.services.omop;

import com.tfg.servicio_etl.model.clinical.Observation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservationService {

    private final JdbcTemplate jdbcTemplate;

    public ObservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Observation> getAllObservations() {
        String sql = "SELECT * FROM cdm_v54.observation";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Observation o = new Observation();
            o.setObservationId(rs.getLong("observation_id"));
            o.setPersonId(rs.getLong("person_id"));
            o.setObservationConceptId((int) rs.getLong("observation_concept_id"));
            if (rs.getDate("observation_date") != null) {
                o.setObservationDate(rs.getDate("observation_date").toLocalDate());
            }
            o.setObservationTypeConceptId((int) rs.getLong("observation_type_concept_id"));
            o.setObservationSourceValue(rs.getString("observation_source_value"));
            return o;
        });
    }
}