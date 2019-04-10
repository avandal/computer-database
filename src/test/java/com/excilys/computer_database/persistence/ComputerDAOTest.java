package com.excilys.computer_database.persistence;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.excilys.computer_database.App;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.util.ScriptRunner;

import junit.framework.TestCase;

public class ComputerDAOTest extends TestCase {
	private ComputerDAO dao = null;
	
	@Before
	public void setUp() {
		this.dao = ComputerDAO.getInstance(App.DATASOURCE_TEST);
		
		try {
			ScriptRunner.run();
		} catch (IOException e) {
			System.out.println("Error finding file");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testComputerList() {
		Company none = new Company(0, null);
		Company apple = new Company(1, "Apple Inc.");
		Company thinking = new Company(2, "Thinking Machines");
		Company rca = new Company(3, "RCA");
		Company netronics = new Company(4, "Netronics");
		
		List<Computer> reference = Arrays.asList(
				new Computer[] {
					new Computer(1, "MacBook Pro 15.4 inch", null, null, apple),
					new Computer(2, "CM-2a", null, null, thinking),
					new Computer(5, "CM-5", Timestamp.valueOf("1991-01-01 00:00:00"), null, thinking),
					new Computer(6, "MacBook Pro", Timestamp.valueOf("2006-01-10 00:00:00"), null, apple),
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(12, "Apple III", Timestamp.valueOf("1980-05-01 01:00:00"), Timestamp.valueOf("1984-04-01 01:00:00"), apple),
					new Computer(18, "COSMAC ELF", null, null, rca),
					new Computer(19, "COSMAC VIP", Timestamp.valueOf("1977-01-01 00:00:00"), null, rca),
					new Computer(20, "ELF II", Timestamp.valueOf("1977-01-01 00:00:00"), null, netronics)
				}
			);
		
		List<Computer> toTest = dao.computerList("");
		
		assertEquals(reference, toTest);
	}
}
