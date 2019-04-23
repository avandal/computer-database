package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.SortMode;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.exception.ConcernedField;
import com.excilys.computer_database.service.exception.FailComputerException;
import com.excilys.computer_database.util.Util;

@Service
public class ComputerService {
	
	@Autowired
	private ComputerDAO dao;
	
	@Autowired
	private CompanyService companyService;
	
	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	private ComputerService() {}
	
	public List<ComputerDTO> getAll(SortMode orderMode) {
		logger.debug("ComputerService - getAll : callings dao.computerList");
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
		if (name == null || "".equals(name)) {
			logger.warn("createComputer - Empty name");
			throw new FailComputerException(ConcernedField.NAME, FailComputerException.NULL_NAME);
		}
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if (introduced == null || "".equals(introduced)) {
			if (discontinued != null && !"".equals(discontinued)) {
				System.out.println(discontinued);
				logger.warn("createComputer - Discontinued without introduced");
				throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_WITHOUT_INTRO);
			}
		} else {
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (optIntroduced.isEmpty()) {
				logger.warn("createComputer - Introduced: Wrong format");
				throw new FailComputerException(ConcernedField.INTRODUCED, FailComputerException.WRONG_FORMAT);
			}
			retIntroduced = optIntroduced.get();
			
			if (!"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (optDiscontinued.isEmpty()) {
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
		if (optCompanyId.isEmpty()) {
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
	
	public int createComputer(ComputerDTO computer) throws FailComputerException {
		return createComputer(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompanyId());
	}
	
	public int updateComputer(int id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		return dao.updateComputer(id, name, introduced, discontinued, companyId);
	}
	
	public int updateComputer(String id, String name, String introduced, String discontinued, String companyId) throws FailComputerException {
		if ("".equals(name)) {
			logger.warn("updateComputer - Empty name");
			throw new FailComputerException(ConcernedField.NAME, FailComputerException.NULL_NAME);
		}
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if (introduced == null || "".equals(introduced)) {
			if (discontinued != null && !"".equals(discontinued)) {
				logger.warn("updateComputer - Discontinued without introduced");
				throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_WITHOUT_INTRO);
			}
		} else {
			System.out.println(introduced);
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (optIntroduced.isEmpty()) {
				logger.warn("updateComputer - Introduced: Wrong format");
				throw new FailComputerException(ConcernedField.INTRODUCED, FailComputerException.WRONG_FORMAT);
			}
			retIntroduced = optIntroduced.get();
			
			if (discontinued != null && !"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (optDiscontinued.isEmpty()) {
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
		if (optCompanyId.isEmpty()) {
			logger.warn("updateComputer - Invalid company id");
			throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.INVALID_COMPANY_ID);
		}
		
		int intCompanyId = optCompanyId.get();
		
		if (intCompanyId <= 0) {
			if (Util.parseInt(id).isPresent()) {
				return updateComputer(Util.parseInt(id).get(), name, retIntroduced, retDiscontinued, null);
			} else {
				throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
			}
		}
		
		if (companyService.getById(intCompanyId).isPresent()) {
			if (Util.parseInt(id).isPresent()) {
				return updateComputer(Util.parseInt(id).get(), name, retIntroduced, retDiscontinued, intCompanyId);
			} else {
				throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
			}
		}
		
		logger.warn("updateComputer - Nonexistent company");
		throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.NONEXISTENT_COMPANY);
	}
	
	public int updateComputer(ComputerDTO computer) throws FailComputerException {
		return updateComputer(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompanyId());
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
