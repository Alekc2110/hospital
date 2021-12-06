package com.my.project.petclinic.hospital.persistence.jdbcrepository;

import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.persistence.jdbcrepository.keyholder.KeyHolderFactory;
import com.my.project.petclinic.hospital.persistence.jdbcrepository.resultsetextractor.ListPatientsResultSetExtractor;
import com.my.project.petclinic.hospital.persistence.jdbcrepository.resultsetextractor.PatientResultSetExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JdbcPatientRepositoryTest {


    @InjectMocks
    private JdbcPatientRepository subject;

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ListPatientsResultSetExtractor listResultSetExtractor;
    @Mock
    private PatientResultSetExtractor patientResultSetExtractor;
    @Mock
    private KeyHolderFactory keyHolderFactory;
    @Mock
    private  KeyHolder keyHolder;


    @Test
    @DisplayName("should return list of patients")
    void findAllShouldReturnListOfPatientsTest(){
        //given
        List<Patient> testList = List.of(Patient.builder().id(1L).name("first").surName("surName1").age(40).build(),
                Patient.builder().id(2L).name("second").surName("surName2").age(35).build());
        Mockito.when(jdbcTemplate.query(Queries.SELECT_ALL_PATIENTS, listResultSetExtractor)).thenReturn(testList);

        //when
        final List<Patient> result = subject.findAll();

        //then
        Assertions.assertEquals(testList, result);
    }

    @Test
    @DisplayName("should save new patient")
    void shouldSaveNewPatientTest(){
        //given
        Long expectedId = 4L;
        Patient patient =  Patient.builder().name("patient").surName("NickName").age(30).build();
        Patient savedPatient =  Patient.builder().id(4L).name("patient").surName("NickName").age(30).build();
        Map<String, Object> keys= new HashMap<>();
        keys.put("p_id", expectedId);

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKeys()).thenReturn(keys);
        when(keyHolder.getKey()).thenReturn(expectedId);
        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        when(jdbcTemplate.query(Queries.GET_PATIENT_BY_ID, patientResultSetExtractor, expectedId)).thenReturn(savedPatient);

        //when
        final Patient result = subject.save(patient);

        //then
        assertEquals(expectedId, result.getId());
    }

    @Test
    @DisplayName("should update patient")
    void shouldUpdateDoctorTest(){
        //given
        final Long patientId = 3L;
        final Patient patient = Patient.builder().id(3L).name("Update").surName("SurName3").age(55).build();
        when(jdbcTemplate.update(anyString(),anyInt(),anyString(),anyString(),anyLong())).thenReturn(1);
        when(jdbcTemplate.query(Queries.GET_PATIENT_BY_ID, patientResultSetExtractor, patientId)).thenReturn(patient);

        //when
        final Patient result = subject.update(patient);

        //then
        Assertions.assertAll(()->{
            Assertions.assertEquals(patient.getId(), result.getId());
            Assertions.assertEquals(patient.getName(), result.getName());
            Assertions.assertEquals(patient.getSurName(),result.getSurName());
            Assertions.assertEquals(patient.getAge(),result.getAge());
        });
    }

    @Test
    @DisplayName("when update patient should call jdbcTepmlate method in right order")
    void shouldCallJdbcTemplateQueriesInOrderWhenUpdatePatientTest(){
        //given
        final Long patientId = 3L;
        final Patient patient = Patient.builder().id(3L).name("Update").surName("SurName3").age(55).build();
        when(jdbcTemplate.update(anyString(),anyInt(),anyString(),anyString(),anyLong())).thenReturn(1);
        when(jdbcTemplate.query(Queries.GET_PATIENT_BY_ID, patientResultSetExtractor, patientId)).thenReturn(patient);

        //when
        subject.update(patient);

        //then
        InOrder order = inOrder(jdbcTemplate);
        order.verify(jdbcTemplate).update(anyString(), anyInt(), anyString(), anyString(), anyLong());
        order.verify(jdbcTemplate).query(Queries.GET_PATIENT_BY_ID, patientResultSetExtractor, patientId);
    }

    @Test
    @DisplayName("should return patient by id")
    void shouldReturnPatientByIdTest(){
        //given
        final Long patientId = 3L;
        final Patient patient = Patient.builder().id(3L).name("Update").surName("SurName3").age(55).build();
        when(jdbcTemplate.query(Queries.GET_PATIENT_BY_ID, patientResultSetExtractor, patientId)).thenReturn(patient);

        //when
        final Patient result = subject.findById(patientId);

        //then
        Assertions.assertAll(()->{
            Assertions.assertEquals(patient.getId(), result.getId());
            Assertions.assertEquals(patient.getName(), result.getName());
            Assertions.assertEquals(patient.getSurName(),result.getSurName());
            Assertions.assertEquals(patient.getAge(),result.getAge());
        });
    }

    @Test
    @DisplayName("should throw EntityNotFoundException if findById returns null")
    void shouldThrowExceptionIfNotFoundPatientByIdTest() {
        //given
        Long patientId = 3L;
        when(jdbcTemplate.query(Queries.GET_PATIENT_BY_ID, patientResultSetExtractor, patientId)).thenReturn(null);

        //then
        Assertions.assertThrows(EntityNotFoundException.class, ()-> subject.findById(patientId));
    }


}
