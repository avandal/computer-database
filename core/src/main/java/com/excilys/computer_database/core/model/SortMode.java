package com.excilys.computer_database.core.model;

public enum SortMode {
	NAME_ASC("name_asc", "order by ct.name asc"), 
	NAME_DESC("name_desc", "order by ct.name desc"), 
	
	INTRO_ASC("intro_asc", "order by ct.introduced asc nulls last"), 
	INTRO_DESC("intro_desc", "order by ct.introduced desc nulls last"), 
	
	DISC_ASC("disc_asc", "order by ct.discontinued asc nulls last"),
	DISC_DESC("disc_desc", "order by ct.discontinued desc nulls last"),
	
	COMP_ASC("comp_asc", "order by cn.name asc nulls last"),
	COMP_DESC("comp_desc", "order by cn.name desc nulls last"),
	
	DEFAULT("", "order by ct.id asc");
	
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
