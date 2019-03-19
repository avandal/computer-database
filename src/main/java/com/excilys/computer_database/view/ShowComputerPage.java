package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.util.Util;

public class ShowComputerPage extends Page {
	
	private ComputerDAO dao;

	public ShowComputerPage() {
		dao = ComputerDAO.getInstance();
	}

	@Override
	public String show() {
		System.out.println(boxMessage("Please give a computer id ('abort' to abort')"));
		
		String input = this.scan.nextLine();
		
		return input;
	}

	@Override
	public Page exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return new ShowComputerPage();
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return new MenuPage();
		}
		
		Integer idInput = Util.parseInt(input);
		
		if (idInput == null) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return new ShowComputerPage();
		}
		
		if (idInput <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return new ShowComputerPage();
		}
		
		Computer computer = dao.getComputerDetails(idInput);
		System.out.println(boxMessage("Here's the "+idInput+" computer"));
		System.out.println(computer);
		
		System.out.println(boxMessage(M_BACK_MENU));
		return new MenuPage();
	}

}