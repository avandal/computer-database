package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.exception.ConcernedField;
import com.excilys.computer_database.service.exception.FailComputerException;
import com.excilys.computer_database.servlets.SortMode;
import com.excilys.computer_database.util.Util;

public class ComputerService {
	private ComputerDAO dao;
	
	private CompanyService companyService;
	
	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	public ComputerService(ComputerDAO dao, CompanyService companyService) {
		this.dao = dao;
		this.companyService = companyService;
	}
	
	public List<ComputerDTO> getAll(SortMode orderMode) {		
		return dao.computerList(orderMode.suffix()).stream().map(c -> ComputerMapper.computerToDTO(c)).collect(Collectors.toList());
	}
	
	public List<ComputerDTO> searchByName(String name, SortMode orderMode) {
		return dao.getByName(name, orderMode.suffix()).stream().map(c -> ComputerMapper.computerToDTO(c)).collect(Collectors.toList());
	}
	
	public Optional<ComputerDTO> getComputerDetails(int id) {
		Optional<Computer> computer = dao.getComputerDetails(id);
		if (computer.isPresent()) {
			return Optional.of(ComputerMapper.computerToDTO(computer.get()));
		}
		
		return Optional.empty();
	}
	
	public int createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		return dao.createComputer(name, introduced, discontinued, companyId);
	}
	
	public int createComputer(String name, String introduced, String discontinued, String companyId) throws FailComputerException {
		if ("".equals(name)) {
			logger.warn("createComputer - Empty name");
			throw new FailComputerException(ConcernedField.NAME, FailComputerException.NULL_NAME);
		}
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if ("".equals(introduced)) {
			if (!"".equals(discontinued)) {
				logger.warn("createComputer - Discontinued without introduced");
				throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_WITHOUT_INTRO);
			}
		} else {
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (!optIntroduced.isPresent()) {
				logger.warn("createComputer - Introduced: Wrong format");
				throw new FailComputerException(ConcernedField.INTRODUCED, FailComputerException.WRONG_FORMAT);
			}
			retIntroduced = optIntroduced.get();
			
			if (!"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (!optDiscontinued.isPresent()) {
					logger.warn("createComputer - Discontinued: Wrong format");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.WRONG_FORMAT);
				}
				retDiscontinued = optDiscontinued.get();
				
				if (retIntroduced.after(retDiscontinued)) {
					logger.warn("createComputer - Discontinued less then introduced");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_LESS_THAN_INTRO);
				}
			}
		}
		
		Optional<Integer> optCompanyId = Util.parseInt(companyId);
		if (!optCompanyId.isPresent()) {
			logger.warn("createComputer - Invalid company id");
			throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.INVALID_COMPANY_ID);
		}
		
		int intCompanyId = optCompanyId.get();
		
		if (intCompanyId <= 0) {
			return createComputer(name, retIntroduced, retDiscontinued, null);
		}
		
		if (companyService.getById(intCompanyId).isPresent()) {
			return createComputer(name, retIntroduced, retDiscontinued, intCompanyId);
		}
		
		logger.warn("createComputer - nonexistent company");
		throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.NONEXISTENT_COMPANY);
	}
	
	public int updateComputer(int id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		return dao.updateComputer(id, name, introduced, discontinued, companyId);
	}
	
	public int updateComputer(int id, String name, String introduced, String discontinued, String companyId) throws FailComputerException {
		if ("".equals(name)) {
			logger.warn("updateComputer - Empty name");
			throw new FailComputerException(ConcernedField.NAME, FailComputerException.NULL_NAME);
		}
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if ("".equals(introduced)) {
			if (discontinued != null && !"".equals(discontinued)) {
				logger.warn("updateComputer - Discontinued without introduced");
				throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_WITHOUT_INTRO);
			}
		} else {
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (!optIntroduced.isPresent()) {
				logger.warn("updateComputer - Introduced: Wrong format");
				throw new FailComputerException(ConcernedField.INTRODUCED, FailComputerException.WRONG_FORMAT);
			}
			retIntroduced = optIntroduced.get();
			
			if (discontinued != null && !"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (!optDiscontinued.isPresent()) {
					logger.warn("updateComputer - Discontinued: Wrong format");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.WRONG_FORMAT);
				}
				retDiscontinued = optDiscontinued.get();
				
				if (retIntroduced.after(retDiscontinued)) {
					logger.warn("updateComputer - Discontinued less then introduced");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_LESS_THAN_INTRO);
				}
			}
		}
		
		Optional<Integer> optCompanyId = Util.parseInt(companyId);
		if (!optCompanyId.isPresent()) {
			logger.warn("updateComputer - Invalid company id");
			throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.INVALID_COMPANY_ID);
		}
		
		int intCompanyId = optCompanyId.get();
		
		if (intCompanyId <= 0) {
			return updateComputer(id, name, retIntroduced, retDiscontinued, null);
		}
		
		if (companyService.getById(intCompanyId).isPresent()) {
			return updateComputer(id, name, retIntroduced, retDiscontinued, intCompanyId);
		}
		
		logger.warn("updateComputer - Nonexistent company");
		throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.NONEXISTENT_COMPANY);
	}
	
	public int deleteComputer(int id) {
		return dao.deleteComputer(id);
	}
	
	public int deleteComputer(String id) throws FailComputerException {
		Optional<Integer> optId = Util.parseInt(id);
		
		if (optId.isPresent()) {
			return deleteComputer(optId.get());
		}
		
		throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
	}
}
