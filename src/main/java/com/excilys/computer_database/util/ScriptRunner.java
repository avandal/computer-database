package com.excilys.computer_database.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computer_database.App;
import com.excilys.computer_database.AppConfigTest;

public class ScriptRunner {
	private static final String URL = "src/resources";
	
	private DataSource datasource;
	
	public void run() throws IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		datasource = context.getBean(DataSource.class);
		
		StringBuilder lines = new StringBuilder();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(URL + App.CONFIG_TEST));
		bufferedReader.lines().forEach(lines::append);
		bufferedReader.close();
		
		for (String line : lines.toString().split(";")) {
			try (Connection connection = datasource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(line);) {
			
				statement.executeUpdate(line);
			} catch (SQLException e) {
				System.out.println("Error when executing script");
				e.printStackTrace();
			}
		}
	}
}
