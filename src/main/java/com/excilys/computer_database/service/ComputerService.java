package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.persistence.ComputerDAO;

public class ComputerService extends Service {
	private ComputerDAO dao;
	
	private static volatile ComputerService instance;
	
	private ComputerService() {
		this.dao = ComputerDAO.getInstance(DRIVER, URL, USER, PASSWORD);
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
	
	public int updateComputer(int id, String name, Timestamp introduced, Timestamp discontinued) {
		return dao.updateComputer(id, name, introduced, discontinued);
	}
	
	public int deleteComputer(int id) {
		return dao.deleteComputer(id);
	}
}
