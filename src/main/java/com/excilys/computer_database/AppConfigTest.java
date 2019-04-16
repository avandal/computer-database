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
							   "com.excilys.computer_database.service",
							   "com.excilys.computer_database.servlets"})
@PropertySource("classpath:/datasourceTest.properties")
public class AppConfigTest {

	@Autowired
	Environment env;

	@Bean(destroyMethod="close")
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
}
