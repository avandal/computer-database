package com.excilys.computer_database.control;

import java.util.List;

import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.persistence.ComputerDAO;

public class ControlerComputer {
	
	private static volatile ControlerComputer instance;
	
	private ControlerComputer() {}
	
	public static ControlerComputer getInstance() {
		if (instance == null) {
			synchronized(ControlerComputer.class) {
				if (instance == null) {
					instance = new ControlerComputer();
				}
			}
		}
		return instance;
	}
	
	public List<Computer> getAll() {
		ComputerDAO dao = ComputerDAO.getInstance();
		return dao.computerList();
	}
}
