package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.console.view.menu.update.TimestampChoice;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Component
public class CreateComputerPage extends Page {
	private static final int NAME = 1;
	private static final int INTRODUCED = 2;
	private static final int DISCONTINUED = 3;
	private static final int IDCOMP = 4;
	
	private final static String NAME_MESSAGE = "Please give a computer name (" + ABORT + ")";
	private final static String INTRODUCED_MESSAGE = "Please give an introduced date (" + NULL_ABORT + ")";
	private final static String DISCONTINUED_MESSAGE = "Please give a discontinued date (" + NULL_ABORT + ")";
	private final static String IDCOMP_MESSAGE = "Please give a company id (an integer, " + NULL_ABORT + ")";

	private int step = 1;

	private String nameComp;
	private String introducedComp;
	private String discontinuedComp;
	private String companyIdComp;
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private CreateComputerPage createComputerPage;
	
	@Autowired
	private MenuPage menuPage;
	
	private CreateComputerPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		step = NAME;
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		switch (step) {
		case NAME: System.out.println(boxMessage(NAME_MESSAGE));break;
		case INTRODUCED:System.out.println(boxMessage(INTRODUCED_MESSAGE));break;
		case DISCONTINUED:System.out.println(boxMessage(DISCONTINUED_MESSAGE));break;
		case IDCOMP:System.out.println(boxMessage(IDCOMP_MESSAGE));break;
		default: break;
		}

		String input = this.scan.nextLine();

		return input;
	}
	
	private void step() {
		switch (step) {
		case NAME : step = INTRODUCED; break;
		case INTRODUCED : step = DISCONTINUED; break;
		case DISCONTINUED : step = IDCOMP; break;
		default : break;
		}
	}

	private void execName(String input) {
		this.nameComp = input;
		step();
	}

	private void setTimestamp(TimestampChoice choice, String time) {
		switch (choice) {
		case INTRODUCED: this.introducedComp = time; break;
		case DISCONTINUED: this.discontinuedComp = time; break;
		default: break;
		}
	}

	private void execTimestamp(TimestampChoice timestamp, String input) {
		if (input.equals("null")) {
			setTimestamp(timestamp, null);
			step();
			return;
		}

		Optional<Timestamp> time = Util.dateToTimestamp(input);

		if (time.isEmpty()) {
			System.out.println(boxMessage("Wrong format"));
			return;
		}

		setTimestamp(timestamp, input);
		step();
	}

	private void execCompany(String input) {
		if (input.equals("null")) {
			this.companyIdComp = null;
			step();
			return;
		}

		Optional<Integer> id = Util.parseInt(input);

		if (id.isEmpty()) {
			System.out.println(boxMessage("Must be an integer"));
			return;
		}

		this.companyIdComp = input;
		step();
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.trim().equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(createComputerPage);
		}

		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return backToMenu();
		}

		switch (step) {
		case NAME: execName(input); break;
		case INTRODUCED: execTimestamp(TimestampChoice.INTRODUCED, input); break;
		case DISCONTINUED: execTimestamp(TimestampChoice.DISCONTINUED, input); break;
		
		case IDCOMP: execCompany(input); 
			ComputerDTO computer = new ComputerDTOBuilder()
				.empty()
				.name(nameComp)
				.introduced(introducedComp)
				.discontinued(discontinuedComp)
				.companyId(companyIdComp)
				.build();
		
			try {
				service.create(computer);
				System.out.println(boxMessage("Computer successfully created, " + BACK_MENU));
			} catch (FailComputerException e) {
				e.printStackTrace();
				System.out.println(boxMessage("[Error] " + M_BACK_MENU));
			}
			
			return backToMenu();

		default: 
			System.out.println(boxMessage("[Undefined problem]")); 
			return backToMenu();
		}

		return Optional.of(createComputerPage);
	}
	
	public String toString() {
		return "CreateComputer";
	}
}