package com.tka.validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tka.pojo.Patient;

public class PatientValidator {

	public List<String> validatePatient(Patient patient) {

		List<String> errors = new ArrayList<>();

		// Patient Name
		if (patient.getPatientName() == null
				|| patient.getPatientName().trim().isEmpty()) {

			errors.add("Patient name is blank");

		} else if (patient.getPatientName().trim().length() < 3) {

			errors.add("Patient name must have minimum 3 characters");
		}

		// Age
		if (patient.getAge() < 0 || patient.getAge() > 120) {

			errors.add("Invalid age");
		}

		// Gender
		if (patient.getGender() == null
				|| !(patient.getGender().equalsIgnoreCase("Male")
				|| patient.getGender().equalsIgnoreCase("Female")
				|| patient.getGender().equalsIgnoreCase("Other"))) {

			errors.add("Invalid gender");
		}

		// Disease
		if (patient.getDisease() == null
				|| patient.getDisease().trim().isEmpty()) {

			errors.add("Disease is blank");
		}

		// Admission Type
		if (patient.getAdmissionType() == null
				|| !(patient.getAdmissionType().equalsIgnoreCase("Emergency")
				|| patient.getAdmissionType().equalsIgnoreCase("Regular"))) {

			errors.add("Invalid admission type");
		}

		// Condition Status
		if (patient.getConditionStatus() == null
				|| !(patient.getConditionStatus().equalsIgnoreCase("Critical")
				|| patient.getConditionStatus().equalsIgnoreCase("Moderate")
				|| patient.getConditionStatus().equalsIgnoreCase("Stable"))) {

			errors.add("Invalid condition status");
		}

		// Triage Score
		if (patient.getTriageScore() < 1
				|| patient.getTriageScore() > 10) {

			errors.add("Invalid triage score");
		}

		// Doctor Name
		if (patient.getDoctorName() == null
				|| patient.getDoctorName().trim().isEmpty()) {

			errors.add("Doctor name is blank");
		}

		// Admission Date
		if (patient.getAdmissionDate() == null) {

			errors.add("Admission date is missing");

		} else if (patient.getAdmissionDate().toLocalDate()
				.isAfter(LocalDate.now())) {

			errors.add("Admission date cannot be future date");
		}

		// Mobile
		if (patient.getMobile() == null
				|| !patient.getMobile().matches("\\d{10}")) {

			errors.add("Mobile number must contain exactly 10 digits");
		}

		// Transfer Status
		if (patient.getTransferStatus() == null
				|| !patient.getTransferStatus().equalsIgnoreCase("PENDING")) {

			errors.add("Patient is not in PENDING status");
		}

		return errors;
	}
}