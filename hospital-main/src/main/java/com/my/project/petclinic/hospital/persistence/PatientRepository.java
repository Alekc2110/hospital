package com.my.project.petclinic.hospital.persistence;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.model.Patient;

import java.util.List;

public interface PatientRepository {

   List<Patient> findAll();

   Patient save(Patient patient);

   Patient update(Patient patient);

   Patient findById(Long id);
}
