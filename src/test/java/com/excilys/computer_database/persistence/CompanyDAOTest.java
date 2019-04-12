package com.excilys.computer_database.persistence;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computer_database.AppConfigTest;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.util.ScriptRunner;

import junit.framework.TestCase;

public class CompanyDAOTest extends TestCase {
	
	private CompanyDAO dao = null;

	private AnnotationConfigApplicationContext context;
	
	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		
		dao = context.getBean(CompanyDAO.class);
		
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
	public void testCompanyList() {
		List<Company> reference = Arrays.asList(
				new Company[] {
						new Company(1, "Apple Inc."),
						new Company(2, "Thinking Machines"),
						new Company(3, "RCA"),
						new Company(4, "Netronics")
				}
			);
		
		List<Company> toTest = dao.companyList();
		
		
		assertEquals(toTest, reference);
	}
	
	@Test
	public void testGetCompanyById() {
		Optional<Company> toTest = dao.getCompanyById(1);
		Company reference = new Company(1, "Apple Inc.");
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
		
		toTest = dao.getCompanyById(42);
		assertFalse(toTest.isPresent());
	}
}
