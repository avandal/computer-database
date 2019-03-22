package com.excilys.computer_database.dto;

public class CompanyDTOBuilder {
	private int id;
	private String name;
	
	public CompanyDTOBuilder id(int id) {
		this.id = id;
		return this;
	}
	
	public CompanyDTOBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public CompanyDTOBuilder empty() {
		id = 0;
		name = null;
		return this;
	}
	
	public CompanyDTO build() {
		return new CompanyDTO(id, name);
	}
}
