INSERT INTO doctors (d_id, name, position, sur_name) VALUES
(1, 'Ivan', 'dentist','Mihailov'),
(2, 'John', 'optometrist','Smith'),
(3, 'Nick', 'psychiatrist','Oldrich'),
(4, 'Peter', 'therapist','Young'),
(5, 'Eva', 'family_doctor','Mendez');

INSERT INTO patients (p_id, age, name, sur_name) VALUES
(1, 25, 'Michael','Jackson'),
(2, 31, 'John','Travolta'),
(3, 35, 'Nina','Richy'),
(4, 19, 'Peter','Pan'),
(5, 55, 'Angelina','Joly');

INSERT INTO patient_doctor (p_id, d_id) VALUES
(1, 3),
(1, 4),
(1, 5),
(2, 1),
(2, 3),
(2, 5),
(3, 5),
(4, 3),
(4, 4),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5);
