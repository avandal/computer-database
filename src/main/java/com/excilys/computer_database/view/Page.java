package com.excilys.computer_database.view;

import java.util.Optional;
import java.util.Scanner;

public abstract class Page {
	protected final static String BACK_MENU   = "back to main menu";
	protected final static String M_BACK_MENU = "Back to main menu";
	
	protected Scanner scan = new Scanner(System.in);
	
	public Page() {}
	
	public abstract String show();
	public abstract Optional<Page> exec(String input);
	
	public abstract String toString();
}
