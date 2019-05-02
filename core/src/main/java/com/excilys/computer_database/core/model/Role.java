package com.excilys.computer_database.core.model;

public enum Role {
	ADMIN("admin"), GUEST("guest");
	
	private String role;
	
	Role(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
