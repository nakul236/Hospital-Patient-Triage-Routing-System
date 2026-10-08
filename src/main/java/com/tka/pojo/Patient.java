package com.tka.pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class Patient {

		private int patientId;
		private int age;
		private int triageScore;
		private String patientName;
		private String gender;
		private String disease;
		private String admissionType;
		private String conditionStatus;
		private String doctorName;
		private String mobile;
		private String transferStatus;

		private Date admissionDate;
		private Timestamp processedAt;

		public Patient() {
			super();
		}

		public Patient(int patientId, int age, int triageScore, String patientName,
				String gender, String disease, String admissionType,
				String conditionStatus, String doctorName, String mobile,
				String transferStatus, Date admissionDate, Timestamp processedAt) {
			super();
			this.patientId = patientId;
			this.age = age;
			this.triageScore = triageScore;
			this.patientName = patientName;
			this.gender = gender;
			this.disease = disease;
			this.admissionType = admissionType;
			this.conditionStatus = conditionStatus;
			this.doctorName = doctorName;
			this.mobile = mobile;
			this.transferStatus = transferStatus;
			this.admissionDate = admissionDate;
			this.processedAt = processedAt;
		}

		public int getPatientId() {
			return patientId;
		}

		public void setPatientId(int patientId) {
			this.patientId = patientId;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public int getTriageScore() {
			return triageScore;
		}

		public void setTriageScore(int triageScore) {
			this.triageScore = triageScore;
		}

		public String getPatientName() {
			return patientName;
		}

		public void setPatientName(String patientName) {
			this.patientName = patientName;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getDisease() {
			return disease;
		}

		public void setDisease(String disease) {
			this.disease = disease;
		}

		public String getAdmissionType() {
			return admissionType;
		}

		public void setAdmissionType(String admissionType) {
			this.admissionType = admissionType;
		}

		public String getConditionStatus() {
			return conditionStatus;
		}

		public void setConditionStatus(String conditionStatus) {
			this.conditionStatus = conditionStatus;
		}

		public String getDoctorName() {
			return doctorName;
		}

		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getTransferStatus() {
			return transferStatus;
		}

		public void setTransferStatus(String transferStatus) {
			this.transferStatus = transferStatus;
		}

		public Date getAdmissionDate() {
			return admissionDate;
		}

		public void setAdmissionDate(Date admissionDate) {
			this.admissionDate = admissionDate;
		}

		public Timestamp getProcessedAt() {
			return processedAt;
		}

		public void setProcessedAt(Timestamp processedAt) {
			this.processedAt = processedAt;
		}

		@Override
		public String toString() {
			return "Patient [patientId=" + patientId
					+ ", patientName=" + patientName
					+ ", age=" + age
					+ ", gender=" + gender
					+ ", disease=" + disease
					+ ", admissionType=" + admissionType
					+ ", conditionStatus=" + conditionStatus
					+ ", triageScore=" + triageScore
					+ ", doctorName=" + doctorName
					+ ", admissionDate=" + admissionDate
					+ ", mobile=" + mobile
					+ ", transferStatus=" + transferStatus
					+ ", processedAt=" + processedAt + "]";
		}

}
