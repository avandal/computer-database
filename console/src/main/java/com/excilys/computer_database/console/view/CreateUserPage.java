package com.excilys.computer_database.console.view;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.service.service.UserService;
import com.excilys.computer_database.service.service.exception.FailUserException;

@Component
public class CreateUserPage extends Page {
	private static final int USERNAME = 1;
	private static final int PASSWORD = 2;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CreateUserPage createUserPage;
	
	@Autowired
	private MenuPage menuPage;
	
	private int step = 1;
	
	private String username;
	private String password;

	@Override
	public String show() {
		switch (step) {
		case USERNAME : System.out.println(boxMessage("Please give an username ('abort' to abort')")); break;
		case PASSWORD : System.out.println(boxMessage("Please give a password ('abort' to abort')")); break;
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
			return Optional.of(menuPage);
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
				return Optional.of(menuPage);
			} catch (FailUserException e) {
				System.out.println(boxMessage("[Error] " + BACK_MENU));
				return Optional.of(menuPage);
			}
			
		default :
			return Optional.of(menuPage);
		}
	}

	@Override
	public String toString() {
		return "Create user";
	}

}
