package com.excilys.computer_database.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PageSize {
	SHOW_5(5), SHOW_10(10), SHOW_20(20), SHOW_50(50), SHOW_100(100);
	
	private final int size;
	private static Logger logger = LoggerFactory.getLogger(PageSize.class);
	
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
			logger.warn("getById - null input, set to 10");
			return SHOW_10;
		}
		
		for (PageSize page : values()) {
			if (page.size == index) {
				return page;
			}
		}
		
		logger.warn("getById - input isn't valid, set to 10");
		return SHOW_10;
	}
}
