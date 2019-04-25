package com.excilys.computer_database;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.control.ControllerCLI;

public class App {
	public static final String DATASOURCE = "/datasource.properties";
	public static final String DATASOURCE_TEST = "/datasourceTest.properties";

	public static final String CONFIG_TEST = "/config_test.sql";

	public static void main(String[] args) {
		GenericApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
		ControllerCLI control = context.getBean(ControllerCLI.class);
		control.run();
		context.close();
	}
}
