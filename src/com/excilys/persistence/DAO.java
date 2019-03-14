package com.excilys.persistence;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAO {
	private static Connection connection = null;
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	private static Logger logger = LoggerFactory.getLogger(DAO.class);
	
	public static void initConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			logger.error("initConnection - Class not found");
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("initConnection - Error when connecting on database");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
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
			logger.error("closeConnection - Error on database access");
			e.printStackTrace();
		}
	}
}
