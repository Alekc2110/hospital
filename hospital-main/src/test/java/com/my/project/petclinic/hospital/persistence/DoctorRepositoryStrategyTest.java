package com.my.project.petclinic.hospital.persistence;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.api.config.RequestBackground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;


class DoctorRepositoryStrategyTest {

    private DoctorRepositoryStrategy subject;
    private DoctorRepository jdbcDoctorRepository;
    private DoctorRepository jpaDoctorRepository;
    private RequestBackground condition;

    @BeforeEach
    void setUp() {
        jdbcDoctorRepository = mock(DoctorRepository.class);
        jpaDoctorRepository = mock(DoctorRepository.class);
        condition = mock(RequestBackground.class);
        subject = new DoctorRepositoryStrategy(jpaDoctorRepository, jdbcDoctorRepository, condition);
    }

    @Test
    @DisplayName("should return list of doctors using jdbc")
    void shouldReturnListOfDoctorsByJdbcIfConditionTrueTest() {
        //given
        final Doctor doctor1 = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        final Doctor doctor2 = Doctor.builder().id(2L).name("second").surName("surName2").position("dentist").build();
        final List<Doctor> doctors = List.of(doctor1, doctor2);
        when(condition.getJdbc()).thenReturn(true);
        when(jdbcDoctorRepository.findAll()).thenReturn(doctors);

        //when
        subject.findAll();

        //then
        verify(jdbcDoctorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should return list of doctors using jpa")
    void shouldReturnListOfDoctorsByJpaIfConditionFalseTest() {
        //given
        final Doctor doctor1 = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        final Doctor doctor2 = Doctor.builder().id(2L).name("second").surName("surName2").position("dentist").build();
        final List<Doctor> doctors = List.of(doctor1, doctor2);
        when(condition.getJdbc()).thenReturn(false);
        when(jpaDoctorRepository.findAll()).thenReturn(doctors);

        //when
        subject.findAll();

        //then
        verify(jpaDoctorRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("should save new doctor using jdbc")
    void shouldSaveNewDoctorByJdbcIfConditionTrueTest() {
        //given
        final Doctor doctor = Doctor.builder().name("first").surName("surName1").position("dentist").build();
        final Doctor doctor1 = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();

        when(condition.getJdbc()).thenReturn(true);
        when(jdbcDoctorRepository.save(doctor)).thenReturn(doctor1);

        //when
        subject.save(doctor);

        //then
        verify(jdbcDoctorRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should save new doctor using jpa")
    void shouldSaveNewDoctorByJpaIfConditionFalseTest() {
        //given
        final Doctor doctor = Doctor.builder().name("first").surName("surName1").position("dentist").build();
        final Doctor doctor1 = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();

        when(condition.getJdbc()).thenReturn(false);
        when(jpaDoctorRepository.save(doctor)).thenReturn(doctor1);

        //when
        subject.save(doctor);

        //then
        verify(jpaDoctorRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should update doctor using jdbc")
    void shouldUpdateDoctorByJdbcIfConditionTrueTest() {
        //given
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        when(condition.getJdbc()).thenReturn(true);
        when(jdbcDoctorRepository.update(doctor)).thenReturn(doctor);

        //when
        subject.update(doctor);

        //then
        verify(jdbcDoctorRepository, times(1)).update(any());
    }

    @Test
    @DisplayName("should update doctor using jpa")
    void shouldUpdateDoctorByJpaIfConditionFalseTest() {
        //given
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        when(condition.getJdbc()).thenReturn(false);
        when(jpaDoctorRepository.update(doctor)).thenReturn(doctor);

        //when
        subject.update(doctor);

        //then
        verify(jpaDoctorRepository, times(1)).update(any());
    }

    @Test
    @DisplayName("should find by id and return doctor using jpa")
    void shouldFindByIdAndReturnDoctorByJpaIfConditionFalseTest() {
        //given
        final Long id = 1L;
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        when(condition.getJdbc()).thenReturn(false);
        when(jpaDoctorRepository.findById(id)).thenReturn(doctor);

        //when
        subject.findById(id);

        //then
        verify(jpaDoctorRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("should find by id and return doctor using jdbc")
    void shouldFindByIdAndReturnDoctorByJdbcIfConditionTrueTest() {
        //given
        final Long id = 1L;
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        when(condition.getJdbc()).thenReturn(true);
        when(jdbcDoctorRepository.findById(id)).thenReturn(doctor);

        //when
        subject.findById(id);

        //then
        verify(jdbcDoctorRepository, times(1)).findById(anyLong());
    }
}
