package com.excilys.computer_database.binding.dto;

public class CompanyDTOBuilder {
	private String id;
	private String name;
	
	public CompanyDTOBuilder id(String id) {
		this.id = id;
		return this;
	}
	
	public CompanyDTOBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public CompanyDTOBuilder empty() {
		id = "0";
		name = null;
		return this;
	}
	
	public CompanyDTO build() {
		return new CompanyDTO(id, name);
	}
}
