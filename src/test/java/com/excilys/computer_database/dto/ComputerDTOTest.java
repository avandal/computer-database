package com.excilys.computer_database.dto;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ComputerDTOTest extends TestCase {
	private ComputerDTOBuilder builderComputerDTO1 = new ComputerDTOBuilder();
	private ComputerDTOBuilder builderComputerDTO2 = new ComputerDTOBuilder();
	
	private ComputerDTO computerDTO1;
	private ComputerDTO computerDTO2;
	
	@Before
	public void setUp() {
		computerDTO1 = builderComputerDTO1.empty().build();
		computerDTO2 = builderComputerDTO2.empty().build();
	}
	
	@Test
	public void testNullEquals() {
		// Both are same ref
		assertEquals(computerDTO1, computerDTO1);
		// Compare with null
		assertNotEquals(computerDTO1, null);
		// Compare with other class
		assertNotEquals(computerDTO1, new Object());
	}
	
	@Test
	public void testIdEquals() {
		computerDTO1 = builderComputerDTO1.id("2").build();
		computerDTO2 = builderComputerDTO2.id("3").build();
		
		// Different ids
		computerDTO1 = builderComputerDTO1.id("2").build();
		computerDTO2 = builderComputerDTO2.id("3").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		// Same value
		computerDTO1 = builderComputerDTO1.id("1").build();
		computerDTO2 = builderComputerDTO2.id("1").build();
		assertEquals(computerDTO1, computerDTO2);
	}
	
	@Test
	public void testNameEquals() {
		// null or not null
		computerDTO1 = builderComputerDTO1.name(null).build();
		computerDTO2 = builderComputerDTO2.name("no").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.name("no").build();
		computerDTO2 = builderComputerDTO2.name(null).build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.name(null).build();
		computerDTO2 = builderComputerDTO2.name(null).build();
		assertEquals(computerDTO1, computerDTO2);
		
		// Different
		computerDTO1 = builderComputerDTO1.name("one").build();
		computerDTO2 = builderComputerDTO2.name("two").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		// Same value
		computerDTO1 = builderComputerDTO1.name("same").build();
		computerDTO2 = builderComputerDTO2.name("same").build();
		assertEquals(computerDTO1, computerDTO2);
	}
	
	@Test
	public void testIntroducedEquals() {
		// null or not null
		computerDTO1 = builderComputerDTO1.introduced(null).build();
		computerDTO2 = builderComputerDTO2.introduced("no").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.introduced("no").build();
		computerDTO2 = builderComputerDTO2.introduced(null).build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.introduced(null).build();
		computerDTO2 = builderComputerDTO2.introduced(null).build();
		assertEquals(computerDTO1, computerDTO2);
		
		// Different
		computerDTO1 = builderComputerDTO1.introduced("one").build();
		computerDTO2 = builderComputerDTO2.introduced("two").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		// Same value
		computerDTO1 = builderComputerDTO1.introduced("same").build();
		computerDTO2 = builderComputerDTO2.introduced("same").build();
		assertEquals(computerDTO1, computerDTO2);
	}
	
	@Test
	public void testDiscontinuedEquals() {
		// null or not null
		computerDTO1 = builderComputerDTO1.discontinued(null).build();
		computerDTO2 = builderComputerDTO2.discontinued("no").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.discontinued("no").build();
		computerDTO2 = builderComputerDTO2.discontinued(null).build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.discontinued(null).build();
		computerDTO2 = builderComputerDTO2.discontinued(null).build();
		assertEquals(computerDTO1, computerDTO2);
		
		// Different
		computerDTO1 = builderComputerDTO1.discontinued("one").build();
		computerDTO2 = builderComputerDTO2.discontinued("two").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		// Same value
		computerDTO1 = builderComputerDTO1.discontinued("same").build();
		computerDTO2 = builderComputerDTO2.discontinued("same").build();
		assertEquals(computerDTO1, computerDTO2);
	}
	

	
	@Test
	public void testCompanyIdEquals() {
		// null or not null
		computerDTO1 = builderComputerDTO1.companyId(null).build();
		computerDTO2 = builderComputerDTO2.companyId("no").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.companyId("no").build();
		computerDTO2 = builderComputerDTO2.companyId(null).build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.companyId(null).build();
		computerDTO2 = builderComputerDTO2.companyId(null).build();
		assertEquals(computerDTO1, computerDTO2);
		
		// Different
		computerDTO1 = builderComputerDTO1.companyId("one").build();
		computerDTO2 = builderComputerDTO2.companyId("two").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		// Same value
		computerDTO1 = builderComputerDTO1.companyId("same").build();
		computerDTO2 = builderComputerDTO2.companyId("same").build();
		assertEquals(computerDTO1, computerDTO2);
	}
	

	
	@Test
	public void testCompanyNameEquals() {
		// null or not null
		computerDTO1 = builderComputerDTO1.companyName(null).build();
		computerDTO2 = builderComputerDTO2.companyName("no").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.companyName("no").build();
		computerDTO2 = builderComputerDTO2.companyName(null).build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		computerDTO1 = builderComputerDTO1.companyName(null).build();
		computerDTO2 = builderComputerDTO2.companyName(null).build();
		assertEquals(computerDTO1, computerDTO2);
		
		// Different
		computerDTO1 = builderComputerDTO1.companyName("one").build();
		computerDTO2 = builderComputerDTO2.companyName("two").build();
		assertNotEquals(computerDTO1, computerDTO2);
		
		// Same value
		computerDTO1 = builderComputerDTO1.companyName("same").build();
		computerDTO2 = builderComputerDTO2.companyName("same").build();
		assertEquals(computerDTO1, computerDTO2);
	}
	
	@Test
	public void testGlobalEquals() {
		testNullEquals();
		testIdEquals();
		testNameEquals();
		testIntroducedEquals();
		testDiscontinuedEquals();
		testCompanyIdEquals();
		testCompanyNameEquals();
		
		assertEquals(computerDTO1, computerDTO2);
	}
}
