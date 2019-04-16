package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.service.CompanyService;

@Component
public class ListCompanyPage extends Page {
	
	@Autowired
	private CompanyService service;
	
	@Autowired
	private MenuPage menuPage;
	
	private ListCompanyPage() {}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's company list"));
		return null;
	}

	@Override
	public Optional<Page> exec(String input) {
		service.getAll().forEach(System.out::println);
		System.out.println();
		System.out.println(boxMessage(M_BACK_MENU));
		System.out.println();
		return Optional.of(menuPage);
	}
	
	public String toString() {
		return "List company";
	}
}
