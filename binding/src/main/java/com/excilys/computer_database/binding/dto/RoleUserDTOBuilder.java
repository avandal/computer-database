package com.excilys.computer_database.binding.dto;

public class RoleUserDTOBuilder {
	private String username;
	private String password;
	private String enabled;
	private String role;
	
	public RoleUserDTOBuilder() {}
	
	public RoleUserDTOBuilder username(String username) {
		this.username = username;
		return this;
	}
	
	public RoleUserDTOBuilder password(String password) {
		this.password = password;
		return this;
	}
	
	public RoleUserDTOBuilder enabled(String enabled) {
		this.enabled = enabled;
		return this;
	}
	
	public RoleUserDTOBuilder role(String role) {
		this.role = role;
		return this;
	}
	
	public RoleUserDTO build() {
		return new RoleUserDTO(username, password, enabled, role);
	}
}
