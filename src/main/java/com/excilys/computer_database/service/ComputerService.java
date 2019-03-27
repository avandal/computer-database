package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.exception.FailCreateException;
import com.excilys.computer_database.util.Util;

public class ComputerService {
	private ComputerDAO dao;
	
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
		if (name == null) {
			throw new FailCreateException("Name cannot be empty");
		}
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if ("".equals(introduced)) {
			if (!"".equals(discontinued)) {
				throw new FailCreateException("If the introduced date is null, the discontinued date must be null too");
			}
		} else {
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (!optIntroduced.isPresent()) {
				throw new FailCreateException("Introduced must be in a valid format");
			}
			retIntroduced = optIntroduced.get();
			
			if (!"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (!optDiscontinued.isPresent()) {
					throw new FailCreateException("Discontinued must be in a valid format");
				}
				retDiscontinued = optDiscontinued.get();
				
				if (retIntroduced.after(retDiscontinued)) {
					throw new FailCreateException("Discontinued must be after introduced");
				}
			}
		}
		if (!Util.parseInt(companyId).isPresent()) {
			throw new FailCreateException("Invalid company id");
		}
		
		return createComputer(
				name, 
				retIntroduced, 
				retDiscontinued,
				Integer.parseInt(companyId) == 0 ? null : Integer.parseInt(companyId)
			);
	}
	
	public int updateComputer(int id, String name, Timestamp introduced, Timestamp discontinued) {
		return dao.updateComputer(id, name, introduced, discontinued);
	}
	
	public int deleteComputer(int id) {
		return dao.deleteComputer(id);
	}
}
