package com.my.project.petclinic.hospital.domain.service;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.persistence.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @InjectMocks
    private DoctorService subject;
    @Mock
    private DoctorRepository repository;

    @Test
    @DisplayName("should return not empty list")
    void getAllDoctorsShouldReturnNotEmptyListTest() {
        //given
        final List<Doctor> testList = List.of(Doctor.builder().id(1L).name("first").surName("surName1").position("pos1").build(),
                Doctor.builder().id(2L).name("second").surName("surName2").position("pos2").build());
        when(repository.findAll()).thenReturn(testList);
        //when
        final List<Doctor> allDoctors = subject.getAllDoctors();

        //then
        assertThat(allDoctors.size()).isNotZero();
    }

    @Test
    @DisplayName("should return doctors list")
    void getAllDoctorsShouldReturnDoctorsListTest() {
        //given
        final List<Doctor> testList = List.of(Doctor.builder().id(1L).name("first").surName("surName1").position("pos1").build(),
                Doctor.builder().id(2L).name("second").surName("surName2").position("pos2").build());
        when(repository.findAll()).thenReturn(testList);

        //when
        final List<Doctor> allDoctors = subject.getAllDoctors();

        //then
        Assertions.assertEquals(2, allDoctors.size());
    }

    @Test
    @DisplayName("should save new doctor")
    void shouldSaveNewDoctorTest() {
        //given
        Doctor doctor = Doctor.builder().build();
        Doctor savedDoctor =  Doctor.builder().id(5L).name("name").surName("SurName").position("doctor").build();
        when(repository.save(any(Doctor.class))).thenReturn(savedDoctor);
        //when
        final Doctor result = subject.save(doctor);
        //then
        Assertions.assertEquals(5L, result.getId());
    }

    @Test
    @DisplayName("should update doctor")
    void shouldUpdateDoctorTest() {
        //given
        Doctor doctor =  Doctor.builder().id(1L).name("updated").surName("SurName").position("doctor").build();
        Doctor updated =  Doctor.builder().id(1L).name("updated").surName("SurName").position("doctor").build();
        when(repository.update(doctor)).thenReturn(updated);
        //when
        final Doctor result = subject.update(doctor);
        //then
        Assertions.assertAll(()->{
            Assertions.assertEquals(updated.getName(), result.getName());
            Assertions.assertEquals(updated.getId(), result.getId());
            Assertions.assertEquals(updated.getPosition(),result.getPosition());
        });
    }

}
