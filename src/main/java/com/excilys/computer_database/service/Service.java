package com.excilys.computer_database.service;

public abstract class Service {
	protected static final String DATABASE = "computer-database-db";
	protected static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	protected static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE;
	protected static final String USER = "admincdb";
	protected static final String PASSWORD = "qwerty1234";
}
