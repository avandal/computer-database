package com.excilys.computer_database.binding.dto;

import java.util.Objects;

public class ComputerDTO {
	
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	
	private String companyId;
	private String companyName;
	
	public ComputerDTO() {}
	
	public ComputerDTO(String id, String name, String introduced, String discontinued, String companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		
		this.companyId = companyId;
		this.companyName = companyName;
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String toString() {
		return id+" - "+name+", "+introduced+", "+discontinued+", [" + companyId + " - " + companyName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyId, companyName, discontinued, id, introduced, name);
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
		ComputerDTO other = (ComputerDTO) obj;
		return Objects.equals(companyId, other.companyId) && Objects.equals(companyName, other.companyName)
				&& Objects.equals(discontinued, other.discontinued) && Objects.equals(id, other.id)
				&& Objects.equals(introduced, other.introduced) && Objects.equals(name, other.name);
	}
}
