package com.excilys.computer_database.model;

import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.Test;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {

	public ComputerTest() {}
	
	@Test
	public void testEquals() {
		ComputerBuilder cb1 = new ComputerBuilder();
		Computer c1 = cb1.build();
		
		// Both are same ref
		assertEquals(c1, c1);
		// Compare with null
		assertNotEquals(c1, null);
		// Compare with other class
		assertNotEquals(c1, new Object());
		
		// Compare with another
		ComputerBuilder cb2 = new ComputerBuilder();
		Computer c2;
		
		// Different ids
		c1 = cb1.id(2).build();
		c2 = cb2.id(3).build();
		assertNotEquals(c1, c2);
		
		// Same value
		c1 = cb1.id(1).build();
		c2 = cb2.id(1).build();
		assertEquals(c1, c2);
		
		// Name
		
		// null or not null
		c1 = cb1.name(null).build();
		c2 = cb2.name("no").build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.name("no").build();
		c2 = cb2.name(null).build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.name(null).build();
		c2 = cb2.name(null).build();
		assertEquals(c1, c2);
		
		// Different
		c1 = cb1.name("one").build();
		c2 = cb2.name("two").build();
		assertNotEquals(c1, c2);
		
		// Same value
		c1 = cb1.name("same").build();
		c2 = cb2.name("same").build();
		assertEquals(c1, c2);
		
		// Introduced
		
		// null or not null
		Timestamp tim1 = Timestamp.valueOf("1995-12-18 20:00:00");
		c1 = cb1.introduced(null).build();
		c2 = cb2.introduced(tim1).build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.introduced(tim1).build();
		c2 = cb2.introduced(null).build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.introduced(null).build();
		c2 = cb2.introduced(null).build();
		assertEquals(c1, c2);
		
		// Different
		Timestamp tim2 = Timestamp.valueOf("2000-01-01 00:00:00");
		c1 = cb1.introduced(tim1).build();
		c2 = cb2.introduced(tim2).build();
		assertNotEquals(c1, c2);
		
		// Same value
		tim2 = Timestamp.valueOf("1995-12-18 20:00:00");
		c2 = cb2.introduced(tim2).build();
		assertEquals(c1, c2);
		
		// Discontinued
		
		// null or not null
		Timestamp tim3 = Timestamp.valueOf("1995-12-18 20:00:00");
		c1 = cb1.discontinued(null).build();
		c2 = cb2.discontinued(tim3).build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.discontinued(tim3).build();
		c2 = cb2.discontinued(null).build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.discontinued(null).build();
		c2 = cb2.discontinued(null).build();
		assertEquals(c1, c2);
		
		// Different
		Timestamp tim4 = Timestamp.valueOf("2000-01-01 00:00:00");
		c1 = cb1.discontinued(tim3).build();
		c2 = cb2.discontinued(tim4).build();
		assertNotEquals(c1, c2);
		
		// Same value
		tim4 = Timestamp.valueOf("1995-12-18 20:00:00");
		c2 = cb2.discontinued(tim4).build();
		assertEquals(c1, c2);
		
		// Company
		
		// null or not null
		Company cop1 = new Company(1, "toto");
		c1 = cb1.company(cop1).build();
		c2 = cb2.company(null).build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.company(null).build();
		c2 = cb2.company(cop1).build();
		assertNotEquals(c1, c2);
		
		c1 = cb1.company(null).build();
		c2 = cb2.company(null).build();
		assertEquals(c1, c2);
		
		// Different
		Company cop2 = new Company(2, "titi");
		c1 = cb1.company(cop1).build();
		c2 = cb2.company(cop2).build();
		assertNotEquals(c1, c2);
		
		// Same value
		c1 = cb1.company(new Company(0, "Same")).build();
		c2 = cb2.company(new Company(0, "Same")).build();
		assertEquals(c1, c2);
	}

}
