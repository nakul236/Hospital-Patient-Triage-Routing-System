package com.tka.main;

import java.util.List;

import com.tka.dao.PatientDao;
import com.tka.daoimpl.PatientDaoImpl;
import com.tka.pojo.Patient;
import com.tka.service.PatientRoutingService;
import com.tka.validator.PatientValidator;

public class PatientMain {

	public static void main(String[] args) {

		PatientDao patientDAO = new PatientDaoImpl();

		PatientRoutingService routingService =
				new PatientRoutingService();

		PatientValidator validator =
				new PatientValidator();

		List<Patient> patients =
				patientDAO.getPendingPatients();

		int criticalCount = 0;
		int generalCount = 0;
		int validationFailed = 0;
		int skippedDuplicate = 0;

		System.out.println();
		System.out.println("KIRAN ACADEMY - HOSPITAL PATIENT ROUTING");
		System.out.println("---------------------------------------");

		for (Patient patient : patients) {
			List<String> errors =
					validator.validatePatient(patient);

			if (!errors.isEmpty()) {

				validationFailed++;

				System.out.println(
						"Patient "
						+ patient.getPatientId()
						+ " -> FAILED -> "
						+ errors.get(0));

				continue;
			}


			boolean critical =
					routingService.isCritical(patient);

			boolean result =
					patientDAO.routePatient(patient, critical);

			if (result) {

				if (critical) {

					criticalCount++;

					System.out.println(
							"Patient "
							+ patient.getPatientId()
							+ " -> CRITICAL CARE -> SUCCESS");

				} else {

					generalCount++;

					System.out.println(
							"Patient "
							+ patient.getPatientId()
							+ " -> GENERAL CARE  -> SUCCESS");
				}

			} else {

				skippedDuplicate++;

				System.out.println(
						"Patient "
						+ patient.getPatientId()
						+ " -> SKIPPED -> Already processed");
			}
		}


		System.out.println();
		System.out.println("PROCESSING SUMMARY");
		System.out.println("---------------------------------------");

		System.out.println(
				"Total Pending Records : " + patients.size());

		System.out.println(
				"Critical Care         : " + criticalCount);

		System.out.println(
				"General Care          : " + generalCount);

		System.out.println(
				"Validation Failed     : " + validationFailed);

		System.out.println(
				"Skipped Duplicate     : " + skippedDuplicate);
	}
}