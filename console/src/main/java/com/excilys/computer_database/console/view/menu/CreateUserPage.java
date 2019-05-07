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
	private static final int USERNAME = 1;
	private static final int PASSWORD = 2;
	
	private static final String USERNAME_MESSAGE = "Please give a username ('abort' to abort')";
	private static final String PASSWORD_MESSAGE = "Please give a password ('abort' to abort')";
	
	private int step = 1;
	
	private String username;
	private String password;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CreateUserPage createUserPage;
	
	@Autowired
	private MenuPage menuPage;
	
	@Override
	protected Optional<Page> backToMenu() {
		step = USERNAME;
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		switch (step) {
		case USERNAME : System.out.println(boxMessage(USERNAME_MESSAGE)); break;
		case PASSWORD : System.out.println(boxMessage(PASSWORD_MESSAGE)); break;
		default : break;
		}

		String input = this.scan.nextLine();

		return input;
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(createUserPage);
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return backToMenu();
		}
		
		switch (step) {
		case USERNAME :
			username = input;
			step = PASSWORD;
			return Optional.of(createUserPage);
			
		case PASSWORD :
			password = input;
			try {
				userService.createUser(username, password);
				System.out.println(boxMessage("User successfully created, " + BACK_MENU));
			} catch (FailUserException e) {
				e.printStackTrace();
				System.out.println(boxMessage("[Error] " + M_BACK_MENU));
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
