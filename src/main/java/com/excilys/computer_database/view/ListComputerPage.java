package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import com.excilys.computer_database.persistence.ComputerDAO;

public class ListComputerPage extends Page {
	
	public ListComputerPage() {}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's computer list"));
		return null;
	}

	@Override
	public Page exec(String input) {
		ComputerDAO.computerList().forEach(System.out::println);
		System.out.println();
		System.out.println(boxMessage(M_BACK_MENU));
		System.out.println();
		return new MenuPage();
	}

}
