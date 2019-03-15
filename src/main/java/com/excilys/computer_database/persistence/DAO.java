package com.excilys.computer_database.persistence;

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
	
	private static void initConnection() {
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
}
