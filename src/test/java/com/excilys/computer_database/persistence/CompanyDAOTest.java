package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.mockito.Mock;

import junit.framework.TestCase;

public class CompanyDAOTest extends TestCase {
	
	@Mock
	Connection con;
	
	@Mock
	ResultSet res;
	
	@Mock
	PreparedStatement stmt;
	
	@Before
	public void setUp() {
		
	}
}
