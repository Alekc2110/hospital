package com.my.project.petclinic.hospital.api.config;

import com.my.project.petclinic.hospital.api.dto.DoctorDto;
import com.my.project.petclinic.hospital.api.dto.PatientDto;
import com.my.project.petclinic.hospital.api.mapper.DoctorCustomMapper;
import com.my.project.petclinic.hospital.api.mapper.PatientCustomMapper;
import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.model.Patient;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@AllArgsConstructor
@Configuration
public class MappingConfig {

    private final DoctorCustomMapper doctorCustomMapper;
    private final PatientCustomMapper patientCustomMapper;

    @Bean
    public MapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder()
                .mapNulls(Boolean.FALSE)
                .build();
    }

    @Bean("controllerOrikaMapper")
    public MapperFacade controllerMapperFacade(MapperFactory mapperFactory) {
        mapperFactory.classMap(Doctor.class, DoctorDto.class).customize(doctorCustomMapper).byDefault().register();
        mapperFactory.classMap(Patient.class, PatientDto.class).customize(patientCustomMapper).byDefault().register();
        return mapperFactory.getMapperFacade();
    }



}
