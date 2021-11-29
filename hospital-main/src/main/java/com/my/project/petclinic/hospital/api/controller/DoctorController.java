package com.my.project.petclinic.hospital.api.controller;

import com.my.project.petclinic.hospital.api.dto.DoctorDto;
import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.service.DoctorService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hospital")
public class DoctorController {

    private final DoctorService service;
    private final MapperFacade mapper;


    public DoctorController(DoctorService service, @Qualifier("controllerOrikaMapper") MapperFacade mapper) {
        this.service = service;
        this.mapper = mapper;

    }

    @GetMapping(value = "/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DoctorDto> getAll() {
      List<DoctorDto> doctorDtoList = new ArrayList<>();
      service.getAllDoctors().forEach(doctor -> doctorDtoList.add(mapper.map(doctor, DoctorDto.class)));
      return doctorDtoList;
    }

    @PostMapping(value = "/doctors", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody DoctorDto doctorDto) {
        return service.save(mapper.map(doctorDto, Doctor.class)).getId();
    }

    @PutMapping(value = "/doctors/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto update(@PathVariable("id") Long id, @RequestBody DoctorDto doctorDto) {
        doctorDto.setId(id);
        final Doctor updatedDoctor = service.update(mapper.map(doctorDto, Doctor.class));
        return mapper.map(updatedDoctor, DoctorDto.class);
    }

}
