package com.excilys.computer_database.model;

import java.sql.Timestamp;

public class ComputerBuilder {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;

	public ComputerBuilder() {}
	
	public ComputerBuilder id(int id) {
		this.id = id;
		return this;
	}
	
	public ComputerBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public ComputerBuilder introduced(Timestamp introduced) {
		this.introduced = introduced;
		return this;
	}
	
	public ComputerBuilder discontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
		return this;
	}
	
	public ComputerBuilder company(Company company) {
		this.company = company;
		return this;
	}
	
	public Computer build() {
		return new Computer(id, name, introduced, discontinued, company);
	}

}
