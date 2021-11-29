package com.my.project.petclinic.hospital.api.mapper;

import com.my.project.petclinic.hospital.api.dto.DoctorDto;
import com.my.project.petclinic.hospital.api.dto.PatientDto;
import com.my.project.petclinic.hospital.domain.model.Patient;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientCustomMapper extends CustomMapper<Patient, PatientDto> {
    @Override
    public void mapAtoB(Patient patient, PatientDto patientDto, MappingContext context) {
        final List<DoctorDto> doctorDtoList = patient.getDoctors().stream().map(doctor -> DoctorDto.builder().id(doctor.getId()).build()).collect(Collectors.toList());
        patientDto.setDoctorDtoList(doctorDtoList);
    }
}
