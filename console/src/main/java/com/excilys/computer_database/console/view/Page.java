package com.excilys.computer_database.console.view;

import java.util.Optional;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public abstract class Page {
	protected static final String BACK_MENU   = "back to main menu";
	protected static final String M_BACK_MENU = "Back to main menu";
	
	protected static final String NULL = "'null' to set null";
	protected static final String ABORT = "'abort' to abort";
	protected static final String NULL_ABORT = ABORT + ", " + NULL;
	
	protected static final String URL_API = "http://localhost:8080/webapp/api";
	
	protected Scanner scan = new Scanner(System.in);
	
	protected Client client;
	
	public Page() {
		this.client = ClientBuilder.newClient();
	}
	
	protected abstract Optional<Page> backToMenu();
	
	public abstract String show();
	public abstract Optional<Page> exec(String input);
	
	public abstract String toString();
}
