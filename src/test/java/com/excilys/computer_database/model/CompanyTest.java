package com.excilys.computer_database.model;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import junit.framework.TestCase;

public class CompanyTest extends TestCase {

	public CompanyTest() {}

	@Test
	public void testEquals() {
		CompanyBuilder builderCompany1 = new CompanyBuilder();
		Company company1 = builderCompany1.build();

		// Both are same ref
		assertEquals(company1, company1);
		// Compare with null
		assertNotEquals(company1, null);
		// Compare with other class
		assertNotEquals(company1, new Object());
		
		// Compare with another
		CompanyBuilder builderCompany2 = new CompanyBuilder();
		Company company2;
		
		// Different ids
		company1 = builderCompany1.id(0).build();
		company2 = builderCompany2.id(1).build();
		assertNotEquals(company1, company2);
		// Set to same value
		builderCompany2.id(0);
		
		// Name null or not null
		company1 = builderCompany1.name(null).build();
		company2 = builderCompany2.name("no").build();
		assertNotEquals(company1, company2);
		
		company1 = builderCompany1.name("no").build();
		company2 = builderCompany2.name(null).build();
		assertNotEquals(company1, company2);
		
		// Different names
		company1 = builderCompany1.name("one").build();
		company2 = builderCompany2.name("two").build();
		assertNotEquals(company1, company2);
		
		// Equal objects
		company1 = builderCompany1.name("same").build();
		company2 = builderCompany2.name("same").build();
		assertEquals(company1, company2);
	}

}
