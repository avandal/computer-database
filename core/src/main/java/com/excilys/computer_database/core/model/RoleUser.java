package com.excilys.computer_database.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class RoleUser implements Serializable {
	private final static long serialVersionUID = 1L;

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private User user;
	
	@Column(name = "role")
	private String role;
	
	public RoleUser() {}

	public RoleUser(User user, String role) {
		this.user = user;
		this.role = role;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(role, user);
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
		RoleUser other = (RoleUser) obj;
		return Objects.equals(role, other.role) && Objects.equals(user, other.user);
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [role=" + role + ", user=" + user + "]";
	}
}
