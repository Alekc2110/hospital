package com.my.project.petclinic.hospital.persistence.jparepository;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.persistence.jparepository.interfaces.JpaDoctorRepo;
import com.my.project.petclinic.hospital.persistence.jparepository.mapper.MapStructDoctorMapper;
import com.my.project.petclinic.hospital.persistence.entity.DoctorEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaDoctorRepositoryTest {

    @InjectMocks
    private JpaDoctorRepository subject;
    @Mock
    private JpaDoctorRepo repository;
    @Mock
    private MapStructDoctorMapper mapStructDoctorMapper;


    @Test
    @DisplayName("should return list of doctors")
    void findAllShouldReturnListOfDoctorsTest() {
        //given
        final DoctorEntity doctorEntity = DoctorEntity.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        final List<DoctorEntity> testEntityList = List.of(doctorEntity);

        when(repository.findAll()).thenReturn(testEntityList);
        when(mapStructDoctorMapper.entityToModel(doctorEntity)).thenReturn(doctor);

        //when
        final List<Doctor> result = subject.findAll();

        //then
        Assertions.assertAll(() -> {
            Assertions.assertEquals(testEntityList.size(), result.size());
            Assertions.assertEquals(testEntityList.get(0).getId(), result.get(0).getId());
            Assertions.assertEquals(testEntityList.get(0).getName(), result.get(0).getName());
            Assertions.assertEquals(testEntityList.get(0).getPosition(), result.get(0).getPosition());
        });
    }

    @Test
    @DisplayName("should save new doctor")
    void shouldSaveNewDoctorTest() {
        //given
        final Long savedId = 1L;
        final Doctor doctorToSave = Doctor.builder().name("first").surName("surName1").position("dentist").build();
        final DoctorEntity doctorEntity = DoctorEntity.builder().id(1L).name("first").surName("surName1").position("dentist").build();
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();

        when(mapStructDoctorMapper.modelToEntity(doctorToSave)).thenReturn(doctorEntity);
        when(repository.save(Mockito.any(DoctorEntity.class))).thenReturn(doctorEntity);
        when(mapStructDoctorMapper.entityToModel(doctorEntity)).thenReturn(doctor);

        //when
        final Doctor result = subject.save(doctorToSave);

        //then
        Assertions.assertEquals(savedId, result.getId());
    }

    @Test
    @DisplayName("should update doctor")
    void shouldUpdateDoctorTest() {
        //given
        final DoctorEntity doctorEntity = DoctorEntity.builder().id(1L).name("Max").surName("surName").position("surgeon").build();
        final Doctor doctor = Doctor.builder().id(1L).name("Max").surName("surName").position("surgeon").build();
        final Doctor updatedDoctor = Doctor.builder().id(1L).name("second").surName("surName1").position("dentist").build();
        final DoctorEntity doctorUpdatedEntity = DoctorEntity.builder().id(1L).name("second").surName("surName1").position("dentist").build();

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(doctorEntity));
        when(mapStructDoctorMapper.entityToModel(doctorEntity)).thenReturn(updatedDoctor);
        when(mapStructDoctorMapper.modelToEntity(updatedDoctor)).thenReturn(doctorUpdatedEntity);
        when(mapStructDoctorMapper.entityToModel(doctorUpdatedEntity)).thenReturn(updatedDoctor);

        //when
        final Doctor result = subject.update(doctor);

        //then
        Assertions.assertEquals(updatedDoctor.getName(), result.getName());
    }

    @Test
    @DisplayName("should return doctor by id")
    void shouldReturnDoctorByIdTest() {
        //given
        final Long doctorId = 1L;
        final DoctorEntity doctorEntity = DoctorEntity.builder().id(1L).name("Max").surName("surName").position("surgeon").build();
        final Doctor doctor = Doctor.builder().id(1L).name("first").surName("surName1").position("dentist").build();

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(doctorEntity));
        when(mapStructDoctorMapper.entityToModel(doctorEntity)).thenReturn(doctor);

        //when
        final Doctor result = subject.findById(doctorId);

        //then
        Assertions.assertAll(() -> {
            Assertions.assertEquals(doctor.getId(), result.getId());
            Assertions.assertEquals(doctor.getName(), result.getName());
            Assertions.assertEquals(doctor.getSurName(), result.getSurName());
            Assertions.assertEquals(doctor.getPosition(), result.getPosition());
        });
    }

    @Test
    @DisplayName("should throw EntityNotFoundException if findById returns null")
    void shouldThrowExceptionIfNotFoundDoctorByIdTest() {
        //given
        final Long doctorId = 1L;
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(EntityNotFoundException.class, ()-> subject.findById(doctorId));
    }
}
