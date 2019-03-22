package com.excilys.computer_database.ui;

import java.util.List;

import com.excilys.computer_database.servlets.Dashboard;

public class WebPage<T> {
	private List<T> list;
	private int index;
	private int size;
	
	public WebPage(List<T> list, int index, int size) {
		this.list = list;
		this.index = index;
		this.size = size;
	}
	
	private String formatUrl(String url, int index, int size) {
		return String.format("%s?%s=%d&%s=%d", url, Dashboard.PAGE_INDEX_PARAM, index, Dashboard.PAGE_SIZE_PARAM, size);
	}
	
	public String previousPage(String url) {
		int newPage = (index > 1) ? index - 1 : 1;
		return formatUrl(url, newPage, size);
	}
	
	public String nextPage(String url) {
		int newPage = ((index + 1) * size <= list.size()) ? index + 1 : index;
		return formatUrl(url, newPage, size);
	}
	
	public String indexAt(String url, int index) {
		return formatUrl(url, index, size);
	}
	
	public List<T> indexPage() {
		return list.subList((index - 1) * size, Math.min(list.size(), index * size));
	}
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
