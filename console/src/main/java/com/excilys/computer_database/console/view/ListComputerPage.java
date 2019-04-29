package com.excilys.computer_database.console.view;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.core.model.SortMode;
import com.excilys.computer_database.service.service.ComputerService;

@Component
public class ListComputerPage extends Page {
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private MenuPage menuPage;
	
	private Logger logger = LoggerFactory.getLogger(ListComputerPage.class);
	
	private ListComputerPage() {}

	@Override
	public String show() {
		System.out.println(boxMessage("Here is the database's computer list"));
		return null;
	}

	@Override
	public Optional<Page> exec(String input) {
		logger.debug("ListComputerPage - Exec : Before showing all");
		service.getAll(SortMode.DEFAULT).forEach(System.out::println);
		logger.debug("ListComputerPage - Exec : After showing all");
		System.out.println();
		System.out.println(boxMessage(M_BACK_MENU));
		System.out.println();
		return Optional.of(menuPage);
	}
	
	public String toString() {
		return "ListComputer";
	}
}
