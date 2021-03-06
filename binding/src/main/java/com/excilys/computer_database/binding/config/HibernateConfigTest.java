package com.excilys.computer_database.binding.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/datasourceTest.properties")
@ComponentScan(basePackages = {"com.excilys.computer_database.control", 
							   "com.excilys.computer_database.view", 
							   "com.excilys.computer_database.persistence",
							   "com.excilys.computer_database.service"})
public class HibernateConfigTest {
	
	@Autowired
	private Environment env;
	
	@Bean
	public AnnotationConfigApplicationContext context() {
		return new AnnotationConfigApplicationContext(this.getClass());
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean fact = new LocalSessionFactoryBean();
		fact.setDataSource(datasource());
		fact.setPackagesToScan("com.excilys.computer_database.model");
		fact.setHibernateProperties(hibernateProperties());

		return fact;
	}
	
	@Bean
	public DataSource datasource() {
		return DataSourceBuilder
				.create()
				.url(env.getProperty("jdbcUrl"))
				.driverClassName(env.getProperty("driverClassName"))
				.username(env.getProperty("db.username"))
				.password(env.getProperty("db.password"))
				.build();
	}

	Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

		return properties;
	}
}
