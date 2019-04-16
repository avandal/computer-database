package com.excilys.computer_database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages = {"com.excilys.computer_database.control", 
							   "com.excilys.computer_database.view", 
							   "com.excilys.computer_database.persistence",
							   "com.excilys.computer_database.service"})
@PropertySource("classpath:/datasource.properties")
public class AppConfig {
//	public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@Autowired
	Environment env;
	
	@Bean
	public DataSource datasource() {
		DataSource datasource = DataSourceBuilder
				.create()
				.url(env.getProperty("jdbcUrl"))
				.driverClassName(env.getProperty("driverClassName"))
				.username(env.getProperty("db.username"))
				.password(env.getProperty("db.password"))
				.build();
		
		return datasource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbc = new JdbcTemplate();
		jdbc.setDataSource(datasource());
		return jdbc;
	}
	
//	@Bean
//	public ComputerService computerService() {
//		return new ComputerService(computerDAO(), companyService());
//	}
//	
//	@Bean
//	public CompanyService companyService() {
//		return new CompanyService(companyDAO());
//	}
//	
//	@Bean
//	public ComputerDAO computerDAO() {
//		return new ComputerDAO(jdbcTemplate(), companyDAO());
//	}
//	
//	@Bean
//	public CompanyDAO companyDAO() {
//		return new CompanyDAO(jdbcTemplate());
//	}
}
