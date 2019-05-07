package com.excilys.computer_database.service.service;

import java.sql.Timestamp;
import java.util.List;
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

@Service
public class ComputerService {
	
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private CompanyDAO companyDAO;
	
	private Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	private ComputerService() {}
	
	public List<ComputerDTO> getAll(SortMode orderMode) {
		logger.debug("ComputerService - getAll : callings dao.computerList");
		return computerDAO.getAll(orderMode.suffix()).stream().map(c -> ComputerMapper.computerToDTO(c)).collect(Collectors.toList());
	}
	
	public List<ComputerDTO> getByName(String name, SortMode orderMode) {
		return computerDAO.getByName(name, orderMode.suffix()).stream().map(c -> ComputerMapper.computerToDTO(c)).collect(Collectors.toList());
	}
	
	public Optional<ComputerDTO> getById(int id) {
		Optional<Computer> computer = computerDAO.getById(id);
		if (computer.isPresent()) {
			return Optional.of(ComputerMapper.computerToDTO(computer.get()));
		}
		
		return Optional.empty();
	}
	
	private void checkId(ComputerDTO computer) throws FailComputerException {
		String id = computer.getId();
		
		Optional<Integer> optId = Util.parseInt(id);
		if (optId.isEmpty()) {
			logger.warn("checkId - Invalid id");
			throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
		}
		
		Optional<Computer> toChange = computerDAO.getById(optId.get());
		if (toChange.isEmpty()) {
			logger.warn("checkId - This computer does not exist");
			throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
		}
	}
	
	private void checkName(ComputerDTO computer) throws FailComputerException {
		if (computer.getName() == null || "".equals(computer.getName().trim())) {
			logger.warn("checkName - Empty name");
			throw new FailComputerException(ConcernedField.NAME, FailComputerException.NULL_NAME);
		}
	}
	
	private void checkDates(ComputerDTO computer) throws FailComputerException {
		String introduced = computer.getIntroduced();
		String discontinued = computer.getDiscontinued();
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if (introduced == null || "".equals(introduced)) {
			if (discontinued != null && !"".equals(discontinued)) {
				logger.warn("checkDates - Discontinued without introduced");
				throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_WITHOUT_INTRO);
			}
		} else {
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (optIntroduced.isEmpty()) {
				logger.warn("checkDates - Introduced: Wrong format");
				throw new FailComputerException(ConcernedField.INTRODUCED, FailComputerException.WRONG_FORMAT);
			}
			retIntroduced = optIntroduced.get();
			
			if (!"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (optDiscontinued.isEmpty()) {
					logger.warn("checkDates - Discontinued: Wrong format");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.WRONG_FORMAT);
				}
				retDiscontinued = optDiscontinued.get();
				
				if (retIntroduced.after(retDiscontinued)) {
					logger.warn("checkDates - Discontinued less then introduced");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_LESS_THAN_INTRO);
				}
			}
		}
	}
	
	private void checkCompany(ComputerDTO computer) throws FailComputerException {
		String companyId = computer.getCompanyId();
		
		if (companyId != null) {
			Optional<Integer> optCompanyId = Util.parseInt(companyId);
			if (optCompanyId.isEmpty()) {
				logger.warn("checkCompany - Invalid company id");
				throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.INVALID_COMPANY_ID);
			}
			
			int intCompanyId = optCompanyId.get();
			
			if (intCompanyId > 0 && companyDAO.getCompanyById(intCompanyId).isEmpty()) {
				logger.warn("checkCompany - nonexistent company");
				throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.NONEXISTENT_COMPANY);
			}
		} else {
			computer.setCompanyId("0");
		}
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
		String name = computer.getName();
		String introduced = computer.getIntroduced();
		String discontinued = computer.getIntroduced();
		String companyId = computer.getCompanyId();
		
		checkName(computer);
		checkDates(computer);
		checkCompany(computer);
		
		Timestamp tsIntroduced = Util.extract(Util.dateToTimestamp(introduced));
		Timestamp tsDiscontinued = Util.extract(Util.dateToTimestamp(discontinued));
		
		Optional<Company> company = companyDAO.getCompanyById(Integer.parseInt(companyId));
		
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
		computer.setCompanyId(Util.accordingTo(s -> s != null, computer.getCompanyId(), "0"));
		
		String id = computer.getId();
		String name = computer.getName();
		String introduced = computer.getIntroduced();
		String discontinued = computer.getIntroduced();
		String companyId = computer.getCompanyId();
		
		checkId(computer);
		checkName(computer);
		checkDates(computer);
		checkCompany(computer);
		
		int intId = Util.parseInt(id).get();
		Timestamp tsIntroduced = Util.extract(Util.dateToTimestamp(introduced));
		Timestamp tsDiscontinued = Util.extract(Util.dateToTimestamp(discontinued));
		Optional<Company> company = companyDAO.getCompanyById(Integer.parseInt(companyId));
		
		if (Integer.parseInt(companyId) <= 0) {
			update(intId, name, tsIntroduced, tsDiscontinued, null);
			return;
		}
		
		update(intId, name, tsIntroduced, tsDiscontinued, company.get());
	}
	
	public int deleteComputer(String id) throws FailComputerException {
		Optional<Integer> optId = Util.parseInt(id);
		
		if (optId.isPresent()) {
			return computerDAO.deleteComputer(optId.get());
		}
		
		throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
	}
}
