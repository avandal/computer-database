package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import java.util.Optional;

import com.excilys.computer_database.AppConfig;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

public class ListCompanyPage extends Page {

	private String datasource;
	
	public ListCompanyPage(String datasource) {
		this.datasource = datasource;
	}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's company list"));
		return null;
	}

	@Override
	public Optional<Page> exec(String input) {
		CompanyService service = AppConfig.context.getBean(CompanyService.class);
		service.getAll().forEach(System.out::println);
		System.out.println();
		System.out.println(boxMessage(M_BACK_MENU));
		System.out.println();
		return Optional.of(new MenuPage(datasource));
	}

}
