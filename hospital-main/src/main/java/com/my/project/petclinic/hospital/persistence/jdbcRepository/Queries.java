package com.my.project.petclinic.hospital.persistence.jdbcRepository;

public final class Queries {
    public final static String SELECT_ALL_DOCTORS = "select d.d_id as d_id, d.name as d_name, d.position as d_pos, d.sur_name as d_sn," +
            " p.p_id as p_id, p.name as p_name, p.sur_name as p_sn, p.age as p_age " +
            "from doctors d left join  patient_doctor pd on pd.d_id = d.d_id left join patients p on pd.p_id = p.p_id";
    public final static String SELECT_ALL_PATIENTS = "select p.p_id as p_id, p.name as p_name, p.sur_name as p_sn, p.age as p_age," +
            " d.d_id as d_id, d.name as d_name, d.position as d_pos, d.sur_name as d_sn from patients p " +
            "left join  patient_doctor pd on pd.p_id = p.p_id left join doctors d on pd.d_id = d.d_id";
    public final static String SAVE_DOCTOR = "insert into doctors (name, position, sur_name) values (?,?,?)";
    public final static String UPDATE_DOCTOR = "update doctors set name=?, position=?, sur_name=? where d_id=?";
    public final static String SAVE_PATIENT = "insert into patients (age, name, sur_name) values (?,?,?)";
    public final static String UPDATE_PATIENT = "update patients set age=?, name=?, sur_name=? where p_id=?";
    public final static String GET_DOCTOR_BY_ID = "select d.d_id as d_id, d.name as d_name, d.position as d_pos," +
            " d.sur_name as d_sn, p.p_id as p_id, p.name as p_name, p.sur_name as p_sn, p.age as p_age" +
            " from doctors d left join patient_doctor pd on d.d_id = pd.d_id left join patients p on p.p_id = pd.p_id where d.d_id = ?";
    public final static String GET_PATIENT_BY_ID = "select p.p_id as p_id, p.name as p_name, p.sur_name as p_sn," +
            " p.age as p_age, d.d_id as d_id, d.name as d_name, d.position as d_pos, d.sur_name as d_sn " +
            "from patients p left join patient_doctor pd on p.p_id = pd.p_id left join doctors d on d.d_id = pd.d_id where p.p_id = ?";
}
