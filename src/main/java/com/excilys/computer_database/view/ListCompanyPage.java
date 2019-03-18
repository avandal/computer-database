package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import com.excilys.computer_database.persistence.CompanyDAO;

public class ListCompanyPage extends Page {

	public ListCompanyPage() {}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's company list"));
		return null;
	}

	@Override
	public Page exec(String input) {
		CompanyDAO companyDAO = CompanyDAO.getInstance();
		companyDAO.companyList().forEach(System.out::println);
		System.out.println();
		System.out.println(boxMessage(M_BACK_MENU));
		System.out.println();
		return new MenuPage();
	}

}
