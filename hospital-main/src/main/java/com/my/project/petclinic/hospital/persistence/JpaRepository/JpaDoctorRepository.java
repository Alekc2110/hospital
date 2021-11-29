package com.my.project.petclinic.hospital.persistence.JpaRepository;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.persistence.DoctorRepository;
import com.my.project.petclinic.hospital.persistence.entity.DoctorEntity;
import com.my.project.petclinic.hospital.persistence.JpaRepository.interfaces.JpaDoctorRepo;
import com.my.project.petclinic.hospital.persistence.JpaRepository.mapper.MapStructDoctorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository(value = "jpaDoctorRepository")
class JpaDoctorRepository implements DoctorRepository {

    private final JpaDoctorRepo repository;
    private final MapStructDoctorMapper mapStructDoctorMapper;

    @Override
    public List<Doctor> findAll() {
        return repository.findAll().stream().map(mapStructDoctorMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public Doctor save(Doctor doctor) {
        return mapStructDoctorMapper.entityToModel(repository.save(mapStructDoctorMapper.modelToEntity(doctor)));
    }

    @Override
    @Transactional
    public Doctor update(Doctor doctor) {
        final Doctor doctorToUpdate = findById(doctor.getId());

        doctorToUpdate.setName(doctor.getName());
        doctorToUpdate.setSurName(doctor.getSurName());
        doctorToUpdate.setPosition(doctor.getPosition());

        return mapStructDoctorMapper.entityToModel(mapStructDoctorMapper.modelToEntity(doctorToUpdate));
    }

    @Override
    public Doctor findById(Long id) {
        final DoctorEntity doctorEntity = repository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("doctor with id %s not found", id)));
        return mapStructDoctorMapper.entityToModel(doctorEntity);
    }
}
