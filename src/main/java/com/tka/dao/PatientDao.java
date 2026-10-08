package com.tka.dao;

import java.util.List;

import com.tka.pojo.Patient;

public interface PatientDao {
	
	public List<Patient> getPendingPatients();
	
	public boolean routePatient(Patient patient, boolean critical);

}
