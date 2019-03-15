package com.excilys.computer_database.model;

public class Company {
	private int id;
	private String name;
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		
		Company company = (Company) obj;
		
		if (this.id != company.id) return false;
		
		if (this.name == null && company.name != null) return false;
		if (this.name != null && company.name != null) return false;
		if (this.name != null && company.name != null && !this.name.equals(company.name)) return false;
		
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
	
	public String toString() {
		return id+" - "+name;
	}
}
