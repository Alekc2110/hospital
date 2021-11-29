package com.my.project.petclinic.hospital.persistence;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.persistence.config.RequestBackground;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository(value = "doctorRepositoryStrategy")
class DoctorRepositoryStrategy implements DoctorRepository {

    private final DoctorRepository jdbcDoctorRepository;
    private final DoctorRepository jpaDoctorRepository;
    private final RequestBackground condition;

    public DoctorRepositoryStrategy(@Qualifier("jpaDoctorRepository") DoctorRepository jpaDoctorRepository,
                                    @Qualifier("jdbcDoctorRepository") DoctorRepository jdbcDoctorRepository, RequestBackground condition) {
        this.jpaDoctorRepository = jpaDoctorRepository;
        this.jdbcDoctorRepository = jdbcDoctorRepository;
        this.condition = condition;
    }

    @Override
    public List<Doctor> findAll() {
        if (condition.getJdbc()) {
            return jdbcDoctorRepository.findAll();
        } else {
            return jpaDoctorRepository.findAll();
        }
    }

    @Override
    public Doctor save(Doctor doctor) {
        if (condition.getJdbc()) {
            return jdbcDoctorRepository.save(doctor);
        } else {
            return jpaDoctorRepository.save(doctor);
        }
    }

    @Override
    public Doctor update(Doctor doctor) {
        if (condition.getJdbc()) {
            return jdbcDoctorRepository.update(doctor);
        } else {
            return jpaDoctorRepository.update(doctor);
        }
    }

    @Override
    public Doctor findById(Long id) {
        if (condition.getJdbc()) {
            return jdbcDoctorRepository.findById(id);
        } else {
            return jpaDoctorRepository.findById(id);
        }
    }
}
