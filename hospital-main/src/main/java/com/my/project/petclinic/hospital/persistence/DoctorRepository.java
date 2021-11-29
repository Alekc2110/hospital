package com.my.project.petclinic.hospital.persistence;

import com.my.project.petclinic.hospital.domain.model.Doctor;

import java.util.List;

public interface DoctorRepository {

    List<Doctor> findAll();

    Doctor save(Doctor doctor);

    Doctor update(Doctor doctor);

    Doctor findById(Long id);
}
