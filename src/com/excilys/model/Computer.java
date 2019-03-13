package com.excilys.model;

import java.sql.Timestamp;

public class Computer {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;
	
	public Computer(int id, String name, Timestamp introduced, Timestamp discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Computer(int id, String name) {
		this.id = id;
		this.name = name;
		this.introduced = null;
		this.discontinued = null;
		this.company = null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		
		Computer computer = (Computer) obj;
		if (this.id != computer.id) return false;
		
		if (this.name == null && computer.name != null) return false;
		if (this.name != null && computer.name == null) return false;
		if (this.name != null && computer.name != null && !this.name.equals(computer.name)) return false;
		
		if (this.introduced == null && computer.introduced != null) return false;
		if (this.introduced != null && computer.introduced == null) return false;
		if (this.introduced != null && computer.introduced != null && !this.introduced.equals(computer.introduced)) return false;
		
		if (this.discontinued == null && computer.discontinued != null) return false;
		if (this.discontinued != null && computer.discontinued == null) return false;
		if (this.discontinued != null && computer.discontinued != null && !this.introduced.equals(computer.introduced)) return false;
		
		if (this.company == null && computer.company != null) return false;
		if (this.company != null && computer.company == null) return false;
		if (this.company != null && computer.company != null && !this.company.equals(computer.company)) return false;
		
		return true;
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
