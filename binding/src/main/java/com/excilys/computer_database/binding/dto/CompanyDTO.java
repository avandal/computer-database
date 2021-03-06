package com.excilys.computer_database.binding.dto;

import java.util.Objects;

public class CompanyDTO {
	private String id;
	private String name;
	
	public CompanyDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.id + " - " + this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
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
		CompanyDTO other = (CompanyDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
