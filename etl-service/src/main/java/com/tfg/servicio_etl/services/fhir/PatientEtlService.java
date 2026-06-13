package com.tfg.servicio_etl.services.fhir;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.servicio_etl.rest.dto.PatientCsvRow;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientEtlService {

    private final JdbcTemplate jdbcTemplate;
    private final ConceptVocabularyService conceptService;

    public PatientEtlService(JdbcTemplate jdbcTemplate, ConceptVocabularyService conceptService) {
        this.jdbcTemplate = jdbcTemplate;
        this.conceptService = conceptService;
    }

    @Transactional
    public void processPatients(List<PatientCsvRow> rows) {
        String sqlPerson = "INSERT INTO cdm_v54.person (" +
                "person_id, gender_concept_id, year_of_birth, month_of_birth, day_of_birth, " +
                "race_concept_id, ethnicity_concept_id, person_source_value, gender_source_value) " +
                "VALUES (nextval('cdm_v54.person_seq'), ?, ?, ?, ?, ?, 0, ?, ?)";

        jdbcTemplate.batchUpdate(sqlPerson, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PatientCsvRow row = rows.get(i);
                String[] dateParts = row.getBirthdate().split("-");

                ps.setLong(1, conceptService.getGenderConceptId(row.getGender()));
                ps.setInt(2, Integer.parseInt(dateParts[0]));
                ps.setInt(3, Integer.parseInt(dateParts[1]));
                ps.setInt(4, Integer.parseInt(dateParts[2]));
                ps.setLong(5, conceptService.getRaceConceptId(row.getRace()));
                ps.setString(6, row.getId());
                ps.setString(7, row.getGender());
            }

            @Override
            public int getBatchSize() {
                return rows.size();
            }
        });

        List<PatientCsvRow> deceased = rows.stream()
                .filter(r -> r.getDeathdate() != null && !r.getDeathdate().trim().isEmpty())
                .collect(Collectors.toList());

        if (!deceased.isEmpty()) {
            String sqlDeath = "INSERT INTO cdm_v54.death (person_id, death_date) " +
                    "SELECT person_id, ?::date FROM cdm_v54.person WHERE person_source_value = ?";

            jdbcTemplate.batchUpdate(sqlDeath, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, deceased.get(i).getDeathdate());
                    ps.setString(2, deceased.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return deceased.size();
                }
            });
        }
    }
}