package com.my.project.petclinic.hospital.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    private Long id;
    private String name;
    private String surName;
    private String position;
    private List<Patient> patients = new ArrayList<>();


}
