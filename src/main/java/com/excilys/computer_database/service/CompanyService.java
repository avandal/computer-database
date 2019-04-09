package com.excilys.computer_database.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.persistence.CompanyDAO;

public class CompanyService {
	private CompanyDAO dao;
	
	private static volatile CompanyService instance;
	
	private CompanyService(String datasource) {
		this.dao = CompanyDAO.getInstance(datasource);
	}
	
	public static CompanyService getInstance(String datasource) {
		if (instance == null) {
			synchronized(CompanyService.class) {
				if (instance == null) {
					instance = new CompanyService(datasource);
				}
			}
		}
		return instance;
	}
	
	public List<CompanyDTO> getAll() {
		return dao.companyList().stream().map(c -> CompanyMapper.companyToDTO(c)).collect(Collectors.toList());
	}
	
	public Optional<CompanyDTO> getById(int id) {
		Optional<Company> company = dao.getCompanyById(id);
		
		if (company.isPresent()) {
			return Optional.of(CompanyMapper.companyToDTO(company.get()));
		}
		return Optional.empty();
	}
}
