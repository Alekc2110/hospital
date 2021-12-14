package com.my.project.petclinic.hospital.api.mapper;

import com.my.project.petclinic.hospital.api.dto.DoctorDto;
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
public class DoctorCustomMapperTest {

    @InjectMocks
    private DoctorCustomMapper subject;

    @Mock
    MappingContext context;

    @Test
    @DisplayName("should return doctorDto with patientList with only id value")
    void shouldMapDoctorToDoctorDto() {
        //given
        Doctor doctor = Doctor.builder()
                .patients(List.of(Patient.builder()
                        .id(55L).age(25).name("patientName").surName("patientSurname").build())).build();
        DoctorDto doctorDto = DoctorDto.builder().build();

        //when
        subject.mapAtoB(doctor, doctorDto, context);

        //then
        assertThat(doctorDto.getPatientDtoList().get(0).getId()).isEqualTo(55L);
        assertThat(doctorDto.getPatientDtoList().get(0).getName()).isNull();
        assertThat(doctorDto.getPatientDtoList().get(0).getAge()).isNull();

    }

}
