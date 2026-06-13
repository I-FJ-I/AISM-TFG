package com.tfg.servicio_etl.services.omop;

import com.tfg.servicio_etl.model.clinical.Death;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeathService {

    private final JdbcTemplate jdbcTemplate;

    public DeathService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Death> getAllDeaths() {
        String sql = "SELECT * FROM cdm_v54.death";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Death d = new Death();
            d.setPersonId(rs.getLong("person_id"));
            if (rs.getDate("death_date") != null) {
                d.setDeathDate(rs.getDate("death_date").toLocalDate());
            }
            return d;
        });
    }
}