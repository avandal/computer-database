package view;

import com.excilys.persistence.ComputerDAO;

import util.Util;

public class ListComputerPage extends Page {
	
	public ListComputerPage() {}

	@Override
	public String show() {
		System.out.println(Util.boxMessage("Here is the database's computer list"));
		return null;
	}

	@Override
	public Page exec(String input) {
		ComputerDAO.computerList().forEach(System.out::println);
		System.out.println();
		System.out.println(M_BACK_MENU);
		System.out.println();
		return new MenuPage();
	}

}
