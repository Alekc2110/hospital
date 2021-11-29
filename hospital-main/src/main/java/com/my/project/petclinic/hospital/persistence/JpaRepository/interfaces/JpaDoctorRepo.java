package com.my.project.petclinic.hospital.persistence.JpaRepository.interfaces;

import com.my.project.petclinic.hospital.persistence.entity.DoctorEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDoctorRepo extends JpaRepository<DoctorEntity, Long> {
    @Override
    @EntityGraph(attributePaths = {"patientList"})
    @NonNull
    List<DoctorEntity> findAll();
}

