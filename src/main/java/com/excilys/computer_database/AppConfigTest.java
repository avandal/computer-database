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

import com.excilys.computer_database.persistence.CompanyDAO;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@Configuration
@ComponentScan(basePackages = { "persistence", "service", "util" })
@PropertySource("classpath:/datasourceTest.properties")
public class AppConfigTest {

//	@Value("${jdbcUrl}")
//	private String jdbcUrl;
//
//	@Value("${db.username}")
//	private String user;
//
//	@Value("${db.password}")
//	private String password;
//	
//	@Value("${dataSource.prepStmtCacheSize}")
//	private int prepStmtCacheSize;
//	
//	@Value("${dataSource.prepStmtCacheSqlLimit}")
//	private int prepStmtCacheSqlLimit;
//	
//	@Value("${dataSource.useServerPrepStmts}")
//	private boolean useServerPrepStmts;
//
//	@Value("${driverClassName}")
//	private String driverClassName;
//
//	@Value("${dataSource.connectionTimeout}")
//	private int connectionTimeout;
//
//	@Value("${dataSource.maximumPoolSize}")
//	private int maximumPoolSize;
//
//	@Bean
//	public DataSource datasource() {
//		Properties dsProps = new Properties();
//		dsProps.put("jdbcUrl", jdbcUrl);
//		dsProps.put("user", user);
//		dsProps.put("password", password);
//		dsProps.put("prepStmtCacheSize", prepStmtCacheSize);
//		dsProps.put("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
//		dsProps.put("cachePrepStmts", Boolean.TRUE);
//		dsProps.put("useServerPrepStmts", useServerPrepStmts);
//
//		Properties configProps = new Properties();
//		configProps.put("driverClassName", driverClassName);
//		configProps.put("maximumPoolSize", maximumPoolSize);
//		configProps.put("connectionTimeout", connectionTimeout);
//		configProps.put("dataSourceProperties", dsProps);
//
//		HikariConfig hc = new HikariConfig(configProps);
//		HikariDataSource ds = new HikariDataSource(hc);
//		DataSource datasource = ds.getDataSource();
//		ds.close();
//		return datasource;
//	}

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
		return new ComputerDAO(jdbcTemplate(), companyDAO());
	}

	@Bean
	public CompanyDAO companyDAO() {
		return new CompanyDAO(jdbcTemplate());
	}
}
