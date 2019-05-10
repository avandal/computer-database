package com.excilys.computer_database.core.model;

public enum Role {
	ADMIN("admin"), GUEST("guest");
	
	private String value;
	
	Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
