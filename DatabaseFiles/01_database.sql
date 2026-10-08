CREATE TABLE hospital_patient_intake (patient_id INT PRIMARY KEY AUTO_INCREMENT, patient_name VARCHAR(100), age INT, gender VARCHAR(10),
    disease VARCHAR(120), admission_type VARCHAR(20), condition_status VARCHAR(20), triage_score INT, doctor_name VARCHAR(100), admission_date DATE,
    mobile VARCHAR(15), transfer_status VARCHAR(20) DEFAULT 'PENDING', processed_at DATETIME, created_at DATETIME DEFAULT CURRENT_TIMESTAMP);
    desc hospital_patient_intake;
    
CREATE TABLE critical_care_patients (critical_id INT PRIMARY KEY AUTO_INCREMENT, source_patient_id INT UNIQUE, patient_name VARCHAR(100),
age INT, disease VARCHAR(120), admission_type VARCHAR(20), condition_status VARCHAR(20), triage_score INT,doctor_name VARCHAR(100),
routed_at DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (source_patient_id)
REFERENCES hospital_patient_intake(patient_id));
desc critical_care_patients;
    
CREATE TABLE general_care_patients (general_id INT PRIMARY KEY AUTO_INCREMENT, source_patient_id INT UNIQUE, patient_name VARCHAR(100),
age INT, disease VARCHAR(120), admission_type VARCHAR(20), condition_status VARCHAR(20), triage_score INT, doctor_name VARCHAR(100),
routed_at DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (source_patient_id)
    REFERENCES hospital_patient_intake(patient_id));