package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import java.util.Optional;

import com.excilys.computer_database.AppConfig;
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.util.Util;

public class ShowComputerPage extends Page {
	
	private ComputerService service;

	public ShowComputerPage() {
		service = AppConfig.context.getBean(ComputerService.class);
	}

	@Override
	public String show() {
		System.out.println(boxMessage("Please give a computer id ('abort' to abort')"));
		
		String input = this.scan.nextLine();
		
		return input;
	}

	private Optional<Page> initialCheck(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(new ShowComputerPage());
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return Optional.of(new MenuPage());
		}
		
		return Optional.empty();
	}

	private Optional<Page> invalidInput(Optional<Integer> idInput) {
		if (!idInput.isPresent()) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return Optional.of(new ShowComputerPage());
		}
		
		if (idInput.get() <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return Optional.of(new ShowComputerPage());
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<Page> exec(String input) {
		Optional<Page> next = initialCheck(input);
		
		if (next.isPresent())
			return next;
		
		Optional<Integer> idInput = Util.parseInt(input);
		
		Optional<Page> isInvalid = invalidInput(idInput);
		
		if (isInvalid.isPresent())
			return isInvalid;
		
		Optional<ComputerDTO> computer = service.getComputerDetails(idInput.get());
		
		if (computer.isPresent()) {
			System.out.println(boxMessage("Here's the "+idInput.get()+" computer"));
			System.out.println(computer.get());
		} else {
			System.out.println((boxMessage("There is no computer with this id")));
		}
		
		System.out.println(boxMessage(M_BACK_MENU));
		
		return Optional.of(new MenuPage());
	}
	
	public String toString() {
		return "Show computer";
	}
}
