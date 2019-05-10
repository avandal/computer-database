package com.excilys.computer_database.core.model;

import java.util.Random;

public class CompanyBuilder {
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
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			s.append((char)(97 + Math.random() * (122 - 97)));
		}
		return s.toString();
	}
	
	public CompanyBuilder random() {
		this.id = new Random().nextInt(1000) + 1;
		this.name = randomChain();
		return this;
	}
	
	public Company build() {
		return new Company(id, name);
	}

}
