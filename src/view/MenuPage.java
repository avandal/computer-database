package view;

import util.Util;

public class MenuPage extends Page {
	
	public MenuPage() {}

	@Override
	public String show() {
		System.out.println("Choose:");
		System.out.println("1 - " + PageDescriptor.LIST_COMPUTER);
		System.out.println("2 - " + PageDescriptor.LIST_COMPANY);
		System.out.println("3 - " + PageDescriptor.SHOW_COMPUTER);
		System.out.println("4 - " + PageDescriptor.CREATE_COMPUTER);
		System.out.println("5 - " + PageDescriptor.UPDATE_COMPUTER);
		System.out.println("6 - " + PageDescriptor.DELETE_COMPUTER);
		System.out.println("7 - Quit");
		
		String input = this.scan.nextLine();
		
		return input;
	}
	
	private Page wrongTyped() {
		System.out.println("Error typing, " + BACK_MENU);
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
			
			case 4 : break;
			
			case 5 : break;
			
			case 6 : break;
			
			case 7 : 
				System.out.println("Goodbye!");
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
