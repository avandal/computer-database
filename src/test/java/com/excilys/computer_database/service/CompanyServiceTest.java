package com.excilys.computer_database.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computer_database.AppConfigTest;
import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.servlets.SortMode;
import com.excilys.computer_database.util.ScriptRunner;

import junit.framework.TestCase;

public class CompanyServiceTest extends TestCase {
	private CompanyService companyService;
	private ComputerService computerService;
	
	private AnnotationConfigApplicationContext context;
	
	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		
		companyService = context.getBean(CompanyService.class);
		computerService = context.getBean(ComputerService.class);
		
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
		List<CompanyDTO> reference = Arrays.asList(
				new CompanyDTO[] {
						new CompanyDTO(1, "Apple Inc."),
						new CompanyDTO(2, "Thinking Machines"),
						new CompanyDTO(3, "RCA"),
						new CompanyDTO(4, "Netronics")
				}
			);
		
		List<CompanyDTO> toTest = companyService.getAll();
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testGetById() {
		CompanyDTO reference = new CompanyDTO(1, "Apple Inc.");
		Optional<CompanyDTO> toTest = companyService.getById(1);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
		
		toTest = companyService.getById(42);
		assertFalse(toTest.isPresent());
	}
	
	@Test
	public void testDeleteCompany() {
		String noneId = "0";
		String noneName = null;
		
		String thinkingId = "2";
		String thinkingName = "Thinking Machines";
		
		String rcaId = "3";
		String rcaName = "RCA";
		
		String netronicsId = "4";
		String netronicsName = "Netronics";
		
		List<CompanyDTO> companyReference = Arrays.asList(
				new CompanyDTO[] {
						new CompanyDTO(2, "Thinking Machines"),
						new CompanyDTO(3, "RCA"),
						new CompanyDTO(4, "Netronics")
				}
			);
		
		List<ComputerDTO> computerReference = Arrays.asList(
				new ComputerDTO[] {
					new ComputerDTO("2", "CM-2a", null, null, thinkingId, thinkingName),
					new ComputerDTO("5", "CM-5", "01/01/1991", null, thinkingId, thinkingName),
					new ComputerDTO("7", "Apple IIe", null, null, noneId, noneName),
					new ComputerDTO("18", "COSMAC ELF", null, null, rcaId, rcaName),
					new ComputerDTO("19", "COSMAC VIP", "01/01/1977", null, rcaId, rcaName),
					new ComputerDTO("20", "ELF II", "01/01/1977", null, netronicsId, netronicsName)
				}
			);
		
		assertEquals(companyService.delete("1"), 3);
		assertEquals(companyReference, companyService.getAll());
		assertEquals(computerReference, computerService.getAll(SortMode.DEFAULT));
		
		// Wrong id company
		assertEquals(companyService.delete("42"), -1);
		
		// No computer has been deleted
		computerService.deleteComputer(2);
		computerService.deleteComputer(5);
		assertEquals(companyService.delete("2"), 0);
	}
}
