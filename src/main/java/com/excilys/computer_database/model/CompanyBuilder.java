package com.excilys.computer_database.model;

public class CompanyBuilder {
	private int id;
	private String name;

	public CompanyBuilder() {}
	
	public CompanyBuilder id(int id) {
		this.id = id;
		return this;
	}
	
	public CompanyBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public CompanyBuilder empty() {
		this.id = 0;
		this.name = null;
		return this;
	}
	
	public Company build() {
		return new Company(id, name);
	}

}
