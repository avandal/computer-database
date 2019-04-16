package com.excilys.computer_database.mapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mock;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;
import com.excilys.computer_database.persistence.CompanyDAO;

import junit.framework.TestCase;

public class CompanyMapperTest extends TestCase {
	
	@Mock
	private ResultSet res;
	
	@Test
	public void testResultSetCompany() throws SQLException {
		res = mock(ResultSet.class);
		
		Company expected = new CompanyBuilder().random().build();
		
		when(res.getInt(CompanyDAO.ID_CN_ALIAS)).thenReturn(expected.getId());
		when(res.getString(CompanyDAO.NAME_CN_ALIAS)).thenReturn(expected.getName());
		
		Company initial = CompanyMapper.resultSetCompany(res);
		
		assertEquals(initial, expected);
	}
}
