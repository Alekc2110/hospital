package com.my.project.petclinic.hospital.api.mapper;

import com.my.project.petclinic.hospital.api.dto.PatientDto;
import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.model.Patient;
import ma.glasnost.orika.MappingContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PatientCustomMapperTest {

    @InjectMocks
    private PatientCustomMapper subject;

    @Mock
    MappingContext context;

    @Test
    @DisplayName("should return patientDto with doctorList with only id value")
    void shouldMapPatientToPatientDto() {
        //given
        Patient patient = Patient.builder()
                .doctors(List.of(Doctor.builder().id(50L).name("name").surName("patientS").position("some").build()))
                .build();
        PatientDto patientDto = PatientDto.builder().build();

        //when
        subject.mapAtoB(patient, patientDto, context);

        //then
        assertThat(patientDto.getDoctorDtoList().get(0).getId()).isEqualTo(50L);
        assertThat(patientDto.getDoctorDtoList().get(0).getName()).isNull();
        assertThat(patientDto.getDoctorDtoList().get(0).getSurName()).isNull();
        assertThat(patientDto.getDoctorDtoList().get(0).getPosition()).isNull();

    }

}
