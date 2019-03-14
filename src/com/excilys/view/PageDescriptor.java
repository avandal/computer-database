package com.excilys.view;

public enum PageDescriptor {
	LIST_COMPUTER(1, "Show computer list"),
	LIST_COMPANY(2, "Show company list"),
	SHOW_COMPUTER(3, "Show a computer's details"),
	CREATE_COMPUTER(4, "Create a new computer"),
	UPDATE_COMPUTER(5, "Update an existing computer"),
	DELETE_COMPUTER(6, "Delete an existing computer"),
	QUIT(7, "Quit");
	
	private final int id;
	private final String value;
	
	private PageDescriptor(int id, String val) {
		this.id = id;
		this.value = val;
	}
	
	@Override
	public String toString() {
		return this.id+" - "+this.value;
	}
}