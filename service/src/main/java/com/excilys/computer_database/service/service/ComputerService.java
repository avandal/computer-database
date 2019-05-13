package com.excilys.computer_database.service.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.mapper.ComputerMapper;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Company;
import com.excilys.computer_database.core.model.Computer;
import com.excilys.computer_database.core.model.ComputerBuilder;
import com.excilys.computer_database.core.model.SortMode;
import com.excilys.computer_database.persistence.CompanyDAO;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.service.exception.ConcernedField;
import com.excilys.computer_database.service.service.exception.FailComputerException;
import com.excilys.computer_database.service.service.validator.ComputerValidator;

@Service
public class ComputerService {
	
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private CompanyDAO companyDAO;
	
	@Autowired
	private ComputerValidator validator;
	
	private Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	private ComputerService() {}
	
	public List<ComputerDTO> getAll(SortMode orderMode) {
		return computerDAO.getAll(orderMode.suffix()).stream().map(ComputerMapper::computerToDTO).collect(Collectors.toList());
	}
	
	public List<ComputerDTO> getByName(String name, SortMode orderMode) {
		return computerDAO.getByName(name, orderMode.suffix()).stream().map(ComputerMapper::computerToDTO).collect(Collectors.toList());
	}
	
	public Optional<ComputerDTO> getById(String id) {
		Optional<Integer> optId = Util.parseInt(id);
		if (optId.isEmpty()) {
			logger.error("getById - Wrong format of id");
			return Optional.empty();
		}
		Optional<Computer> computer = computerDAO.getById(optId.get());
		if (computer.isPresent()) {
			return Optional.of(ComputerMapper.computerToDTO(computer.get()));
		}
		
		return Optional.empty();
	}
	
	private void create(String name, Timestamp introduced, Timestamp discontinued, Company company) {
		computerDAO.create(new ComputerBuilder()
				.empty()
				.name(name)
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build()
		);
	}
	
	public void create(ComputerDTO computer) throws FailComputerException {		
		validator.checkName(computer);
		validator.checkDates(computer);
		validator.checkCompany(computer);
		
		String name = computer.getName();
		String introduced = computer.getIntroduced();
		String discontinued = computer.getIntroduced();
		String companyId = computer.getCompanyId();
		
		Timestamp tsIntroduced = Util.extract(Util.dateToTimestamp(introduced));
		Timestamp tsDiscontinued = Util.extract(Util.dateToTimestamp(discontinued));
		
		Optional<Company> company = companyDAO.getById(Integer.parseInt(companyId));
		
		if (Integer.parseInt(companyId) <= 0) {
			create(name, tsIntroduced, tsDiscontinued, null);
			return;
		}
		
		create(name, tsIntroduced, tsDiscontinued, company.get());
	}
	
	private void update(int id, String name, Timestamp introduced, Timestamp discontinued, Company company) {
		computerDAO.update(new ComputerBuilder()
				.empty()
				.id(id)
				.name(name)
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build()
		);
	}
	
	public void update(ComputerDTO computer) throws FailComputerException {
		computer.setCompanyId(Util.accordingTo(Objects::nonNull, computer.getCompanyId(), "0"));
		
		validator.checkId(computer);
		validator.checkName(computer);
		validator.checkDates(computer);
		validator.checkCompany(computer);
		
		String id = computer.getId();
		String name = computer.getName();
		String introduced = computer.getIntroduced();
		String discontinued = computer.getDiscontinued();
		String companyId = computer.getCompanyId();
		
		int intId = Util.parseInt(id).get();
		Timestamp tsIntroduced = Util.extract(Util.dateToTimestamp(introduced));
		Timestamp tsDiscontinued = Util.extract(Util.dateToTimestamp(discontinued));
		Optional<Company> company = companyDAO.getById(Integer.parseInt(companyId));
		
		if (Integer.parseInt(companyId) <= 0) {
			update(intId, name, tsIntroduced, tsDiscontinued, null);
			return;
		}
		
		update(intId, name, tsIntroduced, tsDiscontinued, company.get());
	}
	
	public int delete(String id) throws FailComputerException {
		Optional<Integer> optId = Util.parseInt(id);
		
		if (optId.isPresent()) {
			return computerDAO.deleteComputer(optId.get());
		}
		
		throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
	}
}
