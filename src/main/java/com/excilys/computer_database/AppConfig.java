package com.excilys.computer_database;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.computer_database.persistence.CompanyDAO;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@Configuration
@ComponentScan({"com.excilys"})
public class AppConfig {
	public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@Bean
	public ComputerService computerService() {
		return new ComputerService(computerDAO(), companyService());
	}
	
	@Bean
	public CompanyService companyService() {
		return new CompanyService(companyDAO());
	}
	
	@Bean
	public ComputerDAO computerDAO() {
		return new ComputerDAO(companyDAO(), App.DATASOURCE);
	}
	
	@Bean
	public CompanyDAO companyDAO() {
		return new CompanyDAO(App.DATASOURCE);
	}
}
