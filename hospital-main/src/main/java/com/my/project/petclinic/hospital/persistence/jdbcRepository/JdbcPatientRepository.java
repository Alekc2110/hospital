package com.my.project.petclinic.hospital.persistence.jdbcRepository;

import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.persistence.PatientRepository;
import com.my.project.petclinic.hospital.persistence.jdbcRepository.keyHolder.KeyHolderFactory;
import com.my.project.petclinic.hospital.persistence.jdbcRepository.resultSetExtractor.ListPatientsResultSetExtractor;
import com.my.project.petclinic.hospital.persistence.jdbcRepository.resultSetExtractor.PatientResultSetExtractor;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Repository(value = "jdbcPatientRepository")
class JdbcPatientRepository implements PatientRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ListPatientsResultSetExtractor listResultSetExtractor;
    private final PatientResultSetExtractor patientResultSetExtractor;
    private final KeyHolderFactory keyHolderFactory;

    @Override
    public List<Patient> findAll() {
        return jdbcTemplate.query(Queries.SELECT_ALL_PATIENTS, listResultSetExtractor);
    }

    @Override
    @Transactional
    public Patient save(Patient patient) {
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        long patientId;
        Map<String, Object> keys;
        jdbcTemplate.update(con -> {
            final PreparedStatement ps = con.prepareStatement(Queries.SAVE_PATIENT, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, patient.getAge());
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getSurName());
            return ps;
        }, keyHolder);
        if (Objects.requireNonNull(keyHolder.getKeys()).size() > 1) {
            keys = keyHolder.getKeys();
            patientId = ((Integer) keys.get("p_id")).longValue();
        } else {
            patientId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
        return findById(patientId);
    }

    @Override
    @Transactional
    public Patient update(Patient patient) {
        jdbcTemplate.update(Queries.UPDATE_PATIENT, patient.getAge(), patient.getName(), patient.getSurName(), patient.getId());
        return findById(patient.getId());

    }

    @Override
    public Patient findById(Long id) {
        final Optional<Patient> doctor = Optional.ofNullable(jdbcTemplate.query(Queries.GET_PATIENT_BY_ID, patientResultSetExtractor, id));
        return doctor.orElseThrow(() -> new EntityNotFoundException(String.format("patient with id %s not found", id)));
    }
}
