package com.excilys.computer_database.core.model;

import java.util.Random;

public class CompanyBuilder {
	private Random random = new Random();
	private int id;
	private String name;

	public CompanyBuilder() {}
	
	public CompanyBuilder id(int id) {
		this.id = id;
		return this;
	}
	
	public CompanyBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public CompanyBuilder empty() {
		this.id = 0;
		this.name = null;
		return this;
	}
	
	private String randomChain() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < 8; i++) {
			builder.append((char)(97 + random.nextInt('z' - 'a')));
		}
		return builder.toString();
	}
	
	public CompanyBuilder random() {
		this.id = random.nextInt(1000) + 1;
		this.name = randomChain();
		return this;
	}
	
	public Company build() {
		return new Company(id, name);
	}

}
