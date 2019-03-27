//package com.excilys.computer_database.persistence;
//
//import java.io.FileInputStream;
//import java.util.List;
//
//import org.dbunit.DBTestCase;
//import org.dbunit.PropertiesBasedJdbcDatabaseTester;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
//import org.junit.Test;
//
//import com.excilys.computer_database.model.Company;
//
//public class CompanyDAOTest extends DBTestCase {
//	
//	private static final String ROOT_URL = "src/test/java/com/excilys/computer_database/";
//	
//	private CompanyDAO dao;
//	
//	public CompanyDAOTest() {
//		super();
//		
//		dao = CompanyDAO.getInstance();
//		
//		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DAO.DRIVER);
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, DAO.URL);
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, DAO.USER);
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, DAO.PASSWORD);
//        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "");
//	}
//
//	@Override
//	protected IDataSet getDataSet() throws Exception {
//		return new FlatXmlDataSetBuilder().build(new FileInputStream(ROOT_URL + "data.xml"));
//	}
//	
//	@Test
//    public void testGetAll() {
//		List<Company> companies = dao.companyList();
//		assertEquals(companies.size(), 3);
//    }
//}
