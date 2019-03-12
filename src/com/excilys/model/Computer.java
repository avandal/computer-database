package com.excilys.model;

import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
	public Computer(int id, String name, Date introduced, Date discontinued, Company company) {
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
	
	public String toString() {
		return id+" - "+name+", "+introduced+", "+discontinued+", ["+company+"]";
	}
}
