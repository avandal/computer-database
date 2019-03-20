package com.excilys.computer_database.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;

import junit.framework.TestCase;

public class UtilTest extends TestCase {

	public UtilTest() {}
	
	private void testIntWith(int value) {
		Optional<Integer> toTest = Util.parseInt(String.format("%d", value));
		
		assertTrue(toTest.isPresent());
		assertEquals(toTest.get(), Integer.valueOf(value));
	}
	
	@Test
	public void testParseInt() {
		assertFalse(Util.parseInt(null).isPresent());
		assertFalse(Util.parseInt("Any string").isPresent());
		assertFalse(Util.parseInt("1.25").isPresent());
		
		testIntWith(0);
		testIntWith(-1);
		testIntWith(42);
	}
	
	@Test
	public void testParseTimestamp() {
		assertFalse(Util.parseTimestamp(null).isPresent());
		assertFalse(Util.parseTimestamp("Any not-timestamp").isPresent());
		
		Optional<Timestamp> time = Util.parseTimestamp("1995-12-12 20:00:00");
		assertTrue(time.isPresent());
		
		LocalDateTime localDateTime = time.get().toLocalDateTime();
		LocalDate localDate = localDateTime.toLocalDate();
		
		assertEquals(localDate.getYear(), 1995);
		assertEquals(localDate.getMonthValue(), 12);
		assertEquals(localDate.getDayOfMonth(), 12);
		
		assertEquals(localDateTime.getHour(), 20);
		assertEquals(localDateTime.getMinute(), 0);
		assertEquals(localDateTime.getSecond(), 0);
	}

	@Test
	public void testBoxMessage() {
		assertNull(Util.boxMessage(null));
		
		String expected;
		
		expected = "\n" +
				" __\n" +
				"/  \\\n" +
				"|  |\n" +
				"\\__/\n";
		assertEquals(Util.boxMessage(""), expected);
		
		expected = "\n" +
				" __________________\n" +
				"/                  \\\n" +
				"| One line message |\n" +
				"\\__________________/\n";
		assertEquals(Util.boxMessage("One line message"), expected);
		
		expected = "\n" +
				" ____________\n" +
				"/            \\\n" +
				"| Multi line |\n" +
				"| messages   |\n" +
				"\\____________/\n";
		assertEquals(Util.boxMessage("Multi line\nmessages"), expected);
	}
}
