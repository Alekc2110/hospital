package com.my.project.petclinic.hospital.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "doctors")
@Entity
public class DoctorEntity {

    @Id
    @SequenceGenerator(name = "doctors_d_id_seq", sequenceName = "doctors_d_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctors_d_id_seq")
    @Column(name = "d_id", updatable = false, nullable = false)
    private Long id;
    @Column
    private String name;
    @Column
    private String surName;
    @Column
    private String position;

    @ManyToMany(mappedBy = "doctorList", fetch = FetchType.EAGER)
    private List<PatientEntity> patientList = new ArrayList<>();

}
