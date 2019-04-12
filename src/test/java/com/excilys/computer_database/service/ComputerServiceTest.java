package com.excilys.computer_database.service;

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
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.service.exception.FailComputerException;
import com.excilys.computer_database.servlets.SortMode;
import com.excilys.computer_database.util.ScriptRunner;

import junit.framework.TestCase;

public class ComputerServiceTest extends TestCase {
	private ComputerService service;
	
	private AnnotationConfigApplicationContext context;
	
	private String noneId = "0";
	private String noneName = null;
	
	private String appleId = "1";
	private String appleName = "Apple Inc.";
	
	private String thinkingId = "2";
	private String thinkingName = "Thinking Machines";
	
	private String rcaId = "3";
	private String rcaName = "RCA";
	
	private String netronicsId = "4";
	private String netronicsName = "Netronics";
	
	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		
		service = context.getBean(ComputerService.class);
		
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
	public void testGetAll() {
		List<ComputerDTO> reference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, appleId, appleName),
					new ComputerDTO("2", "CM-2a", null, null, thinkingId, thinkingName),
					new ComputerDTO("5", "CM-5", "01/01/1991", null, thinkingId, thinkingName),
					new ComputerDTO("6", "MacBook Pro", "10/01/2006", null, appleId, appleName),
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("12", "Apple III", "01/05/1980", "01/04/1984", appleId, appleName),
					new ComputerDTO("18", "COSMAC ELF", null, null, rcaId, rcaName),
					new ComputerDTO("19", "COSMAC VIP", "01/01/1977", null, rcaId, rcaName),
					new ComputerDTO("20", "ELF II", "01/01/1977", null, netronicsId, netronicsName)
				}
			);
		
		List<ComputerDTO> toTest = service.getAll(SortMode.DEFAULT);
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testGetAllNameAsc() {
		List<ComputerDTO> reference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("12", "Apple III", "01/05/1980", "01/04/1984", appleId, appleName),
					new ComputerDTO("2", "CM-2a", null, null, thinkingId, thinkingName),
					new ComputerDTO("5", "CM-5", "01/01/1991", null, thinkingId, thinkingName),
					new ComputerDTO("18", "COSMAC ELF", null, null, rcaId, rcaName),
					new ComputerDTO("19", "COSMAC VIP", "01/01/1977", null, rcaId, rcaName),
					new ComputerDTO("20", "ELF II", "01/01/1977", null, netronicsId, netronicsName),
					new ComputerDTO("6", "MacBook Pro", "10/01/2006", null, appleId, appleName),
					new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, appleId, appleName)
				}
			);
		
		List<ComputerDTO> toTest = service.getAll(SortMode.NAME_ASC);
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testSearchByNameDefault() {
		List<ComputerDTO> reference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, appleId, appleName),
					new ComputerDTO("6", "MacBook Pro", "10/01/2006", null, appleId, appleName),
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("12", "Apple III", "01/05/1980", "01/04/1984", appleId, appleName)
				}
			);
		
		List<ComputerDTO> toTest = service.searchByName("Apple", SortMode.DEFAULT);
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testSearchByNameNameAsc() {
		List<ComputerDTO> reference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("12", "Apple III", "01/05/1980", "01/04/1984", appleId, appleName),
					new ComputerDTO("6", "MacBook Pro", "10/01/2006", null, appleId, appleName),
					new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, appleId, appleName)
				}
			);
		
		List<ComputerDTO> toTest = service.searchByName("Apple", SortMode.NAME_ASC);
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testGetComputerDetails() {
		ComputerDTO reference = new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, appleId, appleName);
		Optional<ComputerDTO> toTest = service.getComputerDetails(1);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
		
		toTest = service.getComputerDetails(42);
		assertFalse(toTest.isPresent());
	}
	
	@Test
	public void testCreateValidComputer() {
		String name = "Test create";
		String introduced = "18/12/1995";
		String discontinued = "18/12/1996";
		String companyId = appleId;
		String companyName = appleName;
		
		List<ComputerDTO> reference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, appleId, appleName),
					new ComputerDTO("2", "CM-2a", null, null, thinkingId, thinkingName),
					new ComputerDTO("5", "CM-5", "01/01/1991", null, thinkingId, thinkingName),
					new ComputerDTO("6", "MacBook Pro", "10/01/2006", null, appleId, appleName),
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("12", "Apple III", "01/05/1980", "01/04/1984", appleId, appleName),
					new ComputerDTO("18", "COSMAC ELF", null, null, rcaId, rcaName),
					new ComputerDTO("19", "COSMAC VIP", "01/01/1977", null, rcaId, rcaName),
					new ComputerDTO("20", "ELF II", "01/01/1977", null, netronicsId, netronicsName),
					new ComputerDTO("21", name, introduced, discontinued, companyId, companyName)
				}
			);
		
		try {
			assertEquals(service.createComputer(name, introduced, discontinued, companyId), 1);
		} catch (FailComputerException e) {
			fail();
		}
		
		assertEquals(reference, service.getAll(SortMode.DEFAULT));
		
		Optional<ComputerDTO> toTest = service.getComputerDetails(21);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference.get(reference.size() - 1), toTest.get());
	}
	
	@Test
	public void testCreateComputerWithoutCompany() {
		String name = "Test create";
		String introduced = "18/12/1995";
		String discontinued = "18/12/1996";
		String companyId = "0";
		String companyName = null;
		
		List<ComputerDTO> reference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, appleId, appleName),
					new ComputerDTO("2", "CM-2a", null, null, thinkingId, thinkingName),
					new ComputerDTO("5", "CM-5", "01/01/1991", null, thinkingId, thinkingName),
					new ComputerDTO("6", "MacBook Pro", "10/01/2006", null, appleId, appleName),
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("12", "Apple III", "01/05/1980", "01/04/1984", appleId, appleName),
					new ComputerDTO("18", "COSMAC ELF", null, null, rcaId, rcaName),
					new ComputerDTO("19", "COSMAC VIP", "01/01/1977", null, rcaId, rcaName),
					new ComputerDTO("20", "ELF II", "01/01/1977", null, netronicsId, netronicsName),
					new ComputerDTO("21", name, introduced, discontinued, companyId, companyName)
				}
			);
		
		try {
			assertEquals(service.createComputer(name, introduced, discontinued, companyId), 1);
		} catch (FailComputerException e) {
			fail("Should have added the computer");
		}
		
		assertEquals(reference, service.getAll(SortMode.DEFAULT));
		
		Optional<ComputerDTO> toTest = service.getComputerDetails(21);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference.get(reference.size() - 1), toTest.get());
	}
	
	@Test
	public void testCreateComputerExceptions() {
		String name = "Fail";
		String introduced = "18/12/1995";
		String discontinued = "18/12/1996";
		String beforeDisc = "17/12/1995";
		String companyId = appleId;
		
		try {
			service.createComputer("", introduced, discontinued, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.NULL_NAME + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.createComputer(name, "", discontinued, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.DISC_WITHOUT_INTRO + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.createComputer(name, "wrong format", discontinued, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.WRONG_FORMAT + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.createComputer(name, introduced, "Wrong format", companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.WRONG_FORMAT + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.createComputer(name, introduced, beforeDisc, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.DISC_LESS_THAN_INTRO + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.createComputer(name, introduced, discontinued, "Wrong id");
			fail("Should have thrown this FailComputerException: '" + FailComputerException.INVALID_COMPANY_ID + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.createComputer(name, introduced, discontinued, "42");
			fail("Should have thrown this FailComputerException: '" + FailComputerException.NONEXISTENT_COMPANY + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testUpdateValidComputer() {
		String name = "Update create";
		String introduced = "18/12/1995";
		String discontinued = "18/12/1996";
		String companyId = thinkingId;
		String companyName = thinkingName;
		
		ComputerDTO reference = new ComputerDTO("1", name, introduced, discontinued, companyId, companyName);
		
		try {
			assertEquals(service.updateComputer(1, name, introduced, discontinued, companyId), 1);
		} catch (FailComputerException e) {
			fail("Should have updated the computer");
		}
		
		Optional<ComputerDTO> toTest = service.getComputerDetails(1);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
	}
	
	@Test
	public void testUpdateComputerWithoutCompany() {
		String name = "Update create";
		String introduced = "18/12/1995";
		String discontinued = "18/12/1996";
		String companyId = "0";
		String companyName = null;
		
		ComputerDTO reference = new ComputerDTO("1", name, introduced, discontinued, companyId, companyName);
		
		try {
			assertEquals(service.updateComputer(1, name, introduced, discontinued, companyId), 1);
		} catch (FailComputerException e) {
			fail("Should have updated the computer");
		}
		
		Optional<ComputerDTO> toTest = service.getComputerDetails(1);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
	}
	
	@Test
	public void testUpdateComputerExceptions() {
		String name = "Fail";
		String introduced = "18/12/1995";
		String discontinued = "18/12/1996";
		String beforeDisc = "17/12/1995";
		String companyId = thinkingId;
		
		try {
			service.updateComputer(1, "", introduced, discontinued, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.NULL_NAME + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.updateComputer(1, name, "", discontinued, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.DISC_WITHOUT_INTRO + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.updateComputer(1, name, "wrong format", discontinued, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.WRONG_FORMAT + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.updateComputer(1, name, introduced, "Wrong format", companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.WRONG_FORMAT + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.updateComputer(1, name, introduced, beforeDisc, companyId);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.DISC_LESS_THAN_INTRO + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.updateComputer(1, name, introduced, discontinued, "Wrong id");
			fail("Should have thrown this FailComputerException: '" + FailComputerException.INVALID_COMPANY_ID + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
		
		try {
			service.updateComputer(1, name, introduced, discontinued, "42");
			fail("Should have thrown this FailComputerException: '" + FailComputerException.NONEXISTENT_COMPANY + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testDeleteComputer() {
		try {
			assertEquals(service.deleteComputer("1"), 1);
		} catch (FailComputerException e) {
			fail("Should have deleted the computer");
		}
		
		List<ComputerDTO> reference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("2", "CM-2a", null, null, thinkingId, thinkingName),
					new ComputerDTO("5", "CM-5", "01/01/1991", null, thinkingId, thinkingName),
					new ComputerDTO("6", "MacBook Pro", "10/01/2006", null, appleId, appleName),
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("12", "Apple III", "01/05/1980", "01/04/1984", appleId, appleName),
					new ComputerDTO("18", "COSMAC ELF", null, null, rcaId, rcaName),
					new ComputerDTO("19", "COSMAC VIP", "01/01/1977", null, rcaId, rcaName),
					new ComputerDTO("20", "ELF II", "01/01/1977", null, netronicsId, netronicsName)
				}
			);
		
		assertEquals(service.getAll(SortMode.DEFAULT), reference);
		
		// Invalid id computer
		try {
			assertEquals(service.deleteComputer("Wrong id"), 0);
			fail("Should have thrown this FailComputerException: '" + FailComputerException.ID_ERROR + "'");
		} catch (FailComputerException e) {
			assertTrue(true);
		}
	}
}
