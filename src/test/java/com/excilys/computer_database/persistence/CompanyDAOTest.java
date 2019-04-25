package com.excilys.computer_database.persistence;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.computer_database.HibernateConfigTest;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.SortMode;
import com.excilys.computer_database.util.ScriptRunner;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
public class CompanyDAOTest extends TestCase {
	
	@Autowired
	private CompanyDAO companyDAO;
	
	@Autowired
	private ComputerDAO computerDAO;

	@Autowired
	private AnnotationConfigApplicationContext context;
	
	@Before
	public void setUp() {
//		context = new AnnotationConfigApplicationContext(HibernateConfigTest.class);
		
//		companyDAO = context.getBean(CompanyDAO.class);
//		computerDAO = context.getBean(ComputerDAO.class);
		
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
		
		List<Company> toTest = companyDAO.companyList();
		
		assertEquals(toTest, reference);
	}
	
	@Test
	public void testGetCompanyById() {
		Optional<Company> toTest = companyDAO.getCompanyById(1);
		Company reference = new Company(1, "Apple Inc.");
		
		assertTrue(toTest.isPresent());
		assertEquals(reference, toTest.get());
		
		toTest = companyDAO.getCompanyById(42);
		assertFalse(toTest.isPresent());
	}
	
	@Test
	public void testDeleteCompany() {
		Company none = new Company(0, null);
		Company thinking = new Company(2, "Thinking Machines");
		Company rca = new Company(3, "RCA");
		Company netronics = new Company(4, "Netronics");
		
		List<Company> companyReference = Arrays.asList(
				new Company[] {
						new Company(2, "Thinking Machines"),
						new Company(3, "RCA"),
						new Company(4, "Netronics")
				}
			);
		
		List<Computer> computerReference = Arrays.asList(
				new Computer[] {
					new Computer(2, "CM-2a", null, null, thinking),
					new Computer(5, "CM-5", Timestamp.valueOf("1991-01-01 00:00:00"), null, thinking),
					new Computer(7, "Apple IIe", null, null, none),
					new Computer(18, "COSMAC ELF", null, null, rca),
					new Computer(19, "COSMAC VIP", Timestamp.valueOf("1977-01-01 00:00:00"), null, rca),
					new Computer(20, "ELF II", Timestamp.valueOf("1977-01-01 00:00:00"), null, netronics)
				}
			);
		
		assertEquals(companyDAO.deleteCompany(1), 3);
		assertEquals(companyReference, companyDAO.companyList());
		assertEquals(computerReference, computerDAO.computerList(SortMode.DEFAULT.suffix()));
		
		// Wrong id company
		assertEquals(companyDAO.deleteCompany(42), -1);
		
		// No computer has been deleted
		computerDAO.deleteComputer(2);
		computerDAO.deleteComputer(5);
		assertEquals(companyDAO.deleteCompany(2), 0);
	}
}
