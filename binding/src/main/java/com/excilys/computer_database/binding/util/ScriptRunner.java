package com.excilys.computer_database.binding.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.computer_database.binding.config.HibernateConfigTest;

public class ScriptRunner {
	private final static String URL = "src/resources/config_test.sql";
	
	public void run() throws IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfigTest.class);
		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
		
		StringBuilder lines = new StringBuilder();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(URL));
		bufferedReader.lines().forEach(lines::append);
		bufferedReader.close();
		
		for (String line : lines.toString().split(";")) {
			jdbcTemplate.update(line);
		}
		
		context.close();
	}
}
