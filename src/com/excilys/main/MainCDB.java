package com.excilys.main;

import com.excilys.control.Controller;
import com.excilys.persistence.DAO;

public class MainCDB {
	public static void main(String[] args) {
		DAO.initConnection();
		
		Controller control = new Controller();
		control.run();
		DAO.closeConnection();
	}
}
