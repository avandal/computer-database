package com.excilys.computer_database.persistence;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CompanyDAO.class)
public class CompanyDAOTest extends TestCase {
	
	CompanyDAO companyDao;
	
	@Mock
	Connection con;
	
	@Mock
	ResultSet res;
	
	@Mock
	PreparedStatement stmt;
	
	@Mock
	Company company;
	

	@Test
	public void testGetCompanyById() throws SQLException {
		mockStatic(DAO.class);
//		
//		dao = mock(DAO.class, Mockito.CALLS_REAL_METHODS);
		
		companyDao = CompanyDAO.getInstance();
		
		when(DAO.getConnection()).thenReturn(con);
		when(con.prepareStatement(CompanyDAO.SELECT_ONE_COMPANY)).thenReturn(stmt);
		when(stmt.executeQuery()).thenReturn(res);
		
		Company company = new Company(1, "A random name");
		when(CompanyMapper.resultSetCompany(res)).thenReturn(company.copy());
		when(companyDao.getCompanyById(company.getId())).thenReturn(this.company);
		
		assertEquals(company, this.company);
	}
}
