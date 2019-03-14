package com.excilys.view;

import static com.excilys.util.Util.boxMessage;

import com.excilys.persistence.ComputerDAO;
import com.excilys.util.Util;

public class DeleteComputerPage extends Page {

	public DeleteComputerPage() {}

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
			return new DeleteComputerPage();
		}

		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return new MenuPage();
		}

		Integer idInput = Util.parseInt(input);

		if (idInput == null) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return new DeleteComputerPage();
		}

		if (idInput <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return new DeleteComputerPage();
		}

		int status = ComputerDAO.deleteComputer(idInput);
		
		if (status == 1) {
			System.out.println(boxMessage("Computer successfully deleted"));
		} else {
			System.out.println(boxMessage("[Problem] Fail deleting computer"));
		}
		
		return new MenuPage();
	}

}
