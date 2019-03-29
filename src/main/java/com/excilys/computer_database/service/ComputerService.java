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
import com.excilys.computer_database.service.exception.FailCreateException;
import com.excilys.computer_database.util.Util;

public class ComputerService {
	private ComputerDAO dao;
	
	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	private static volatile ComputerService instance;
	
	private ComputerService() {
		this.dao = ComputerDAO.getInstance();
	}
	
	public static ComputerService getInstance() {
		if (instance == null) {
			synchronized(ComputerService.class) {
				if (instance == null) {
					instance = new ComputerService();
				}
			}
		}
		return instance;
	}
	
	public List<ComputerDTO> getAll() {
		return dao.computerList().stream().map(c -> ComputerMapper.computerToDTO(c)).collect(Collectors.toList());
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
	
	public int createComputer(String name, String introduced, String discontinued, String companyId) throws FailCreateException {
		if ("".equals(name)) {
			logger.warn("createComputer - Empty name");
			throw new FailCreateException(ConcernedField.NAME, FailCreateException.NULL_NAME);
		}
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if ("".equals(introduced)) {
			if (!"".equals(discontinued)) {
				logger.warn("createComputer - Discontinued without introduced");
				throw new FailCreateException(ConcernedField.DISCONTINUED, FailCreateException.DISC_WITHOUT_INTRO);
			}
		} else {
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (!optIntroduced.isPresent()) {
				logger.warn("createComputer - Introduced: Wrong format");
				throw new FailCreateException(ConcernedField.INTRODUCED, FailCreateException.WRONG_FORMAT);
			}
			retIntroduced = optIntroduced.get();
			
			if (!"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (!optDiscontinued.isPresent()) {
					logger.warn("createComputer - Discontinued: Wrong format");
					throw new FailCreateException(ConcernedField.DISCONTINUED, FailCreateException.WRONG_FORMAT);
				}
				retDiscontinued = optDiscontinued.get();
				
				if (retIntroduced.after(retDiscontinued)) {
					logger.warn("createComputer - Discontinued less then introduced");
					throw new FailCreateException(ConcernedField.DISCONTINUED, FailCreateException.DISC_LESS_THAN_INTRO);
				}
			}
		}
		
		Optional<Integer> optCompanyId = Util.parseInt(companyId);
		if (!optCompanyId.isPresent()) {
			logger.warn("createComputer - Invalid company id");
			throw new FailCreateException(ConcernedField.COMPANY, FailCreateException.INVALID_COMPANY_ID);
		}
		
		int intCompanyId = optCompanyId.get();
		
		if (intCompanyId <= 0) {
			return createComputer(name, retIntroduced, retDiscontinued, null);
		}
		
		CompanyService companyService = CompanyService.getInstance();
		
		if (companyService.getById(intCompanyId).isPresent()) {
			return createComputer(name, retIntroduced, retDiscontinued, intCompanyId);
		}
		
		throw new FailCreateException(ConcernedField.COMPANY, FailCreateException.NONEXISTENT_COMPANY);
	}
	
	public List<ComputerDTO> searchByName(String name) {
		return dao.getByName(name).stream().map(c -> ComputerMapper.computerToDTO(c)).collect(Collectors.toList());
	}
	
	public int updateComputer(int id, String name, Timestamp introduced, Timestamp discontinued) {
		return dao.updateComputer(id, name, introduced, discontinued);
	}
	
	public int deleteComputer(int id) {
		return dao.deleteComputer(id);
	}
}
