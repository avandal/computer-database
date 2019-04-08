package com.excilys.computer_database.servlets;

public enum SortMode {
	NAME_ASC("name_asc", "order by ct.name asc"), 
	NAME_DESC("name_desc", "order by ct.name desc"), 
	
	INTRO_ASC("intro_asc", "order by ct.introduced asc"), 
	INTRO_DESC("intro_desc", "order by ct.introduced desc"), 
	
	DISC_ASC("disc_asc", "order by ct.discontinued asc"),
	DISC_DESC("disc_desc", "order by ct.discontinued desc"),
	
	COMP_ASC("comp_asc", "order by cn.name asc"),
	COMP_DESC("comp_desc", "order by cn.name desc"),
	
	DEFAULT("", "");
	
	private String value;
	private String suffix;
	
	SortMode(String value, String suffix) {
		this.value = value;
		this.suffix = suffix;
	}
	
	public String suffix() {
		return suffix;
	}
	
	public String value() {
		return value;
	}
	
	public static SortMode getByValue(String value) {
		if (value != null) {
			for (SortMode mode : values()) {
				if (value.equals(mode.value)) {
					return mode;
				}
			}
		}
		
		return DEFAULT;
	}
}
