package com.my.project.petclinic.hospital.persistence.jparepository.mapper;

import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructPatientMapper {

    @Mapping(source = "patient.doctors", target = "doctorList")
    PatientEntity modelToEntity(Patient patient);

    @Mapping(source = "patientEntity.doctorList", target = "doctors")
    Patient entityToModel(PatientEntity patientEntity);
}
