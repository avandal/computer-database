package com.excilys.view;

import com.excilys.util.Util;

public class MenuPage extends Page {
	
	public MenuPage() {}

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
		return new MenuPage();
	}

	@Override
	public Page exec(String input) {
		// TODO Auto-generated method stub
		Integer choice = Util.parseInt(input);
		Page pageReturn = null;
		
		if (choice == null) {
			pageReturn = wrongTyped();
		} else {
			
			switch (choice) {
			case 1 :
				pageReturn = new ListComputerPage();
				break;
			
			case 2 : 
				pageReturn = new ListCompanyPage();
				break;
			
			case 3 :
				pageReturn = new ShowComputerPage();
				break;
			
			case 4 : 
				pageReturn = new CreateComputerPage();
				break;
				
			case 5 : 
				pageReturn = new UpdateComputerPage();
				break;
			
			case 6 :
				pageReturn = new DeleteComputerPage();
				break;
			
			case 7 : 
				System.out.println(Util.boxMessage("Goodbye!"));
				pageReturn = null;
				break;
			
			default :
				pageReturn = wrongTyped();
				break;
			}
		}
		return pageReturn;
	}
	
}
