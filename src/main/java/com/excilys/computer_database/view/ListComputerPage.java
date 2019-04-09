package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import java.util.Optional;

import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.servlets.SortMode;

public class ListComputerPage extends Page {
	
	private ComputerService service;
	
	public ListComputerPage() {
		service = ComputerService.getInstance();
	}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's computer list"));
		return null;
	}

	@Override
	public Optional<Page> exec(String input) {
		service.getAll(SortMode.DEFAULT).forEach(System.out::println);
		System.out.println();
		System.out.println(boxMessage(M_BACK_MENU));
		System.out.println();
		return Optional.of(new MenuPage());
	}

}
