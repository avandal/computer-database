package com.excilys.computer_database.console;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.console.control.ControllerCLI;
import com.excilys.computer_database.service.config.HibernateConfig;

public class App {
	public final static String DATASOURCE = "/datasource.properties";
	public final static String DATASOURCE_TEST = "/datasourceTest.properties";

	public final static String CONFIG_TEST = "/config_test.sql";

	public static void main(String[] args) {
		GenericApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
		ControllerCLI control = context.getBean(ControllerCLI.class);
		control.run();
		context.close();
	}
}
