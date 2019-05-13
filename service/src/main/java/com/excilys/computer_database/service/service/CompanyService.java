package com.excilys.computer_database.service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.binding.mapper.CompanyMapper;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Company;
import com.excilys.computer_database.persistence.CompanyDAO;
import com.excilys.computer_database.service.service.exception.ConcernedField;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Service
public class CompanyService {
	private Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyDAO dao;
	
	private CompanyService() {}
	
	public List<CompanyDTO> getAll() {
		return dao.getAll().stream().map(CompanyMapper::companyToDTO).collect(Collectors.toList());
	}
	
	public Optional<CompanyDTO> getById(String id) {
		Optional<Integer> companyId = Util.parseInt(id);
		if (companyId.isEmpty()) {
			logger.error("getById - Wrong format of id");
			return Optional.empty();
		}
		
		Optional<Company> company = dao.getById(companyId.get());
		
		if (company.isPresent()) {
			return Optional.of(CompanyMapper.companyToDTO(company.get()));
		}
		return Optional.empty();
	}
	
	public int delete(String id) throws FailComputerException {
		if ("".equals(id)) {
			return dao.delete(null);
		}
		
		Optional<Integer> optId = Util.parseInt(id);
		if (optId.isPresent()) {
			return dao.delete(optId.get());
		}
		
		throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
	}
}
