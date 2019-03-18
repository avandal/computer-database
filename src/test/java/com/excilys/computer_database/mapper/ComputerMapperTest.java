package com.excilys.computer_database.mapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mock;

import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;

import junit.framework.TestCase;

public class ComputerMapperTest extends TestCase {

	@Mock
	private ResultSet res;
	
	@Test
	public void testResultSetComputer() throws SQLException {
		res = mock(ResultSet.class);
		
		Computer expected = new ComputerBuilder().random().build();
		
		when(res.getInt("ct.id")).thenReturn(expected.getId());
		when(res.getString("ct.name")).thenReturn(expected.getName());
		when(res.getTimestamp("ct.introduced")).thenReturn(expected.getIntroduced());
		when(res.getTimestamp("ct.discontinued")).thenReturn(expected.getDiscontinued());
		when(res.getInt("ct.company_id")).thenReturn(expected.getCompany().getId());
		when(res.getInt("cn.id")).thenReturn(expected.getCompany().getId());
		when(res.getString("cn.name")).thenReturn(expected.getCompany().getName());
		
		Computer initial = ComputerMapper.resultSetComputer(res);
		
		assertEquals(initial, expected);
	}
}
