package view;

import com.excilys.persistence.CompanyDAO;

import util.Util;

public class ListCompanyPage extends Page {

	public ListCompanyPage() {}

	@Override
	public String show() {
		System.out.println(Util.boxMessage("Here is the database's company list"));
		return null;
	}

	@Override
	public Page exec(String input) {
		CompanyDAO.companyList().forEach(System.out::println);
		System.out.println();
		System.out.println(M_BACK_MENU);
		System.out.println();
		return new MenuPage();
	}

}
