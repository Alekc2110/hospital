package com.my.project.petclinic.hospital.persistence.jparepository.interfaces;

import com.my.project.petclinic.hospital.persistence.entity.PatientEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaPatientRepo extends JpaRepository<PatientEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"doctorList"})
    @NonNull
    List<PatientEntity> findAll();
}
