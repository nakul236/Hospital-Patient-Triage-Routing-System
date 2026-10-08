package com.tka.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HospitalMain {

	public static void main(String[] args) {

		// Database connection
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Hospital", "root", "root");

			System.out.println("Connection successful");

			String query = "SELECT * FROM Hospital_patient";

			PreparedStatement ps = con.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			String highQuery = "INSERT INTO Hospital_patient_high VALUES (?, ?, ?)";

			String lessQuery = "INSERT INTO Hospital_patient_low VALUES (?, ?, ?)";

			PreparedStatement highPs = con.prepareStatement(highQuery);
			PreparedStatement lessPs = con.prepareStatement(lessQuery);

			while (rs.next()) {

				String name = rs.getString("patient_name");
				String city = rs.getString("patient_city");
				int expense = rs.getInt("patient_expence");

				if (expense > 10000) {

					highPs.setString(1, name);
					highPs.setString(2, city);
					highPs.setInt(3, expense);

					highPs.executeUpdate();

				} else {

					lessPs.setString(1, name);
					lessPs.setString(2, city);
					lessPs.setInt(3, expense);

					lessPs.executeUpdate();

				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
