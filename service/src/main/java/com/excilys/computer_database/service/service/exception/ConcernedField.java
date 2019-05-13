package com.excilys.computer_database.service.service.exception;

public enum ConcernedField {
	// FailComputerException
	ID("id"), NAME("name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company"),
	// FailUserException
	USERNAME("username"), PASSWORD("password");
	
	private String value;
	
	ConcernedField(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
