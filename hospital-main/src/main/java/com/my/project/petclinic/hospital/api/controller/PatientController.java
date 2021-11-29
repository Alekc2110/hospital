package com.my.project.petclinic.hospital.api.controller;

import com.my.project.petclinic.hospital.api.dto.PatientDto;
import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.domain.service.PatientService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hospital")
public class PatientController {

    private final PatientService service;
    private final MapperFacade mapper;

    public PatientController(PatientService service, @Qualifier("controllerOrikaMapper") MapperFacade mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PatientDto> getAll() {
        List<PatientDto> patientDtoList = new ArrayList<>();
        service.getAllPatients().forEach(patient -> patientDtoList.add(mapper.map(patient, PatientDto.class)));
        return patientDtoList;
    }

    @PostMapping(value = "/patients", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody PatientDto patientDto) {
        return service.save(mapper.map(patientDto, Patient.class)).getId();
    }

    @PutMapping(value = "/patients/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PatientDto update(@PathVariable("id") Long id, @RequestBody PatientDto patientDto) {
        patientDto.setId(id);
        final Patient updatedPatient = service.update(mapper.map(patientDto, Patient.class));
        return mapper.map(updatedPatient, PatientDto.class);
    }
}
