package com.my.project.petclinic.hospital.persistence;

import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.api.config.RequestBackground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class PatientRepositoryStrategyTest {

    private PatientRepositoryStrategy subject;
    private PatientRepository jdbcPatientRepository;
    private PatientRepository jpaPatientRepository;
    private RequestBackground condition;

    @BeforeEach
    void setUp() {
        jdbcPatientRepository = mock(PatientRepository.class);
        jpaPatientRepository = mock(PatientRepository.class);
        condition = mock(RequestBackground.class);
        subject = new PatientRepositoryStrategy(jdbcPatientRepository, jpaPatientRepository, condition);
    }

    @Test
    @DisplayName("should return list of patients using jdbc")
    void shouldReturnListOfPatientsByJdbcIfConditionTrueTest() {
        //given
        final Patient patient1 = Patient.builder().id(1L).name("first").surName("surName1").build();
        final Patient patient2 = Patient.builder().id(2L).name("second").surName("surName2").build();
        final List<Patient> patients = List.of(patient1, patient2);
        when(condition.getJdbc()).thenReturn(true);
        when(jdbcPatientRepository.findAll()).thenReturn(patients);

        //when
        subject.findAll();

        //then
        verify(jdbcPatientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should return list of patients using jpa")
    void shouldReturnListOfPatientsByJpaIfConditionFalseTest() {
        //given
        final Patient patient1 = Patient.builder().id(1L).name("first").surName("surName1").build();
        final Patient patient2 = Patient.builder().id(2L).name("second").surName("surName2").build();
        final List<Patient> patients = List.of(patient1, patient2);
        when(condition.getJdbc()).thenReturn(false);
        when(jpaPatientRepository.findAll()).thenReturn(patients);

        //when
        subject.findAll();

        //then
        verify(jpaPatientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should save new patient using jdbc")
    void shouldSaveNewDoctorByJdbcIfConditionTrueTest() {
        //given
        final Patient patient = Patient.builder().name("first").surName("surName1").build();
        final Patient patient1 = Patient.builder().id(1L).name("first").surName("surName1").build();
        when(condition.getJdbc()).thenReturn(true);
        when(jdbcPatientRepository.save(patient)).thenReturn(patient1);

        //when
        subject.save(patient);

        //then
        verify(jdbcPatientRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should save new patient using jpa")
    void shouldSaveNewPatientByJpaIfConditionFalseTest() {
        //given
        final Patient patient = Patient.builder().name("first").surName("surName1").build();
        final Patient patient1 = Patient.builder().id(1L).name("first").surName("surName1").build();
        when(condition.getJdbc()).thenReturn(false);
        when(jpaPatientRepository.save(patient)).thenReturn(patient1);

        //when
        subject.save(patient);

        //then
        verify(jpaPatientRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should update patient using jdbc")
    void shouldUpdatePatientByJdbcIfConditionTrueTest() {
        //given
        final Patient patient = Patient.builder().id(5L).name("first").surName("surName1").build();
        when(condition.getJdbc()).thenReturn(true);
        when(jdbcPatientRepository.update(patient)).thenReturn(patient);

        //when
        subject.update(patient);

        //then
        verify(jdbcPatientRepository, times(1)).update(any());
    }

    @Test
    @DisplayName("should update patient using jpa")
    void shouldUpdatePatientByJpaIfConditionFalseTest() {
        //given
        final Patient patient = Patient.builder().id(5L).name("first").surName("surName1").build();
        when(condition.getJdbc()).thenReturn(false);
        when(jpaPatientRepository.update(patient)).thenReturn(patient);

        //when
        subject.update(patient);

        //then
        verify(jpaPatientRepository, times(1)).update(any());
    }

    @Test
    @DisplayName("should find by id and return patient using jpa")
    void shouldFindByIdAndReturnPatientByJpaIfConditionFalseTest() {
        //given
        final Long id = 5L;
        final Patient patient = Patient.builder().id(5L).name("first").surName("surName1").build();
        when(condition.getJdbc()).thenReturn(false);
        when(jpaPatientRepository.findById(id)).thenReturn(patient);

        //when
        subject.findById(id);

        //then
        verify(jpaPatientRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("should find by id and return patient using jdbc")
    void shouldFindByIdAndReturnPatientByJdbcIfConditionTrueTest() {
        //given
        final Long id = 5L;
        final Patient patient = Patient.builder().id(5L).name("first").surName("surName1").build();
        when(condition.getJdbc()).thenReturn(true);
        when(jdbcPatientRepository.findById(id)).thenReturn(patient);

        //when
        subject.findById(id);

        //then
        verify(jdbcPatientRepository, times(1)).findById(anyLong());
    }

}
