package com.excilys.computer_database.service.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/datasource.properties")
@ComponentScan(basePackages = {"com.excilys.computer_database.console.control",
							   "com.excilys.computer_database.console.view",
							   "com.excilys.computer_database.persistence",
							   "com.excilys.computer_database.service.service",
							   "com.excilys.computer_database.webapp.control"})
public class HibernateConfig {

	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean fact = new LocalSessionFactoryBean();
		fact.setDataSource(datasource());
		fact.setPackagesToScan("com.excilys.computer_database.core.model");
		fact.setHibernateProperties(hibernateProperties());

		return fact;
	}
	
	@Bean
    public HikariDataSource datasource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(env.getRequiredProperty("jdbcUrl"));
		dataSource.setUsername(env.getRequiredProperty("db.username"));
		dataSource.setPassword(env.getRequiredProperty("db.password"));
		dataSource.setDriverClassName(env.getRequiredProperty("driverClassName"));
		
        return dataSource;
    }

	Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

		return properties;
	}
}
