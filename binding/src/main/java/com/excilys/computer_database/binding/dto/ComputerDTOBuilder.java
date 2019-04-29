package com.excilys.computer_database.binding.dto;

public class ComputerDTOBuilder {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	
	private String companyId;
	private String companyName;
	
	public ComputerDTOBuilder id(String id) {
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
	
	public ComputerDTOBuilder companyId(String companyId) {
		this.companyId = companyId;
		return this;
	}
	
	public ComputerDTOBuilder companyName(String companyName) {
		this.companyName = companyName;
		return this;
	}
	
	public ComputerDTOBuilder empty() {
		id = null;
		name = null;
		introduced = null;
		discontinued = null;
		companyId = null;
		companyName = null;
		return this;
	}
	
	public ComputerDTO build() {
		return new ComputerDTO(id, name, introduced, discontinued, companyId, companyName);
	}
}
