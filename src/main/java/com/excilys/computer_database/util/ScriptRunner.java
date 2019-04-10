package com.excilys.computer_database.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.excilys.computer_database.App;
import com.excilys.computer_database.persistence.ConnectionPool;

public class ScriptRunner {
	private static final String URL = "src/resources";
	
	public static void run() throws IOException {
		StringBuilder lines = new StringBuilder();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(URL + App.CONFIG_TEST));
		bufferedReader.lines().forEach(lines::append);
		bufferedReader.close();
		
		for (String line : lines.toString().split(";")) {
			try (Connection connection = ConnectionPool.getInstance(App.DATASOURCE_TEST).getDataSource().getConnection();
				 PreparedStatement statement = connection.prepareStatement(line);) {
			
				statement.executeUpdate(line);
			} catch (SQLException e) {
				System.out.println("Error when executing script");
				e.printStackTrace();
			}
		}
	}
}
