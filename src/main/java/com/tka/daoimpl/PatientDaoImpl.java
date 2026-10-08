package com.tka.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tka.connection.DBConnection;
import com.tka.dao.PatientDao;
import com.tka.pojo.Patient;

public class PatientDaoImpl implements PatientDao {

	private Connection connection = null;

	// 1. GET ALL PENDING PATIENTS
	@Override
	public List<Patient> getPendingPatients() {

		List<Patient> patients = new ArrayList<>();

		String query = "SELECT patient_id, patient_name, age, gender, disease, "
				+ "admission_type, condition_status, triage_score, doctor_name, "
				+ "admission_date, mobile, transfer_status, processed_at "
				+ "FROM hospital_patient_intake "
				+ "WHERE transfer_status = 'PENDING'";

		try {

			connection = DBConnection.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Patient patient = new Patient();

				patient.setPatientId(resultSet.getInt("patient_id"));
				patient.setPatientName(resultSet.getString("patient_name"));
				patient.setAge(resultSet.getInt("age"));
				patient.setGender(resultSet.getString("gender"));
				patient.setDisease(resultSet.getString("disease"));
				patient.setAdmissionType(resultSet.getString("admission_type"));
				patient.setConditionStatus(resultSet.getString("condition_status"));
				patient.setTriageScore(resultSet.getInt("triage_score"));
				patient.setDoctorName(resultSet.getString("doctor_name"));
				patient.setAdmissionDate(resultSet.getDate("admission_date"));
				patient.setMobile(resultSet.getString("mobile"));
				patient.setTransferStatus(resultSet.getString("transfer_status"));
				patient.setProcessedAt(resultSet.getTimestamp("processed_at"));

				patients.add(patient);
			}

			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println("Get Pending Patients Error : " + e);

		} finally {

			DBConnection.closeConnection();
		}

