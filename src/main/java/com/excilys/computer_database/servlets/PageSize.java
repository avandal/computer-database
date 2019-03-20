package com.excilys.computer_database.servlets;

public enum PageSize {
	SHOW_5(5), SHOW_10(10), SHOW_20(20), SHOW_50(50), SHOW_100(100);
	
	private final int size;
	
	PageSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public String toString() {
		return "Showing " + size + " per page";
	}
	
	public static PageSize getById(Integer index) {
		if (index == null) {
			return SHOW_10;
		}
		
		for (PageSize page : values()) {
			if (page.size == index) {
				return page;
			}
		}
		
		return SHOW_10;
	}
}
