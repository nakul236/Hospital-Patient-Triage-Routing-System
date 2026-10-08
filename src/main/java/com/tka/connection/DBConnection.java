package com.tka.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static String driver_class = "com.mysql.cj.jdbc.Driver";

	private static String database_url = "jdbc:mysql://localhost:3306/";

	private static String database_name = "hospital";

	private static String database_username = "root";

	private static String database_password = "root";

	private static Connection connection = null;

	public static Connection getConnection() {

		try {

			Class.forName(driver_class);

			connection = DriverManager.getConnection(
					database_url + database_name,
					database_username,
					database_password);

			System.out.println("Database Connection Done...");

		} catch (ClassNotFoundException e) {

			System.out.println("Driver Error : " + e);

		} catch (SQLException e) {

			System.out.println("Database Connection Error : " + e);
		}

		return connection;
	}

	public static void closeConnection() {

		try {

			if (connection != null) {

				connection.close();

				connection = null;
			}

			System.out.println("Connection Close...");

		} catch (SQLException e) {

			System.out.println("Close Connection Error : " + e);
		}
	}
}