package com.my.project.petclinic.hospital.persistence.jdbcrepository;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.persistence.DoctorRepository;
import com.my.project.petclinic.hospital.persistence.jdbcrepository.keyholder.KeyHolderFactory;
import com.my.project.petclinic.hospital.persistence.jdbcrepository.resultsetextractor.DoctorResultSetExtractor;
import com.my.project.petclinic.hospital.persistence.jdbcrepository.resultsetextractor.ListDoctorsResultSetExtractor;
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
@Repository(value = "jdbcDoctorRepository")
class JdbcDoctorRepository implements DoctorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ListDoctorsResultSetExtractor listResultSetExtractor;
    private final DoctorResultSetExtractor doctorResultSetExtractor;
    private final KeyHolderFactory keyHolderFactory;


    @Override
    public List<Doctor> findAll() {
        return jdbcTemplate.query(Queries.SELECT_ALL_DOCTORS, listResultSetExtractor);
    }

    @Override
    @Transactional
    public Doctor save(Doctor doctor) {
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        long doctorId = 0;
        Map<String, Object> keys;
        jdbcTemplate.update(con -> {
            final PreparedStatement ps = con.prepareStatement(Queries.SAVE_DOCTOR, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getPosition());
            ps.setString(3, doctor.getSurName());
            return ps;
        }, keyHolder);
        if (Objects.requireNonNull(keyHolder.getKeys()).size() > 1) {
            keys = keyHolder.getKeys();
            if(keys != null){
                doctorId = ((Integer) keys.get("d_id")).longValue();
            }

        } else {
            doctorId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
        return findById(doctorId);
    }

    @Override
    @Transactional
    public Doctor update(Doctor doctor) {
        jdbcTemplate.update(Queries.UPDATE_DOCTOR, doctor.getName(), doctor.getPosition(), doctor.getSurName(), doctor.getId());
        return findById(doctor.getId());

    }

    @Override
    public Doctor findById(Long id) {
        final Optional<Doctor> doctor = Optional.ofNullable(jdbcTemplate.query(Queries.GET_DOCTOR_BY_ID, doctorResultSetExtractor, id));
        return doctor.orElseThrow(() -> new EntityNotFoundException(String.format("doctor with id %s not found", id)));
    }
}
