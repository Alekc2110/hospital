package com.my.project.petclinic.hospital.persistence.JpaRepository.mapper;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.persistence.entity.PatientEntity;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrikaPatientCustomMapper extends CustomMapper<PatientEntity, Patient> {

    @Override
    public void mapAtoB(PatientEntity patientEntity, Patient patient, MappingContext context) {
        final List<Doctor> patientList = patientEntity.getDoctorList().stream().
                map(d -> Doctor.builder().id(d.getId()).name(d.getName()).surName(d.getSurName()).position(d.getPosition()).build()).collect(Collectors.toList());
        patient.setDoctors(patientList);
    }
}
