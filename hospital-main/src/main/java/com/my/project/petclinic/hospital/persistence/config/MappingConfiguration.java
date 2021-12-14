package com.my.project.petclinic.hospital.persistence.config;

import com.my.project.petclinic.hospital.persistence.jparepository.mapper.MapStructDoctorMapper;
import com.my.project.petclinic.hospital.persistence.jparepository.mapper.MapStructPatientMapper;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class MappingConfiguration {

    @Bean
    public MapStructDoctorMapper mapStructDoctorMapper(){
        return Mappers.getMapper(MapStructDoctorMapper.class);
    }

    @Bean
    public MapStructPatientMapper mapStructPatientMapper(){
        return Mappers.getMapper(MapStructPatientMapper.class);
    }
}
