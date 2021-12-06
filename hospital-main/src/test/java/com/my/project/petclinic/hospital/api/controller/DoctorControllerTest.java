package com.my.project.petclinic.hospital.api.controller;

import com.my.project.petclinic.hospital.api.dto.DoctorDto;
import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.service.DoctorService;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @InjectMocks
    private DoctorController subject;
    @Mock
    private DoctorService service;
    @Mock
    private MapperFacade mapper;

    @Test
    @DisplayName("should return list of doctorDtos")
    void getAllShouldReturnDoctorDtoListTest() {
        //given
        final DoctorDto doctorDto = DoctorDto.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        final List<Doctor> doctorList = List.of(doctor);
        when(service.getAllDoctors()).thenReturn(doctorList);
        when(mapper.map(doctor, DoctorDto.class)).thenReturn(doctorDto);

        //when
        final List<DoctorDto> result = subject.getAll();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("first");
    }

    @Test
    @DisplayName("should save new doctor and return id")
    void shouldSaveNewDoctorTest() {
        //given
        final DoctorDto doctorDto = DoctorDto.builder().name("first").surName("surName1").position("dentist").build();
        final Doctor doctor = Doctor.builder().name("first").surName("surName1").position("dentist").build();
        final Doctor savedDoctor = Doctor.builder().id(25L).name("first").surName("surName1").position("dentist").build();
        when(mapper.map(doctorDto, Doctor.class)).thenReturn(doctor);
        when(service.save(doctor)).thenReturn(savedDoctor);

        //when
        final Long savedId = subject.save(doctorDto);

        //then
        assertThat(savedId).isEqualTo(25);
    }

    @Test
    @DisplayName("should update doctor")
    void shouldUpdateDoctorTest() {
        //given
        final Long id = 5L;
        final DoctorDto doctorDto = DoctorDto.builder().id(5L).name("first").surName("surName1").position("dentist").build();
        final Doctor doctor = Doctor.builder().id(5L).name("first").surName("surName1").position("dentist").build();
        final Doctor updatedDoctor = Doctor.builder().id(5L).name("first").surName("surName1").position("dentist").build();
        when(mapper.map(doctorDto, Doctor.class)).thenReturn(doctor);
        when(service.update(doctor)).thenReturn(updatedDoctor);
        when(mapper.map(updatedDoctor, DoctorDto.class)).thenReturn(doctorDto);

        //when
        final DoctorDto result = subject.update(id, doctorDto);

        //then
        assertThat(result.getId()).isEqualTo(id);
        verify(mapper, times(2)).map(any(),any());
    }
}
