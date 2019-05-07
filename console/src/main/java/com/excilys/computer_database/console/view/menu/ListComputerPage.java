package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.core.model.SortMode;
import com.excilys.computer_database.service.service.ComputerService;

@Component
public class ListComputerPage extends Page {
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private MenuPage menuPage;
	
	private ListComputerPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's computer list"));
		return null;
	}

	@Override
	public Optional<Page> exec(String input) {
		StringBuilder sbList = new StringBuilder();
		service.getAll(SortMode.DEFAULT).forEach(s -> sbList.append(s+"\n"));
		String list = sbList.toString();
		
		System.out.println(boxMessage(list.toString(), 3));
		System.out.println();
		System.out.println(boxMessage(M_BACK_MENU));
		System.out.println();
		return backToMenu();
	}
	
	public String toString() {
		return "ListComputer";
	}
}
