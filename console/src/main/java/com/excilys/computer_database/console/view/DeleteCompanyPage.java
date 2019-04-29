package com.excilys.computer_database.console.view;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.service.service.CompanyService;

@Component
public class DeleteCompanyPage extends Page {
	
	@Autowired
	private CompanyService service;
	
	@Autowired
	private MenuPage menuPage;
	
	@Autowired
	private DeleteCompanyPage deleteCompanyPage;

	@Override
	public String show() {
		System.out.println(boxMessage("Please give a company id ('abort' to abort')"));

		String input = this.scan.nextLine();

		return input;
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(deleteCompanyPage);
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return Optional.of(menuPage);
		}
		
		Optional<Integer> idInput = Util.parseInt(input);
		
		if (idInput.isEmpty()) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return Optional.of(deleteCompanyPage);
		}

		if (idInput.get() <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return Optional.of(deleteCompanyPage);
		}

		int status = service.delete(input);
		
		if (status == 1) {
			System.out.println(boxMessage("Computer successfully deleted"));
		} else {
			System.out.println(boxMessage("[Problem] Fail deleting computer"));
		}
		
		return Optional.of(menuPage);
	}

	@Override
	public String toString() {
		return "Delete company";
	}

}
