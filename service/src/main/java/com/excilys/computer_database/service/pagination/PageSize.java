package com.excilys.computer_database.service.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PageSize {
	SHOW_5("5"), SHOW_10("10"), SHOW_20("20"), SHOW_50("50"), SHOW_100("100"), SHOW_ALL("all");
	
	private final String size;
	private static Logger logger = LoggerFactory.getLogger(PageSize.class);
	
	PageSize(String size) {
		this.size = size;
	}
	
	public String getSize() {
		return size;
	}
	
	public String toString() {
		return "Showing " + size + " per page";
	}
	
	public static PageSize getById(String index) {
		if (index == null) {
			logger.warn("getById - null input, set to 10");
			return SHOW_10;
		}
		
		for (PageSize page : values()) {
			if (page.size.equals(index)) {
				return page;
			}
		}
		
		logger.warn("getById - input isn't valid, set to 10");
		return SHOW_10;
	}
}
