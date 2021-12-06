package com.my.project.petclinic.hospital.persistence.jparepository;

import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.persistence.jparepository.interfaces.JpaPatientRepo;
import com.my.project.petclinic.hospital.persistence.jparepository.mapper.MapStructPatientMapper;
import com.my.project.petclinic.hospital.persistence.PatientRepository;
import com.my.project.petclinic.hospital.persistence.entity.PatientEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository(value = "jpaPatientRepository")
 class JpaPatientRepository implements PatientRepository {

    private final JpaPatientRepo repository;
    private final MapStructPatientMapper mapStructPatientMapper;


    @Override
    public List<Patient> findAll() {
        return repository.findAll().stream().map(mapStructPatientMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public Patient save(Patient patient) {
        return mapStructPatientMapper.entityToModel(repository.save(mapStructPatientMapper.modelToEntity(patient)));
    }

    @Override
    @Transactional
    public Patient update(Patient patient) {
        final Patient patientToUpdate = findById(patient.getId());

        patientToUpdate.setAge(patient.getAge());
        patientToUpdate.setName(patient.getName());
        patientToUpdate.setSurName(patient.getSurName());

        return mapStructPatientMapper.entityToModel(mapStructPatientMapper.modelToEntity(patientToUpdate));
    }

    @Override
    public Patient findById(Long id) {
        final PatientEntity patientEntity = repository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("patient with id %s not found", id)));
        return mapStructPatientMapper.entityToModel(patientEntity);
    }
}
