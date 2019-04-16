package com.excilys.computer_database.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.computer_database.App;
import com.excilys.computer_database.AppConfigTest;

public class ScriptRunner {
	private static final String URL = "src/resources";
	
	private JdbcTemplate jdbcTemplate;
	
	public void run() throws IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		jdbcTemplate = context.getBean(JdbcTemplate.class);
		
		StringBuilder lines = new StringBuilder();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(URL + App.CONFIG_TEST));
		bufferedReader.lines().forEach(lines::append);
		bufferedReader.close();
		
		for (String line : lines.toString().split(";")) {
			jdbcTemplate.update(line);
//			try (Connection connection = datasource.getConnection();
//				 PreparedStatement statement = connection.prepareStatement(line);) {
//			
//				statement.executeUpdate(line);
//			} catch (SQLException e) {
//				System.out.println("Error when executing script");
//				e.printStackTrace();
//			}
		}
		context.close();
	}
}
