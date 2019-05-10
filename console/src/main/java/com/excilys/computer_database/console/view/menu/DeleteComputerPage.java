package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Component
public class DeleteComputerPage extends Page {
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private MenuPage menuPage;
	
	@Autowired
	private DeleteComputerPage that;

	private DeleteComputerPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		System.out.println(boxMessage(String.format("Please give a computer id (%s)", ABORT)));

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
		} catch (FailComputerException e) {
			e.printStackTrace();
		}
		
		return backToMenu();
	}

	public String toString() {
		return "Delete computer";
	}
}
