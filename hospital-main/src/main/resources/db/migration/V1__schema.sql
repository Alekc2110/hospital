DROP TABLE IF EXISTS doctors, patient_doctor, patients;
DROP SEQUENCE IF EXISTS doctors_d_id_seq, patients_p_id_seq;




CREATE SEQUENCE doctors_d_id_seq START WITH 6 INCREMENT BY 1;

create table doctors
(
    d_id INTEGER PRIMARY KEY DEFAULT NEXTVAL('doctors_d_id_seq'),
    name varchar(255) not null,
    position varchar(255),
    sur_name varchar(255)

);

CREATE SEQUENCE patients_p_id_seq START WITH 6 INCREMENT BY 1;

create table patients
(
    p_id INTEGER PRIMARY KEY DEFAULT NEXTVAL('patients_p_id_seq'),
    age int not null,
    name varchar(255) not null,
    sur_name varchar(255)
);



create table patient_doctor
(
    p_id BIGINT,
    d_id BIGINT,
    constraint FK_patient_doctor_p_id
        foreign key (p_id)
            references patients(p_id) ON DELETE CASCADE,
    constraint FK_patient_doctor_d_id
            foreign key (d_id)
            references doctors(d_id) ON DELETE CASCADE
);



