package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import junit.framework.TestCase;

public class DAOTest extends TestCase {

	public DAOTest(String name) {
		super(name);
	}
	
	@Test
	public void testGetConnection() {
		try {
			Connection con = DAO.getConnection();
			assertTrue(con != null);
		} catch (SQLException e) {
			fail("Fail test connection to database");
		}
	}

}
