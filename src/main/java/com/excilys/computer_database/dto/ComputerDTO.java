package com.excilys.computer_database.dto;

import java.sql.Timestamp;

import com.excilys.computer_database.model.Company;

public class ComputerDTO {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;
	
	public ComputerDTO(int id, String name, Timestamp introduced, Timestamp discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String toString() {
		return id+" - "+name+", "+introduced+", "+discontinued+", ["+company+"]";
	}
}
