package com.excilys.computer_database.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computer_database.model.Computer;

public class ComputerDAOTest {
	private Connection con;
	
	@Before
	public void before() {
		try {
			if (con != null && !con.isClosed()) {
				con = DAO.getConnection();
			}
		} catch (SQLException e) {
			fail("Error on connection to database");
		}
	}
	
	@After
	public void after() {
		try {
			if (con == null || con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			fail("Error on disconnection to database");
		}
	}

	@Test
	public void getComputerDetailsTest() {
		Computer computer;
		
		computer = ComputerDAO.getComputerDetails(1);
		assertNotNull("The computer [1] exists on database",computer);
		
	}

}
