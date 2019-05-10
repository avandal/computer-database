package com.excilys.computer_database.console.view;

import java.util.Optional;
import java.util.Scanner;

public abstract class Page {
	protected final static String BACK_MENU   = "back to main menu";
	protected final static String M_BACK_MENU = "Back to main menu";
	
	protected final static String NULL = "'null' to set null";
	protected final static String ABORT = "'abort' to abort";
	protected final static String NULL_ABORT = ABORT + ", " + NULL;
	
	protected Scanner scan = new Scanner(System.in);
	
	public Page() {}
	
	protected abstract Optional<Page> backToMenu();
	
	public abstract String show();
	public abstract Optional<Page> exec(String input);
	
	public abstract String toString();
}
