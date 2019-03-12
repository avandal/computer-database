package com.excilys.model;

public class Company {
	private Integer id;
	private String name;
	
	public Company(int id, String name) {
		this.id = (id <= 0 ? null : id);
		this.name = name;
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
