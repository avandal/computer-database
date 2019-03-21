package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
	
	public List<Computer> getAll() {
		return dao.computerList();
	}
	
	public Optional<Computer> getComputerDetails(int id) {
		return dao.getComputerDetails(id);
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
