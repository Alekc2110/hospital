package com.my.project.petclinic.hospital.api.controller;

import com.my.project.petclinic.hospital.api.dto.PatientDto;
import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.domain.service.PatientService;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @InjectMocks
    private PatientController subject;
    @Mock
    private PatientService service;
    @Mock
    private MapperFacade mapper;

    @Test
    @DisplayName("should return list of patientDtos")
    void getAllShouldReturnPatientDtoListTest() {
        //given
        final PatientDto patientDto1 = PatientDto.builder().id(1L).name("first").surName("surName1").build();
        final PatientDto patientDto2 = PatientDto.builder().id(2L).name("second").surName("surName2").build();
        final Patient patient1 = Patient.builder().id(1L).name("first").surName("surName1").build();
        final Patient patient2 = Patient.builder().id(2L).name("second").surName("surName2").build();
        final List<Patient> patients = List.of(patient1, patient2);
        when(service.getAllPatients()).thenReturn(patients);
        when(mapper.map(patient1, PatientDto.class)).thenReturn(patientDto1);
        when(mapper.map(patient2, PatientDto.class)).thenReturn(patientDto2);

        //when
        final List<PatientDto> result = subject.getAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("first");
        assertThat(result.get(1).getName()).isEqualTo("second");
    }

    @Test
    @DisplayName("should save new patient and return id")
    void shouldSaveNewPatientTest() {
        //given
        final PatientDto patientDto = PatientDto.builder().name("first").surName("surName1").build();
        final Patient patient = Patient.builder().name("first").surName("surName1").build();
        final Patient savedPatient = Patient.builder().id(5L).name("first").surName("surName1").build();

        when(mapper.map(patientDto, Patient.class)).thenReturn(patient);
        when(service.save(patient)).thenReturn(savedPatient);

        //when
        final Long savedId = subject.save(patientDto);

        //then
        assertThat(savedId).isEqualTo(5L);
    }

    @Test
    @DisplayName("should update patient")
    void shouldUpdateDoctorTest() {
        //given
        final Long id = 5L;
        final PatientDto patientDto = PatientDto.builder().id(5L).name("first").surName("surName1").build();
        final Patient patient = Patient.builder().id(5L).name("first").surName("surName1").build();
        final Patient updatedPatient = Patient.builder().id(5L).name("first").surName("surName1").build();
        when(mapper.map(patientDto, Patient.class)).thenReturn(patient);
        when(service.update(patient)).thenReturn(updatedPatient);
        when(mapper.map(updatedPatient, PatientDto.class)).thenReturn(patientDto);

        //when
        final PatientDto result = subject.update(id, patientDto);

        //then
        assertThat(result.getId()).isEqualTo(id);
        verify(mapper, times(2)).map(any(),any());
    }

}
