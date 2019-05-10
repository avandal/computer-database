package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.service.service.UserService;
import com.excilys.computer_database.service.service.exception.FailUserException;

@Component
public class CreateUserPage extends Page {
	private final static int STEP_USERNAME = 1;
	private final static int STEP_PASSWORD = 2;
	
	private final static String USERNAME_MESSAGE = "Please give a username ('abort' to abort')";
	private final static String PASSWORD_MESSAGE = "Please give a password ('abort' to abort')";
	
	private int step = 1;
	
	private String username;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CreateUserPage that;
	
	@Autowired
	private MenuPage menuPage;
	
	@Override
	protected Optional<Page> backToMenu() {
		step = STEP_USERNAME;
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		switch (step) {
		case STEP_USERNAME : System.out.println(boxMessage(USERNAME_MESSAGE)); break;
		case STEP_PASSWORD : System.out.println(boxMessage(PASSWORD_MESSAGE)); break;
		default : break;
		}

		return this.scan.nextLine();
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(that);
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage(String.format("[Aborted] %s", M_BACK_MENU)));
			return backToMenu();
		}
		
		switch (step) {
		case STEP_USERNAME :
			username = input;
			step = STEP_PASSWORD;
			return Optional.of(that);
			
		case STEP_PASSWORD :
			try {
				userService.createUser(username, input);
				System.out.println(boxMessage(String.format("User successfully created, %s", BACK_MENU)));
			} catch (FailUserException e) {
				e.printStackTrace();
				System.out.println(boxMessage(String.format("[Error] %s", M_BACK_MENU)));
			}
			return backToMenu();
			
		default :
			return backToMenu();
		}
	}

	@Override
	public String toString() {
		return "Create user";
	}

}
