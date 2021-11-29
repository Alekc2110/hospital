package com.my.project.petclinic.hospital.persistence.JpaRepository.mapper;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.persistence.entity.DoctorEntity;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrikaDoctorCustomMapper extends CustomMapper<DoctorEntity, Doctor> {
    @Override
    public void mapAtoB(DoctorEntity doctorEntity, Doctor doctor, MappingContext context) {
        final List<Patient> patientList = doctorEntity.getPatientList().stream().
                map(p -> Patient.builder().id(p.getId()).name(p.getName()).surName(p.getSurName()).age(p.getAge()).build()).collect(Collectors.toList());
        doctor.setPatients(patientList);
    }
}
