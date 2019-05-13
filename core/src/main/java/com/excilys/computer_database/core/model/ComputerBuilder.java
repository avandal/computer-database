package com.excilys.computer_database.core.model;

import java.sql.Timestamp;
import java.util.Random;

public class ComputerBuilder {
	private Random random = new Random();
	
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
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < 8; i++) {
			builder.append((char)(97 + random.nextInt('z' - 'a')));
		}
		return builder.toString();
	}
	
	private Timestamp randomTime() {
		int year = 1990 + random.nextInt(2019 - 1990);
		int month = 1 + random.nextInt(12 - 1);
		int day = 1 + random.nextInt(28 - 1);
		
		int hour = random.nextInt(24);
		int minute = random.nextInt(60);
		int second = random.nextInt(60);
		
		String time = String.format("%d-%d-%d %d:%d:%d", year, month, day, hour, minute, second);
		
		return Timestamp.valueOf(time);
	}
	
	public ComputerBuilder random() {
		this.id = random.nextInt(1000) + 1;
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