		return patients;
	}


	// 2. ROUTE PATIENT
	@Override
	public boolean routePatient(Patient patient, boolean critical) {

		try {

			connection = DBConnection.getConnection();

			connection.setAutoCommit(false);

			// STEP 1 : CHECK DUPLICATE TRANSFER
			if (alreadyTransferred(patient.getPatientId())) {

				System.out.println("Patient "
						+ patient.getPatientId()
						+ " already transferred. Skipping...");

				connection.rollback();

				return false;
			}

			
			// STEP 2 : INSERT INTO DESTINATION TABLE
			boolean insertResult;

			if (critical) {

				insertResult = insertCriticalPatient(patient);

			} else {

				insertResult = insertGeneralPatient(patient);
			}


			// STEP 3 : IF INSERT FAILS -> ROLLBACK
			if (!insertResult) {

				connection.rollback();

				System.out.println("Insert Failed. Transaction Rollback.");

				return false;
			}


			// STEP 4 : UPDATE SOURCE TABLE
			boolean updateResult = markProcessed(patient.getPatientId());

			// STEP 5 : IF UPDATE FAILS -> ROLLBACK
			if (!updateResult) {

				connection.rollback();

				System.out.println("Update Failed. Transaction Rollback.");

				return false;
			}

			// STEP 6 : COMMIT TRANSACTION
			connection.commit();

			return true;

		} catch (SQLException e) {

			System.out.println("Transaction Error : " + e);

			try {

				if (connection != null) {

					connection.rollback();

					System.out.println("Transaction Rollback Done.");

				}

			} catch (SQLException rollbackException) {

				System.out.println(
						"Rollback Error : " + rollbackException);
			}

			return false;

		} finally {

			DBConnection.closeConnection();
		}
	}

	// 3. CHECK WHETHER PATIENT IS ALREADY TRANSFERRED
	private boolean alreadyTransferred(int patientId) {

		String criticalQuery =
				"SELECT source_patient_id "
				+ "FROM critical_care_patients "
				+ "WHERE source_patient_id = ?";

		String generalQuery =
				"SELECT source_patient_id "
				+ "FROM general_care_patients "
				+ "WHERE source_patient_id = ?";

		try {

			// Check Critical Care Table
			PreparedStatement criticalStatement =
					connection.prepareStatement(criticalQuery);

			criticalStatement.setInt(1, patientId);

			ResultSet criticalResult =
					criticalStatement.executeQuery();

			if (criticalResult.next()) {

				criticalResult.close();
				criticalStatement.close();

				return true;
			}

			criticalResult.close();
			criticalStatement.close();

			// Check General Care Table
			PreparedStatement generalStatement =
					connection.prepareStatement(generalQuery);

			generalStatement.setInt(1, patientId);

			ResultSet generalResult =
					generalStatement.executeQuery();

			if (generalResult.next()) {

				generalResult.close();
				generalStatement.close();

				return true;
			}

			generalResult.close();
			generalStatement.close();

		} catch (SQLException e) {

			System.out.println(
					"Duplicate Check Error : " + e);

		}

		return false;
	}


	// 4. INSERT PATIENT INTO CRITICAL CARE TABLE
	private boolean insertCriticalPatient(Patient patient) {

		String query =
				"INSERT INTO critical_care_patients "
				+ "(source_patient_id, patient_name, age, disease, "
				+ "admission_type, condition_status, triage_score, doctor_name) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			PreparedStatement preparedStatement =
					connection.prepareStatement(query);

			preparedStatement.setInt(
					1, patient.getPatientId());

			preparedStatement.setString(
					2, patient.getPatientName());

			preparedStatement.setInt(
					3, patient.getAge());

			preparedStatement.setString(
					4, patient.getDisease());

			preparedStatement.setString(
					5, patient.getAdmissionType());

			preparedStatement.setString(
					6, patient.getConditionStatus());

			preparedStatement.setInt(
					7, patient.getTriageScore());

			preparedStatement.setString(
					8, patient.getDoctorName());

			int result = preparedStatement.executeUpdate();

			preparedStatement.close();

			return result == 1;

		} catch (SQLException e) {

			System.out.println(
					"Critical Patient Insert Error : " + e);

			return false;
		}
	}

	// 5. INSERT PATIENT INTO GENERAL CARE TABLE
	private boolean insertGeneralPatient(Patient patient) {

		String query =
				"INSERT INTO general_care_patients "
				+ "(source_patient_id, patient_name, age, disease, "
				+ "admission_type, condition_status, triage_score, doctor_name) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			PreparedStatement preparedStatement =
					connection.prepareStatement(query);

			preparedStatement.setInt(
					1, patient.getPatientId());

			preparedStatement.setString(
					2, patient.getPatientName());

			preparedStatement.setInt(
					3, patient.getAge());

			preparedStatement.setString(
					4, patient.getDisease());

			preparedStatement.setString(
					5, patient.getAdmissionType());

			preparedStatement.setString(
					6, patient.getConditionStatus());

			preparedStatement.setInt(
					7, patient.getTriageScore());

			preparedStatement.setString(
					8, patient.getDoctorName());

			int result = preparedStatement.executeUpdate();

			preparedStatement.close();

			return result == 1;

		} catch (SQLException e) {

			System.out.println(
					"General Patient Insert Error : " + e);

			return false;
		}
	}

	// 6. UPDATE SOURCE TABLE
	private boolean markProcessed(int patientId) {

		String query =
				"UPDATE hospital_patient_intake "
				+ "SET transfer_status = 'PROCESSED', "
				+ "processed_at = NOW() "
				+ "WHERE patient_id = ? "
				+ "AND transfer_status = 'PENDING'";

		try {

			PreparedStatement preparedStatement =
					connection.prepareStatement(query);

			preparedStatement.setInt(1, patientId);

			int result = preparedStatement.executeUpdate();

			preparedStatement.close();

			return result == 1;

		} catch (SQLException e) {

			System.out.println(
					"Mark Processed Error : " + e);

			return false;
		}
	}
}