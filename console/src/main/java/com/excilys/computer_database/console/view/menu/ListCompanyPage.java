package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.service.service.CompanyService;

@Component
public class ListCompanyPage extends Page {
	
	@Autowired
	private CompanyService service;
	
	@Autowired
	private MenuPage menuPage;
	
	private ListCompanyPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's company list"));
		return null;
	}

	@Override
	public Optional<Page> exec(String input) {
		StringBuilder sbList = new StringBuilder();
		service.getAll().forEach(s -> sbList.append(s+"\n"));
		String list = sbList.toString();
		
		System.out.println(String.format("%s\n%s\n", boxMessage(list, 3), M_BACK_MENU));
		return backToMenu();
	}
	
	public String toString() {
		return "List company";
	}
}
