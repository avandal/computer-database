package com.excilys.computer_database.service.exception;

public enum ConcernedField {
	NAME("name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company");
	
	private String value;
	
	ConcernedField(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
}
