package com.excilys.computer_database.model;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class CompanyTest extends TestCase {
	
	private CompanyBuilder builderCompany1 = new CompanyBuilder();
	private CompanyBuilder builderCompany2 = new CompanyBuilder();
	
	Company company1;
	Company company2;

	public CompanyTest() {}
	
	@Before
	public void setUp() {
		company1 = builderCompany1.empty().build();
		company2 = builderCompany2.empty().build();
	}
	
	@Test
	public void testNullEquals() {
		// Both are same ref
		assertEquals(company1, company1);
		// Compare with null
		assertNotEquals(company1, null);
		// Compare with other class
		assertNotEquals(company1, new Object());
	}
	
	@Test
	public void testIdEquals() {
		company1 = builderCompany1.id(2).build();
		company2 = builderCompany2.id(3).build();
		
		// Different ids
		company1 = builderCompany1.id(2).build();
		company2 = builderCompany2.id(3).build();
		assertNotEquals(company1, company2);
		
		// Same value
		company1 = builderCompany1.id(1).build();
		company2 = builderCompany2.id(1).build();
		assertEquals(company1, company2);
	}
	
	@Test
	public void testNameEquals() {
		// null or not null
		company1 = builderCompany1.name(null).build();
		company2 = builderCompany2.name("no").build();
		assertNotEquals(company1, company2);
		
		company1 = builderCompany1.name("no").build();
		company2 = builderCompany2.name(null).build();
		assertNotEquals(company1, company2);
		
		company1 = builderCompany1.name(null).build();
		company2 = builderCompany2.name(null).build();
		assertEquals(company1, company2);
		
		// Different
		company1 = builderCompany1.name("one").build();
		company2 = builderCompany2.name("two").build();
		assertNotEquals(company1, company2);
		
		// Same value
		company1 = builderCompany1.name("same").build();
		company2 = builderCompany2.name("same").build();
		assertEquals(company1, company2);
	}
	
	@Test
	public void testGlobalEquals() {
		testNullEquals();
		testIdEquals();
		testNameEquals();
		
		assertEquals(company1, company2);
	}
}
