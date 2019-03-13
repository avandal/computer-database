package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {
	private static Connection connection = null;
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	static ResultSet query(String q) throws SQLException {
		if (connection != null) {
			Statement stmt = connection.createStatement();
			ResultSet ret = stmt.executeQuery(q);
			
			return ret;
		}
		return null;
	}
	
	public static void initConnection() {
		if (connection == null) {
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if (connection == null) {
			initConnection();
		}
		
		return connection;
	}
	
	public static void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
