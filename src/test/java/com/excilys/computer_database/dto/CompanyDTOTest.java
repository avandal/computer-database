package com.excilys.computer_database.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class CompanyDTOTest {
	private CompanyDTOBuilder builderCompanyDTO1 = new CompanyDTOBuilder();
	private CompanyDTOBuilder builderCompanyDTO2 = new CompanyDTOBuilder();
	
	private CompanyDTO companyDTO1;
	private CompanyDTO companyDTO2;
	
	@Before
	public void setUp() {
		companyDTO1 = builderCompanyDTO1.empty().build();
		companyDTO2 = builderCompanyDTO2.empty().build();
	}
	
	@Test
	public void testNullEquals() {
		// Both are same ref
		assertEquals(companyDTO1, companyDTO1);
		// Compare with null
		assertNotEquals(companyDTO1, null);
		// Compare with other class
		assertNotEquals(companyDTO1, new Object());
	}
	
	@Test
	public void testIdEquals() {
		// null or not null
		companyDTO1 = builderCompanyDTO1.id(null).build();
		companyDTO2 = builderCompanyDTO2.id("no").build();
		assertNotEquals(companyDTO1, companyDTO2);
		
		companyDTO1 = builderCompanyDTO1.id("no").build();
		companyDTO2 = builderCompanyDTO2.id(null).build();
		assertNotEquals(companyDTO1, companyDTO2);
		
		companyDTO1 = builderCompanyDTO1.id(null).build();
		companyDTO2 = builderCompanyDTO2.id(null).build();
		assertEquals(companyDTO1, companyDTO2);
		
		// Different
		companyDTO1 = builderCompanyDTO1.id("one").build();
		companyDTO2 = builderCompanyDTO2.id("two").build();
		assertNotEquals(companyDTO1, companyDTO2);
		
		// Same value
		companyDTO1 = builderCompanyDTO1.id("same").build();
		companyDTO2 = builderCompanyDTO2.id("same").build();
		assertEquals(companyDTO1, companyDTO2);
	}
	
	@Test
	public void testNameEquals() {
		// null or not null
		companyDTO1 = builderCompanyDTO1.name(null).build();
		companyDTO2 = builderCompanyDTO2.name("no").build();
		assertNotEquals(companyDTO1, companyDTO2);
		
		companyDTO1 = builderCompanyDTO1.name("no").build();
		companyDTO2 = builderCompanyDTO2.name(null).build();
		assertNotEquals(companyDTO1, companyDTO2);
		
		companyDTO1 = builderCompanyDTO1.name(null).build();
		companyDTO2 = builderCompanyDTO2.name(null).build();
		assertEquals(companyDTO1, companyDTO2);
		
		// Different
		companyDTO1 = builderCompanyDTO1.name("one").build();
		companyDTO2 = builderCompanyDTO2.name("two").build();
		assertNotEquals(companyDTO1, companyDTO2);
		
		// Same value
		companyDTO1 = builderCompanyDTO1.name("same").build();
		companyDTO2 = builderCompanyDTO2.name("same").build();
		assertEquals(companyDTO1, companyDTO2);
	}
	
	@Test
	public void testGlobalEquals() {
		testNullEquals();
		testIdEquals();
		testNameEquals();
		
		assertEquals(companyDTO1, companyDTO2);
	}
}
