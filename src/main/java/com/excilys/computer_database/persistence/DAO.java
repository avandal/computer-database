//package com.excilys.computer_database.persistence;
//
//import java.sql.Connection;
//
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public abstract class DAO {
//	private static Connection connection = null;
//	
//	protected static final String DATABASE = "computer-database-db";
//	protected static final String TIMEZONE = "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//	protected static final String DRIVER = "com.mysql.cj.jdbc.Driver";
//	protected static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + TIMEZONE;
//	protected static final String USER = "admincdb";
//	protected static final String PASSWORD = "qwerty1234";
//	
//	private static Logger logger = LoggerFactory.getLogger(DAO.class);
//	
//	private static void initConnection() {
//		try {
//			Class.forName(DRIVER);
//			connection = DriverManager.getConnection(URL, USER, PASSWORD);
//		} catch (ClassNotFoundException e) {
//			logger.error("initConnection - Class not found");
//			e.printStackTrace();
//		} catch (SQLException e) {
//			logger.error("initConnection - Error when connecting on database");
//			e.printStackTrace();
//		}
//	}
//	
//	public static Connection getConnection() throws SQLException {
//		if (connection == null || connection.isClosed()) {
//			initConnection();
//		}
//		
//		return connection;
//	}
//}
