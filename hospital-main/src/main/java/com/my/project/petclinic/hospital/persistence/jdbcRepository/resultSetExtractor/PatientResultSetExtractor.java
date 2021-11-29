package com.my.project.petclinic.hospital.persistence.jdbcRepository.resultSetExtractor;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.model.Patient;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class PatientResultSetExtractor implements ResultSetExtractor<Patient> {
    @Override
    public Patient extractData(ResultSet rs) throws SQLException, DataAccessException {
        Patient patient = null;
        List<Doctor> doctorList = new ArrayList<>();
        Map<Long, Doctor> doctorKeyDoctorMap = new HashMap<>();
        Map<Long, Patient> patientKeyPatientMap = new HashMap<>();

        while (rs.next()) {
            final Long doctorKey = rs.getLong("d_id");
            Doctor doctor = doctorKeyDoctorMap.get(doctorKey);
            if (doctor == null) {
                doctor = new Doctor();
                doctor.setId(doctorKey);
                doctor.setName(rs.getString("d_name"));
                doctor.setPosition(rs.getString("d_pos"));
                doctor.setSurName(rs.getString("d_sn"));
                doctorKeyDoctorMap.put(doctorKey, doctor);
            }
            final Long patientKey = rs.getLong("p_id");
            Patient storedPatient = patientKeyPatientMap.get(patientKey);
            if (storedPatient == null) {
                patient = new Patient();
                patient.setId(patientKey);
                patient.setName(rs.getString("p_name"));
                patient.setSurName(rs.getString("p_sn"));
                patient.setAge(rs.getInt("p_age"));
                patient.setDoctors(doctorList);
                patientKeyPatientMap.put(patientKey, patient);
            }
            if (Objects.requireNonNull(patient).getDoctors() == null) {
                patient.setDoctors(doctorList);
            }
            patient.getDoctors().add(doctor);
        }


        return patient;
    }
}
