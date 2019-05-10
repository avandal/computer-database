package com.excilys.computer_database.console.view;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.menu.CreateComputerPage;
import com.excilys.computer_database.console.view.menu.CreateUserPage;
import com.excilys.computer_database.console.view.menu.DeleteCompanyPage;
import com.excilys.computer_database.console.view.menu.DeleteComputerPage;
import com.excilys.computer_database.console.view.menu.ListCompanyPage;
import com.excilys.computer_database.console.view.menu.ListComputerPage;
import com.excilys.computer_database.console.view.menu.ShowComputerPage;
import com.excilys.computer_database.console.view.menu.update.UpdateComputerPage;

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
	private MenuPage that;
	
	private MenuPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(that);
	}

	@Override
	public String show() {
		StringBuilder shower = new StringBuilder("Choose:");
		
		Consumer<MenuDescriptor> appendShow = descr -> shower.append(String.format("\n%s",descr));
		
		Arrays.asList(MenuDescriptor.values()).forEach(appendShow);
		System.out.println(shower.toString());
		
		return this.scan.nextLine();
	}
	
	private Optional<Page> wrongTyped() {
		System.out.println(Util.boxMessage(String.format("Error typing, %s\n", BACK_MENU)));
		return backToMenu();
	}

	@Override
	public Optional<Page> exec(String input) {
		logger.debug("MenuPage - Exec");
		Optional<Integer> choice = Util.parseInt(input);
		Optional<Page> pageReturn = Optional.empty();
		
		if (choice.isEmpty()) {
			pageReturn = backToMenu();
		} else {
			Optional<MenuDescriptor> matching = MenuDescriptor.getById(choice.get());
			
			if (matching.isEmpty() ) {
				return wrongTyped();
			}
			
			switch (matching.get()) {
			case LIST_COMPUTER :
				pageReturn = Optional.of(listComputerPage);
				break;
			
			case LIST_COMPANY : 
				pageReturn = Optional.of(listCompanyPage);
				break;
			
			case SHOW_COMPUTER :
				pageReturn = Optional.of(showComputerPage);
				break;
			
			case CREATE_COMPUTER : 
				pageReturn = Optional.of(createComputerPage);
				break;
				
			case UPDATE_COMPUTER : 
				pageReturn = Optional.of(updateComputerPage);
				break;
			
			case DELETE_COMPUTER :
				pageReturn = Optional.of(deleteComputerPage);
				break;
			
			case DELETE_COMPANY : 
				pageReturn = Optional.of(deleteCompanyPage);
				break;
				
			case CREATE_USER :
				pageReturn = Optional.of(createUserPage);
				break;
			
			case QUIT : 
				System.out.println(Util.boxMessage("Goodbye!"));
				pageReturn = Optional.empty();
				break;
			}
		}
		return pageReturn;
	}
	
	public String toString() {
		return "Menu";
	}
}
