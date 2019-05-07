package com.excilys.computer_database.console.view;

import java.util.Optional;

public enum MenuDescriptor {
	LIST_COMPUTER(1, "Show computer list"),
	LIST_COMPANY(2, "Show company list"),
	SHOW_COMPUTER(3, "Show a computer's details"),
	CREATE_COMPUTER(4, "Create a new computer"),
	UPDATE_COMPUTER(5, "Update an existing computer"),
	DELETE_COMPUTER(6, "Delete an existing computer"),
	DELETE_COMPANY(7, "Delete an existing company"),
	CREATE_USER(8, "Create an user"),
	QUIT(9, "Quit");
	
	private int id;
	private String value;
	
	private MenuDescriptor(int id, String val) {
		this.id = id;
		this.value = val;
	}
	
	public int getId() {
		return id;
	}
	
	public static Optional<MenuDescriptor> getById(int id) {
		for (MenuDescriptor page : values()) {
			if (page.id == id) {
				return Optional.of(page);
			}
		}
		return Optional.empty();
	}
	
	@Override
	public String toString() {
		return this.id+" - "+this.value;
	}
}
