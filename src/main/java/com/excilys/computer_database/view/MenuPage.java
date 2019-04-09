package com.excilys.computer_database.view;

import java.util.Optional;

import com.excilys.computer_database.util.Util;

public class MenuPage extends Page {
	
	private String datasource;
	
	public MenuPage(String datasource) {
		this.datasource = datasource;
	}

	@Override
	public String show() {
		System.out.println("Choose:");
		System.out.println(PageDescriptor.LIST_COMPUTER);
		System.out.println(PageDescriptor.LIST_COMPANY);
		System.out.println(PageDescriptor.SHOW_COMPUTER);
		System.out.println(PageDescriptor.CREATE_COMPUTER);
		System.out.println(PageDescriptor.UPDATE_COMPUTER);
		System.out.println(PageDescriptor.DELETE_COMPUTER);
		System.out.println(PageDescriptor.QUIT);
		
		String input = this.scan.nextLine();
		
		return input;
	}
	
	private Page wrongTyped() {
		System.out.println(Util.boxMessage("Error typing, " + BACK_MENU));
		System.out.println();
		return new MenuPage(datasource);
	}

	@Override
	public Optional<Page> exec(String input) {
		// TODO Auto-generated method stub
		Optional<Integer> choice = Util.parseInt(input);
		Optional<Page> pageReturn = Optional.empty();
		
		if (!choice.isPresent()) {
			pageReturn = Optional.of(wrongTyped());
		} else {
			
			switch (choice.get()) {
			case 1 :
				pageReturn = Optional.of(new ListComputerPage(datasource));
				break;
			
			case 2 : 
				pageReturn = Optional.of(new ListCompanyPage(datasource));
				break;
			
			case 3 :
				pageReturn = Optional.of(new ShowComputerPage(datasource));
				break;
			
			case 4 : 
				pageReturn = Optional.of(new CreateComputerPage(datasource));
				break;
				
			case 5 : 
				pageReturn = Optional.of(new UpdateComputerPage(datasource));
				break;
			
			case 6 :
				pageReturn = Optional.of(new DeleteComputerPage(datasource));
				break;
			
			case 7 : 
				System.out.println(Util.boxMessage("Goodbye!"));
				pageReturn = Optional.empty();
				break;
			
			default :
				pageReturn = Optional.of(wrongTyped());
				break;
			}
		}
		return pageReturn;
	}
	
}
