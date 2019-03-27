package com.excilys.computer_database.ui;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.servlets.Dashboard;
import com.excilys.computer_database.servlets.PageSize;

public class WebPage<T> {
	private List<T> list;
	private int index;
	private int size;
	
	private String url;
	
	private static Logger logger = LoggerFactory.getLogger(WebPage.class);
	
	public WebPage(List<T> list, int index, int size, String url) {
		this.list = list;
		this.index = index;
		this.size = size;
		
		this.url = url;
	}
	
	private String formatUrl(int index, int size) {
		return String.format("%s?%s=%d&%s=%d", url, Dashboard.PAGE_INDEX_PARAM, index, Dashboard.PAGE_SIZE_PARAM, size);
	}
	
	public String previousPage() {
		int newPage = (index > 1) ? index - 1 : 1;
		return formatUrl(newPage, size);
	}
	
	public String nextPage() {		
		int newPage = (index * size < list.size()) ? index + 1 : index;
		return formatUrl(newPage, size);
	}
	
	public String indexAt(int index) {
		return formatUrl(index, size);
	}
	
	public String setPageSize(int size) {
		return formatUrl(1, size);
	}
	
	public PageSize[] sizes() {
		return PageSize.values();
	}
	
	public int getFirstIndex() {
		int s = list.size();
		if (index - 2 <= 1) {
			logger.warn("getFirstIndex - trying access to invalid index (< 1), set to 1");
			return 1;
		}
		if ((index + 1) * size < s) {
			return index - 2;
		}
		logger.warn("getFirstIndex - Trying access to invalid index (> max), set to max");
		return s / size - 3 - (s % size == 0 ? 1 : 0);
	}
	
	public int firstId() {
		return (index - 1) * size + 1;
	}
	
	public int lastId() {
		return Math.min(list.size(), index * size);
	}
	
	public List<T> indexPage() {
		return list.subList((index - 1) * size, lastId());
	}
	
	
}
