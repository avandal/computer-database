package com.excilys.computer_database.service.service.exception;

public enum ConcernedField {
	ID("id"), NAME("name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company");
	
	private String value;
	
	ConcernedField(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
}