package com.excilys.computer_database.service;

import java.util.List;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.persistence.CompanyDAO;

public class CompanyService extends Service {
	private CompanyDAO dao;
	
	private static volatile CompanyService instance;
	
	private CompanyService() {
		this.dao = CompanyDAO.getInstance(DRIVER, URL, USER, PASSWORD);
	}
	
	public static CompanyService getInstance() {
		if (instance == null) {
			synchronized(CompanyService.class) {
				if (instance == null) {
					instance = new CompanyService();
				}
			}
		}
		return instance;
	}
	
	public List<Company> getAll() {
		return dao.companyList();
	}
}
