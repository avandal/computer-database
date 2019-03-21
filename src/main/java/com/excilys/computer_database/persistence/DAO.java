package com.excilys.computer_database.persistence;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAO {
	private static Connection connection = null;
	
	protected static String driver;
	protected static String url;
	protected static String user;
	protected static String password;
	
	private static Logger logger = LoggerFactory.getLogger(DAO.class);
	
	private static void initConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
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
