package com.my.project.petclinic.hospital.domain.service;

import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.persistence.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService subject;
    @Mock
    private PatientRepository repository;

    @Test
    @DisplayName("should return not empty list")
    public void getAllPatientsShouldReturnNotEmptyListTest() {
        final List<Patient> testList = List.of(Patient.builder().id(1L).name("first").surName("surName1").age(40).build());
        //given
        when(repository.findAll()).thenReturn(testList);
        //when
        final List<Patient> allPatients = subject.getAllPatients();
        //then
        assertThat(allPatients.size()).isNotZero();
    }

    @Test
    @DisplayName("should return patient list")
    public void getAllPatientsShouldReturnPatientsListTest() {
        final List<Patient> testList = List.of(Patient.builder().id(1L).name("first").surName("surName1").age(40).build(),
                Patient.builder().id(2L).name("second").surName("surName2").age(35).build());
        //given
        when(repository.findAll()).thenReturn(testList);
        //when
        final List<Patient> allPatients = subject.getAllPatients();
        //then
        Assertions.assertEquals(2, allPatients.size());
    }

    @Test
    @DisplayName("should save new patient")
    public void shouldSaveNewPatientTest() {
        //given
        Patient patient = Patient.builder().build();
        Patient savedPatient =  Patient.builder().id(5L).name("name").surName("SurName").age(25).build();
        when(repository.save(any(Patient.class))).thenReturn(savedPatient);
        //when
        final Patient result = subject.save(patient);
        //then
        Assertions.assertEquals(5L, result.getId());
    }

    @Test
    @DisplayName("should update patient")
    public void shouldUpdatePatientTest() {
        //given
        Patient patient =  Patient.builder().build();
        Patient updated =  Patient.builder().id(1L).name("updated").surName("SurName").age(30).build();
        when(repository.update(any(Patient.class))).thenReturn(updated);
        //when
        final Patient result = subject.update(patient);
        //then
        Assertions.assertEquals(updated, result);
    }
}
