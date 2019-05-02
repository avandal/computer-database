package com.excilys.computer_database.console.view;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.util.Util;

@Component
public class MenuPage extends Page {
	private Logger logger = LoggerFactory.getLogger(MenuPage.class);
	
	@Autowired
	private ListComputerPage listComputerPage;
	
	@Autowired
	private ListCompanyPage listCompanyPage;
	
	@Autowired
	private ShowComputerPage showComputerPage;
	
	@Autowired
	private CreateComputerPage createComputerPage;
	
	@Autowired
	private UpdateComputerPage updateComputerPage;
	
	@Autowired
	private DeleteComputerPage deleteComputerPage;
	
	@Autowired
	private DeleteCompanyPage deleteCompanyPage;
	
	@Autowired
	private CreateUserPage createUserPage;
	
	@Autowired
	private MenuPage menuPage;
	
	private MenuPage() {}

	@Override
	public String show() {
		logger.debug("MenuPage - Show");
		System.out.println("Choose:");
		Arrays.asList(PageDescriptor.values()).forEach(System.out::println);
		
		String input = this.scan.nextLine();
		
		return input;
	}
	
	private Page wrongTyped() {
		System.out.println(Util.boxMessage("Error typing, " + BACK_MENU));
		System.out.println();
		return menuPage;
	}

	@Override
	public Optional<Page> exec(String input) {
		logger.debug("MenuPage - Exec");
		// TODO Auto-generated method stub
		Optional<Integer> choice = Util.parseInt(input);
		Optional<Page> pageReturn = Optional.empty();
		
		if (choice.isEmpty()) {
			pageReturn = Optional.of(wrongTyped());
		} else {
			
			switch (choice.get()) {
			case 1 :
				logger.debug("MenuPage - Exec : listComputer chosen");
				pageReturn = Optional.of(listComputerPage);
				break;
			
			case 2 : 
				pageReturn = Optional.of(listCompanyPage);
				break;
			
			case 3 :
				pageReturn = Optional.of(showComputerPage);
				break;
			
			case 4 : 
				pageReturn = Optional.of(createComputerPage);
				break;
				
			case 5 : 
				pageReturn = Optional.of(updateComputerPage);
				break;
			
			case 6 :
				pageReturn = Optional.of(deleteComputerPage);
				break;
			
			case 7 : 
				pageReturn = Optional.of(deleteCompanyPage);
				break;
				
			case 8 :
				pageReturn = Optional.of(createUserPage);
				break;
			
			case 9 : 
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
	
	public String toString() {
		return "Menu";
	}
}
