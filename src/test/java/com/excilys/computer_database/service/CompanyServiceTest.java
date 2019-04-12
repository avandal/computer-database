package com.excilys.computer_database.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computer_database.AppConfigTest;
import com.excilys.computer_database.dto.CompanyDTO;

import junit.framework.TestCase;

public class CompanyServiceTest extends TestCase {
	private CompanyService service;
	
	private AnnotationConfigApplicationContext context;
	
	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		
		service = context.getBean(CompanyService.class);
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
		
		List<CompanyDTO> toTest = service.getAll();
		
		assertEquals(reference, toTest);
	}
	
	@Test
	public void testGetById() {
		CompanyDTO reference = new CompanyDTO(1, "Apple Inc.");
		Optional<CompanyDTO> toTest = service.getById(1);
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
		
		toTest = service.getById(42);
		assertFalse(toTest.isPresent());
	}
}
