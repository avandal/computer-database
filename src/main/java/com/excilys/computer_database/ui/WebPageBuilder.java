package com.excilys.computer_database.ui;

import java.util.List;
import java.util.Optional;

public class WebPageBuilder<T> {
	private List<T> list;
	private Optional<Integer> size;
	private Optional<Integer> index;
	private String search;
	private String order;
	
	private String url;
	
	public WebPageBuilder<T> list(List<T> list) {
		this.list = list;
		return this;
	}
	
	public WebPageBuilder<T> size(Optional<Integer> size) {
		this.size = size;
		return this;
	}
	
	public WebPageBuilder<T> index(Optional<Integer> index) {
		this.index = index;
		return this;
	}
	
	public WebPageBuilder<T> search(String search) {
		this.search = search;
		return this;
	}
	
	public WebPageBuilder<T> order(String order) {
		this.order = order;
		return this;
	}
	
	public WebPageBuilder<T> url(String url) {
		this.url = url;
		return this;
	}
	
	public WebPage<T> build() {
		return new WebPage<T>(list, size, index, url, search, order);
	}
}
