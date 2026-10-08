package com.tka.service;

import com.tka.pojo.Patient;

public class PatientRoutingService {

	public boolean isCritical(Patient patient) {

		// Rule 1
		if ("Critical".equalsIgnoreCase(patient.getConditionStatus())) {
			return true;
		}

		// Rule 2
		if ("Emergency".equalsIgnoreCase(patient.getAdmissionType())
				&& patient.getTriageScore() >= 7) {
			return true;
		}

		// Rule 3
		if ("Moderate".equalsIgnoreCase(patient.getConditionStatus())
				&& patient.getTriageScore() >= 8) {
			return true;
		}

		// Otherwise General Care
		return false;
	}
}