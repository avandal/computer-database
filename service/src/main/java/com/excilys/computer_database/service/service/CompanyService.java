package com.excilys.computer_database.service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.binding.mapper.CompanyMapper;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Company;
import com.excilys.computer_database.persistence.CompanyDAO;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyDAO dao;
	
	private CompanyService() {}
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
	
	public int delete(String id) {
		if ("".equals(id)) {
			return dao.deleteCompany(null);
		}
		
		Optional<Integer> optId = Util.parseInt(id);
		if (optId.isPresent()) {
			return dao.deleteCompany(optId.get());
		}
		
		return -1;
	}
}