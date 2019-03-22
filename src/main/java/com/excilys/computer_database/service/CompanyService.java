package com.excilys.computer_database.service;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.mapper.CompanyMapper;
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
	
	public List<CompanyDTO> getAll() {
		return dao.companyList().stream().map(c -> CompanyMapper.companyToDTO(c)).collect(Collectors.toList());
	}
}
