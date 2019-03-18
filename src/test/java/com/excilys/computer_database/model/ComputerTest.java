package com.excilys.computer_database.model;

import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {
	
	private ComputerBuilder builderComputer1 = new ComputerBuilder();
	private ComputerBuilder builderComputer2 = new ComputerBuilder();
	
	private Computer computer1;
	private Computer computer2;

	public ComputerTest() {}
	
	@Before
	public void setUp() {
		computer1 = builderComputer1.empty().build();
		computer2 = builderComputer2.empty().build();
		System.out.println("test");
	}
	
	@Test
	public void testNullEquals() {
		// Both are same ref
		assertEquals(computer1, computer1);
		// Compare with null
		assertNotEquals(computer1, null);
		// Compare with other class
		assertNotEquals(computer1, new Object());
	}
	
	@Test
	public void testIdEquals() {
		computer1 = builderComputer1.id(2).build();
		computer2 = builderComputer2.id(3).build();
		
		// Different ids
		computer1 = builderComputer1.id(2).build();
		computer2 = builderComputer2.id(3).build();
		assertNotEquals(computer1, computer2);
		
		// Same value
		computer1 = builderComputer1.id(1).build();
		computer2 = builderComputer2.id(1).build();
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void testNameEquals() {
		// null or not null
		computer1 = builderComputer1.name(null).build();
		computer2 = builderComputer2.name("no").build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.name("no").build();
		computer2 = builderComputer2.name(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.name(null).build();
		computer2 = builderComputer2.name(null).build();
		assertEquals(computer1, computer2);
		
		// Different
		computer1 = builderComputer1.name("one").build();
		computer2 = builderComputer2.name("two").build();
		assertNotEquals(computer1, computer2);
		
		// Same value
		computer1 = builderComputer1.name("same").build();
		computer2 = builderComputer2.name("same").build();
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void testIntroducedEquals() {
		// null or not null
		Timestamp time1 = Timestamp.valueOf("1995-12-18 20:00:00");
		computer1 = builderComputer1.introduced(null).build();
		computer2 = builderComputer2.introduced(time1).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.introduced(time1).build();
		computer2 = builderComputer2.introduced(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.introduced(null).build();
		computer2 = builderComputer2.introduced(null).build();
		assertEquals(computer1, computer2);
		
		// Different
		Timestamp time2 = Timestamp.valueOf("2000-01-01 00:00:00");
		computer1 = builderComputer1.introduced(time1).build();
		computer2 = builderComputer2.introduced(time2).build();
		assertNotEquals(computer1, computer2);
		
		// Same value
		time2 = Timestamp.valueOf("1995-12-18 20:00:00");
		computer2 = builderComputer2.introduced(time2).build();
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void testDiscontinuedEquals() {
		// null or not null
		Timestamp time1 = Timestamp.valueOf("1995-12-18 20:00:00");
		computer1 = builderComputer1.discontinued(null).build();
		computer2 = builderComputer2.discontinued(time1).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.discontinued(time1).build();
		computer2 = builderComputer2.discontinued(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.discontinued(null).build();
		computer2 = builderComputer2.discontinued(null).build();
		assertEquals(computer1, computer2);
		
		// Different
		Timestamp time2 = Timestamp.valueOf("2000-01-01 00:00:00");
		computer1 = builderComputer1.discontinued(time1).build();
		computer2 = builderComputer2.discontinued(time2).build();
		assertNotEquals(computer1, computer2);
		
		// Same value
		time2 = Timestamp.valueOf("1995-12-18 20:00:00");
		computer2 = builderComputer2.discontinued(time2).build();
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void testCompanyEquals() {
		// null or not null
		Company coompany1 = new Company(1, "toto");
		computer1 = builderComputer1.company(coompany1).build();
		computer2 = builderComputer2.company(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.company(null).build();
		computer2 = builderComputer2.company(coompany1).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = builderComputer1.company(null).build();
		computer2 = builderComputer2.company(null).build();
		assertEquals(computer1, computer2);
		
		// Different
		Company company2 = new Company(2, "titi");
		computer1 = builderComputer1.company(coompany1).build();
		computer2 = builderComputer2.company(company2).build();
		assertNotEquals(computer1, computer2);
		
		// Same value
		computer1 = builderComputer1.company(new Company(0, "Same")).build();
		computer2 = builderComputer2.company(new Company(0, "Same")).build();
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void testGlobalEquals() {
		testNullEquals();
		testIdEquals();
		testNameEquals();
		testIntroducedEquals();
		testDiscontinuedEquals();
		testCompanyEquals();
		
		assertEquals(computer1, computer2);
	}

}
