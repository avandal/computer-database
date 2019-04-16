package com.excilys.computer_database.view;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.util.Util;

@Component
public class MenuPage extends Page {
	
	private Logger logger = LoggerFactory.getLogger(MenuPage.class);
	
	@Autowired
	private GenericApplicationContext context;
	
	private MenuPage() {}

	@Override
	public String show() {
		logger.debug("MenuPage - Show");
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
		return context.getBean(MenuPage.class);
	}

	@Override
	public Optional<Page> exec(String input) {
		logger.debug("MenuPage - Exec");
		// TODO Auto-generated method stub
		Optional<Integer> choice = Util.parseInt(input);
		Optional<Page> pageReturn = Optional.empty();
		
		if (!choice.isPresent()) {
			pageReturn = Optional.of(wrongTyped());
		} else {
			
			switch (choice.get()) {
			case 1 :
				logger.debug("MenuPage - Exec : listComputer chosen");
				pageReturn = Optional.of(context.getBean(ListComputerPage.class));
				break;
			
			case 2 : 
				pageReturn = Optional.of(context.getBean(ListCompanyPage.class));
				break;
			
			case 3 :
				pageReturn = Optional.of(context.getBean(ShowComputerPage.class));
				break;
			
			case 4 : 
				pageReturn = Optional.of(context.getBean(CreateComputerPage.class));
				break;
				
			case 5 : 
				pageReturn = Optional.of(context.getBean(UpdateComputerPage.class));
				break;
			
			case 6 :
				pageReturn = Optional.of(context.getBean(DeleteComputerPage.class));
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
	
	public String toString() {
		return "Menu";
	}
}
