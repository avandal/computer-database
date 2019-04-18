package com.excilys.computer_database.persistence;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computer_database.AppConfigTest;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.SortMode;
import com.excilys.computer_database.util.ScriptRunner;

import junit.framework.TestCase;

public class ComputerDAOTest extends TestCase {
	private ComputerDAO dao;
	
	private Company none = new Company(0, null);
	private Company apple = new Company(1, "Apple Inc.");
	private Company thinking = new Company(2, "Thinking Machines");
	private Company rca = new Company(3, "RCA");
	private Company netronics = new Company(4, "Netronics");
	
	private AnnotationConfigApplicationContext context;
	
	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		
		this.dao = context.getBean(ComputerDAO.class);
		
		try {
			new ScriptRunner().run();
		} catch (IOException e) {
			System.out.println("Error finding file");
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		context.close();
	}
	
	@Test
	public void testComputerListDefault() {
		List<Computer> reference = Arrays.asList(
				new Computer[] {
					new Computer(1, "MacBook Pro 15.4 inch", null, null, apple),
					new Computer(2, "CM-2a", null, null, thinking),
					new Computer(5, "CM-5", Timestamp.valueOf("1991-01-01 00:00:00"), null, thinking),
					new Computer(6, "MacBook Pro", Timestamp.valueOf("2006-01-10 00:00:00"), null, apple),
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(12, "Apple III", Timestamp.valueOf("1980-05-01 00:00:00"), Timestamp.valueOf("1984-04-01 00:00:00"), apple),
					new Computer(18, "COSMAC ELF", null, null, rca),
					new Computer(19, "COSMAC VIP", Timestamp.valueOf("1977-01-01 00:00:00"), null, rca),
					new Computer(20, "ELF II", Timestamp.valueOf("1977-01-01 00:00:00"), null, netronics)
				}
			);
		
		List<Computer> toTest = dao.computerList(SortMode.DEFAULT.suffix());
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testComputerListNameAsc() {
		List<Computer> reference = Arrays.asList(
				new Computer[] {
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(12, "Apple III", Timestamp.valueOf("1980-05-01 00:00:00"), Timestamp.valueOf("1984-04-01 00:00:00"), apple),
					new Computer(2, "CM-2a", null, null, thinking),
					new Computer(5, "CM-5", Timestamp.valueOf("1991-01-01 00:00:00"), null, thinking),
					new Computer(18, "COSMAC ELF", null, null, rca),
					new Computer(19, "COSMAC VIP", Timestamp.valueOf("1977-01-01 00:00:00"), null, rca),
					new Computer(20, "ELF II", Timestamp.valueOf("1977-01-01 00:00:00"), null, netronics),
					new Computer(6, "MacBook Pro", Timestamp.valueOf("2006-01-10 00:00:00"), null, apple),
					new Computer(1, "MacBook Pro 15.4 inch", null, null, apple)
				}
			);
		
		List<Computer> toTest = dao.computerList(SortMode.NAME_ASC.suffix());
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testGetComputerDetails() {
		Computer reference = new Computer(1, "MacBook Pro 15.4 inch", null, null, apple);
		Optional<Computer> toTest = dao.getComputerDetails(1);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
		
		toTest = dao.getComputerDetails(42);
		assertFalse(toTest.isPresent());
	}
	
	@Test
	public void testGetByNameDefault() {
		List<Computer> reference = Arrays.asList(
				new Computer[] {
					new Computer(1, "MacBook Pro 15.4 inch", null, null, apple),
					new Computer(6, "MacBook Pro", Timestamp.valueOf("2006-01-10 00:00:00"), null, apple),
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(12, "Apple III", Timestamp.valueOf("1980-05-01 00:00:00"), Timestamp.valueOf("1984-04-01 00:00:00"), apple)
				}
			);
		
		List<Computer> toTest = dao.getByName("Apple", SortMode.DEFAULT.suffix());
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testGetByNameIntroNameAsc() {
		List<Computer> reference = Arrays.asList(
				new Computer[] {
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(12, "Apple III", Timestamp.valueOf("1980-05-01 00:00:00"), Timestamp.valueOf("1984-04-01 00:00:00"), apple),
					new Computer(6, "MacBook Pro", Timestamp.valueOf("2006-01-10 00:00:00"), null, apple),
					new Computer(1, "MacBook Pro 15.4 inch", null, null, apple)
				}
			);
		List<Computer> toTest = dao.getByName("Apple", SortMode.NAME_ASC.suffix());
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testCreateComputer() {
		// Valid
		String name = "Test create";
		Timestamp introduced = Timestamp.valueOf("1995-12-18 00:00:00");
		Timestamp discontinued = Timestamp.valueOf("1996-12-18 00:00:00");
		Integer companyId = 1;
		
		List<Computer> reference = Arrays.asList(
				new Computer[] {
					new Computer(1, "MacBook Pro 15.4 inch", null, null, apple),
					new Computer(2, "CM-2a", null, null, thinking),
					new Computer(5, "CM-5", Timestamp.valueOf("1991-01-01 00:00:00"), null, thinking),
					new Computer(6, "MacBook Pro", Timestamp.valueOf("2006-01-10 00:00:00"), null, apple),
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(12, "Apple III", Timestamp.valueOf("1980-05-01 00:00:00"), Timestamp.valueOf("1984-04-01 00:00:00"), apple),
					new Computer(18, "COSMAC ELF", null, null, rca),
					new Computer(19, "COSMAC VIP", Timestamp.valueOf("1977-01-01 00:00:00"), null, rca),
					new Computer(20, "ELF II", Timestamp.valueOf("1977-01-01 00:00:00"), null, netronics),
					new Computer(21, name, introduced, discontinued, apple)
				}
			);
		
		assertEquals(dao.createComputer(name, introduced, discontinued, companyId), 1);
		assertEquals(dao.computerList(SortMode.DEFAULT.suffix()), reference);
		
		Optional<Computer> toTest = dao.getComputerDetails(21);
		assertTrue(toTest.isPresent());
		assertEquals(reference.get(reference.size() - 1), toTest.get());
		
		// Company does not exist
		assertEquals(dao.createComputer(name, introduced, discontinued, 42), 0);
		
		// Some null values
		assertEquals(dao.createComputer(name, introduced, discontinued, null), 1);
		assertEquals(dao.createComputer(name, introduced, null, companyId), 1);
		assertEquals(dao.createComputer(name, null, discontinued, companyId), 1);
		assertEquals(dao.createComputer(null, introduced, discontinued, companyId), 1);
	}
	
	@Test
	public void testUpdateComputer() {
		// Valid
		String name = "Update create";
		Timestamp introduced = Timestamp.valueOf("1995-12-18 00:00:00");
		Timestamp discontinued = Timestamp.valueOf("1996-12-18 00:00:00");
		Integer companyId = 1;
		
		Computer reference = new Computer(1, name, introduced, discontinued, apple);
		
		assertEquals(dao.updateComputer(1, name, introduced, discontinued, companyId), 1);
		
		Optional<Computer> toTest = dao.getComputerDetails(1);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
		
		// Company does not exist
		assertEquals(dao.updateComputer(1, name, introduced, discontinued, 42), 0);
		
		// Some null values
		assertEquals(dao.updateComputer(1, name, introduced, discontinued, null), 1);
		assertEquals(dao.updateComputer(1, name, introduced, null, companyId), 1);
		assertEquals(dao.updateComputer(1, name, null, discontinued, companyId), 1);
		assertEquals(dao.updateComputer(1, null, introduced, discontinued, companyId), 1);
		
		// Invalid id computer
		assertEquals(dao.updateComputer(42, name, introduced, discontinued, companyId), 0);
	}
	
	@Test
	public void testDeleteComputer() {
		assertEquals(dao.deleteComputer(1), 1);
		
		List<Computer> reference = Arrays.asList(
				new Computer[] {
					new Computer(2, "CM-2a", null, null, thinking),
					new Computer(5, "CM-5", Timestamp.valueOf("1991-01-01 00:00:00"), null, thinking),
					new Computer(6, "MacBook Pro", Timestamp.valueOf("2006-01-10 00:00:00"), null, apple),
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(12, "Apple III", Timestamp.valueOf("1980-05-01 00:00:00"), Timestamp.valueOf("1984-04-01 00:00:00"), apple),
					new Computer(18, "COSMAC ELF", null, null, rca),
					new Computer(19, "COSMAC VIP", Timestamp.valueOf("1977-01-01 00:00:00"), null, rca),
					new Computer(20, "ELF II", Timestamp.valueOf("1977-01-01 00:00:00"), null, netronics)
				}
			);
		
		assertEquals(dao.computerList(SortMode.DEFAULT.suffix()), reference);
		
		// Invalid id computer
		assertEquals(dao.deleteComputer(42), 0);
	}
}
