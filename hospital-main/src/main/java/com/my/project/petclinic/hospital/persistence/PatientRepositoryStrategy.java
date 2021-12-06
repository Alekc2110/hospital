package com.my.project.petclinic.hospital.persistence;

import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.api.config.RequestBackground;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository(value = "patientRepositoryStrategy")
class PatientRepositoryStrategy implements PatientRepository {

    private final PatientRepository jdbcPatientRepository;
    private final PatientRepository jpaPatientRepository;
    private final RequestBackground condition;


    public PatientRepositoryStrategy(@Qualifier("jdbcPatientRepository") PatientRepository jdbcPatientRepository,
                                     @Qualifier("jpaPatientRepository") PatientRepository jpaPatientRepository, RequestBackground condition) {
        this.jdbcPatientRepository = jdbcPatientRepository;
        this.jpaPatientRepository = jpaPatientRepository;
        this.condition = condition;
    }

    @Override
    public List<Patient> findAll() {
        final Boolean jdbc = condition.getJdbc();
        if (Boolean.TRUE.equals(jdbc)) {
            return jdbcPatientRepository.findAll();
        } else {
            return jpaPatientRepository.findAll();
        }
    }

    @Override
    public Patient save(Patient patient) {
        final Boolean jdbc = condition.getJdbc();
        if (Boolean.TRUE.equals(jdbc)) {
            return jdbcPatientRepository.save(patient);
        } else {
            return jpaPatientRepository.save(patient);
        }
    }

    @Override
    public Patient update(Patient patient) {
        final Boolean jdbc = condition.getJdbc();
        if (Boolean.TRUE.equals(jdbc)) {
            return jdbcPatientRepository.update(patient);
        } else {
            return jpaPatientRepository.update(patient);
        }
    }

    @Override
    public Patient findById(Long id) {
        final Boolean jdbc = condition.getJdbc();
        if (Boolean.TRUE.equals(jdbc)) {
            return jdbcPatientRepository.findById(id);
        } else {
            return jpaPatientRepository.findById(id);
        }
    }
}
