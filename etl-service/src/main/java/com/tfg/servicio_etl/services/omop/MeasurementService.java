package com.tfg.servicio_etl.services.omop;

import com.tfg.servicio_etl.model.clinical.Measurement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    private final JdbcTemplate jdbcTemplate;

    public MeasurementService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Measurement> getAllMeasurements() {
        String sql = "SELECT * FROM cdm_v54.measurement";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Measurement m = new Measurement();
            m.setMeasurementId(rs.getLong("measurement_id"));
            m.setPersonId(rs.getLong("person_id"));
            m.setMeasurementConceptId((int) rs.getLong("measurement_concept_id"));
            if (rs.getDate("measurement_date") != null) {
                m.setMeasurementDate(rs.getDate("measurement_date").toLocalDate());
            }
            m.setMeasurementTypeConceptId((int) rs.getLong("measurement_type_concept_id"));
            m.setValueAsNumber(rs.getDouble("value_as_number"));
            if (rs.wasNull()) {
                m.setValueAsNumber(null);
            }
            m.setMeasurementSourceValue(rs.getString("measurement_source_value"));
            m.setUnitSourceValue(rs.getString("unit_source_value"));
            return m;
        });
    }
}