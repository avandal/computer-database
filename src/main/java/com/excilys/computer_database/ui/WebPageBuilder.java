package com.excilys.computer_database.ui;

import java.util.List;

public class WebPageBuilder<T> {
	private List<T> list;
	private int index;
	private int size;
	
	private String url;
	
	public WebPageBuilder<T> list(List<T> list) {
		this.list = list;
		return this;
	}
	
	public WebPageBuilder<T> index(int index) {
		this.index = index;
		return this;
	}
	
	public WebPageBuilder<T> size(int size) {
		this.size = size;
		return this;
	}
	
	public WebPageBuilder<T> url(String url) {
		this.url = url;
		return this;
	}
	
	public WebPage<T> build() {
		return new WebPage<T>(list, index, size, url);
	}
}
