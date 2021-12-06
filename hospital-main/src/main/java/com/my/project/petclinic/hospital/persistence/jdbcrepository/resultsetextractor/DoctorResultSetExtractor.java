package com.my.project.petclinic.hospital.persistence.jdbcrepository.resultsetextractor;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.model.Patient;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class DoctorResultSetExtractor implements ResultSetExtractor<Doctor> {
    @Override
    public Doctor extractData(ResultSet rs) throws SQLException, DataAccessException {
        Doctor doctor = null;
        List<Patient> patientList = new ArrayList<>();
        Map<Long, Doctor> doctorKeyDoctorMap = new HashMap<>();
        Map<Long, Patient> patientKeyPatientMap = new HashMap<>();

        while (rs.next()) {
            final Long doctorKey = rs.getLong("d_id");
            final Doctor storedDoctor = doctorKeyDoctorMap.get(doctorKey);
            if (storedDoctor == null) {
                doctor = new Doctor();
                doctor.setId(doctorKey);
                doctor.setName(rs.getString("d_name"));
                doctor.setPosition(rs.getString("d_pos"));
                doctor.setSurName(rs.getString("d_sn"));
                doctor.setPatients(patientList);
                doctorKeyDoctorMap.put(doctorKey, doctor);
            }
            final Long patientKey = rs.getLong("p_id");
            Patient patient = patientKeyPatientMap.get(patientKey);
            if (patient == null) {
                patient = new Patient();
                patient.setId(patientKey);
                patient.setName(rs.getString("p_name"));
                patient.setSurName(rs.getString("p_sn"));
                patient.setAge(rs.getInt("p_age"));
                patientKeyPatientMap.put(patientKey, patient);

            }
            if (Objects.requireNonNull(doctor).getPatients() == null) {
                doctor.setPatients(patientList);
            }
           doctor.getPatients().add(patient);

        }
        return doctor;
    }
}

