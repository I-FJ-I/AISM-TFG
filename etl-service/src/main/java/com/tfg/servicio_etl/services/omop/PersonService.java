package com.tfg.servicio_etl.services.omop;

import com.tfg.servicio_etl.model.clinical.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final JdbcTemplate jdbcTemplate;

    public PersonService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPersons() {
        String sql = "SELECT * FROM cdm_v54.person";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Person p = new Person();
            p.setPersonId(rs.getLong("person_id"));
            p.setGenderConceptId((int) rs.getLong("gender_concept_id"));
            p.setYearOfBirth(rs.getInt("year_of_birth"));
            p.setMonthOfBirth(rs.getInt("month_of_birth"));
            p.setDayOfBirth(rs.getInt("day_of_birth"));
            p.setRaceConceptId((int) rs.getLong("race_concept_id"));
            p.setEthnicityConceptId((int) rs.getLong("ethnicity_concept_id"));
            p.setPersonSourceValue(rs.getString("person_source_value"));
            p.setGenderSourceValue(rs.getString("gender_source_value"));
            return p;
        });
    }
}