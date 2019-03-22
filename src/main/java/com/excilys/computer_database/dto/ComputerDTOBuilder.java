package com.excilys.computer_database.dto;

import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;

public class ComputerDTOBuilder {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	
	private int companyId;
	private String companyName;
	
	public ComputerDTOBuilder id(int id) {
		this.id = id;
		return this;
	}
	
	public ComputerDTOBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public ComputerDTOBuilder introduced(String introduced) {
		this.introduced = introduced;
		return this;
	}
	
	public ComputerDTOBuilder discontinued(String discontinued) {
		this.discontinued = discontinued;
		return this;
	}
	
	public ComputerDTOBuilder companyId(int companyId) {
		this.companyId = companyId;
		return this;
	}
	
	public ComputerDTOBuilder companyName(String companyName) {
		this.companyName = companyName;
		return this;
	}
	
	public ComputerDTOBuilder empty() {
		id = 0;
		name = null;
		introduced = null;
		discontinued = null;
		companyId = 0;
		companyName = null;
		return this;
	}
	
	public ComputerDTO build() {
		return new ComputerDTO(id, name, introduced, discontinued, companyId, companyName);
	}
}
