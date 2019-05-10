package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.service.service.CompanyService;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Component
public class DeleteCompanyPage extends Page {
	
	@Autowired
	private CompanyService service;
	
	@Autowired
	private MenuPage menuPage;
	
	@Autowired
	private DeleteCompanyPage that;
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		System.out.println(boxMessage(String.format("Please give a company id (%s)", ABORT)));

		return this.scan.nextLine();
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(that);
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage(String.format("[Aborted] %s", BACK_MENU)));
			return backToMenu();
		}
		
		Optional<Integer> idInput = Util.parseInt(input);
		
		if (idInput.isEmpty()) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return Optional.of(that);
		}

		if (idInput.get() <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return Optional.of(that);
		}

		try {
			service.delete(input);
			System.out.println(boxMessage("Computer successfully deleted"));
		} catch (FailComputerException e) {
			System.out.println(boxMessage("[Problem] Fail deleting company"));
			e.printStackTrace();
		}
		
		return backToMenu();
	}

	@Override
	public String toString() {
		return "Delete company";
	}

}
