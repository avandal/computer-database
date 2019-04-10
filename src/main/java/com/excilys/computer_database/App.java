package com.excilys.computer_database;

import com.excilys.computer_database.control.ControlerCLI;

/**
 * Hello world!
 *
 */
public class App {
	public static final String DATASOURCE = "/datasource.properties";
	public static final String DATASOURCE_TEST = "/datasourceTest.properties";

	public static final String CONFIG_TEST = "/config_test.sql";
	
	public static void main(String[] args) {
		ControlerCLI control = new ControlerCLI();
		control.run();
	}
}
