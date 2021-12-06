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
@Table(name = "patients")
@Entity
public class PatientEntity {

    @Id
    @SequenceGenerator(name = "patients_p_id_seq", sequenceName = "patients_p_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patients_p_id_seq")
    @Column(name = "p_id", updatable = false, nullable = false)
    private Long id;
    @Column
    private String name;
    @Column
    private String surName;
    @Column
    private Integer age;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "patient_doctor",
            joinColumns = {@JoinColumn(name = "p_id")},
            inverseJoinColumns = {@JoinColumn(name = "d_id")}
    )
    private List<DoctorEntity> doctorList = new ArrayList<>();

}

