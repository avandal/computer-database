package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import java.sql.Timestamp;
import java.util.Optional;

import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.util.Util;

public class CreateComputerPage extends Page {

	private final static String MSG_NAME = "Please give a computer name ('abort' to abort)";
	private final static String MSG_INTRODUCED = "Please give an introduced date ('abort' to abort, "
												+ "'null' to set null)";
	private final static String MSG_DISCONTINUED = "Please give a discontinued date ('abort' to abort, "
												+ "'null' to set null)";
	private final static String MSG_IDCOMP = "Please give a company id (an integer, 'abort' to abort, "
												+ "'null' to set null)";

	private String nameComp;
	private Timestamp introducedComp;
	private Timestamp discontinuedComp;
	private Integer companyIdComp;

	private int index = 1;
	
	private ComputerService service;
	
	private String datasource;

	public CreateComputerPage(String datasource) {
		this.datasource = datasource;
		this.service = ComputerService.getInstance(datasource);
	}

	@Override
	public String show() {
		switch (this.index) {
		case 1:
			System.out.println(boxMessage(MSG_NAME));
			break;
		case 2:
			System.out.println(boxMessage(MSG_INTRODUCED));
			break;
		case 3:
			System.out.println(boxMessage(MSG_DISCONTINUED));
			break;
		case 4:
			System.out.println(boxMessage(MSG_IDCOMP));
			break;
		default:
			break;
		}

		String input = this.scan.nextLine();

		return input;
	}

	private boolean execName(String input) {
		this.nameComp = input;
		return true;
	}

	private void setTimestamp(TimestampChoice choice, Timestamp time) {
		switch (choice) {
		case INTRODUCED: this.introducedComp = time; break;
		case DISCONTINUED: this.discontinuedComp = time; break;
		default: break;
		}
	}

	private boolean execTimestamp(TimestampChoice timestamp, String input) {
		if (input.equals("null")) {
			setTimestamp(timestamp, null);
			return true;
		}

		Optional<Timestamp> time = Util.parseTimestamp(input);

		if (!time.isPresent()) {
			System.out.println(boxMessage("Wrong format"));
			return false;
		}

		setTimestamp(timestamp, time.get());
		return true;
	}

	private boolean execCompany(String input) {
		if (input.equals("null")) {
			this.companyIdComp = null;
			return true;
		}

		Optional<Integer> id = Util.parseInt(input);

		if (!id.isPresent()) {
			System.out.println(boxMessage("Must be an integer"));
			return false;
		}

		this.companyIdComp = id.get();
		return true;
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(this);
		}

		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return Optional.of(new MenuPage(datasource));
		}

		boolean next = false;
		boolean finished = false;

		switch (this.index) {
		case 1: next = execName(input); break;

		case 2: next = execTimestamp(TimestampChoice.INTRODUCED, input); break;

		case 3: next = execTimestamp(TimestampChoice.DISCONTINUED, input); break;

		case 4: finished = execCompany(input); break;

		default: System.out.println(boxMessage("[Undefined problem]")); break;
		}

		if (finished) {
			int status = service.createComputer(nameComp, introducedComp, discontinuedComp, companyIdComp);
			if (status == -2) {
				System.out.println(boxMessage("[Error] this companyId doesn't exist, " + BACK_MENU));
			} else if (status <= 0) {
				System.out.println(boxMessage("[Error] Nothing has been created, " + BACK_MENU));
			} else {
				System.out.println(boxMessage("Computer successfully created, " + BACK_MENU));
			}

			return Optional.of(new MenuPage(datasource));
		}

		if (next) {
			index++;
		}

		return Optional.of(this);
	}
}