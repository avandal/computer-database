package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.service.CompanyService;

@Component
public class ListCompanyPage extends Page {
	
	@Autowired
	private GenericApplicationContext context;
	
	@Autowired
	private CompanyService service;
	
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
		return Optional.of(context.getBean(MenuPage.class));
	}
	
	public String toString() {
		return "List company";
	}
}
