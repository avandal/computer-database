package com.excilys.computer_database.binding.dto;

import java.util.Objects;

public class RoleUserDTO {
	private String username;
	private String password;
	private String enabled;
	private String role;
	
	public RoleUserDTO(String username, String password, String enabled, String role) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(enabled, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RoleUserDTO other = (RoleUserDTO) obj;
		return Objects.equals(enabled, other.enabled) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + ", enabled=" + enabled + ", role=" + role
				+ "]";
	}	
}
