package com.excilys.computer_database.model;

import java.sql.Timestamp;
import java.util.Random;

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
	
	public ComputerBuilder empty() {
		this.id = 0;
		this.name = null;
		this.introduced = null;
		this.discontinued = null;
		this.company = null;
		return this;
	}
	
	private String randomChain() {
		String s = "";
		for (int i = 0; i < 8; i++) {
			s += (char)(97 + Math.random() * (122 - 97));
		}
		return s;
	}
	
	private Timestamp randomTime() {
		int year = 1990 + (int)(Math.random() * (2019 - 1990));
		int month = 1 + (int)(Math.random() * (12 - 1));
		int day = 1 + (int)(Math.random() * (28 - 1));
		
		int hour = (int)(Math.random() * 24);
		int minute = (int)(Math.random() * 60);
		int second = (int)(Math.random() * 60);
		
		String time = String.format("%d-%d-%d %d:%d:%d", year, month, day, hour, minute, second);
		
		return Timestamp.valueOf(time);
	}
	
	public ComputerBuilder random() {
		this.id = new Random().nextInt(1000) + 1;
		this.name = randomChain();
		this.introduced = randomTime();
		this.discontinued = randomTime();
		this.company = new CompanyBuilder().random().build();
		return this;
	}
	
	public Computer build() {
		return new Computer(id, name, introduced, discontinued, company);
	}

}
