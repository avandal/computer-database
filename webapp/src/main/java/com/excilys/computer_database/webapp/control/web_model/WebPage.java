package com.excilys.computer_database.webapp.control.web_model;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.core.model.SortMode;
import com.excilys.computer_database.webapp.control.DashboardController;

public class WebPage<T> {
	private List<T> list;
	private int index;
	private int size;
	private String search;
	private String order;
	
	private String url;
	
	private Logger logger = LoggerFactory.getLogger(WebPage.class);
	
	private void fixSize(Optional<Integer> size) {
		if (size.isPresent()) {
			this.size = PageSize.getById(size.get()).getSize();
		} else {
			logger.warn("extractPageSize - Undefined pageSize input, set to default");
			this.size = PageSize.SHOW_10.getSize();
		}
	}
	
	private void fixIndex(Optional<Integer> optIndex) {
		if (optIndex.isPresent()) {
			index = optIndex.get();
			
			if ((index - 1) * size > list.size() || index <= 0) {
				logger.warn("extractPageIndex - Trying to get an invalid page (index out of bound), set to default 1");
				index = 1;
			}
		} else {
			logger.warn("extractPageIndex - Undefined pageIndex input, set to default 1");
			index = 1;
		}
	}
	
	public WebPage(List<T> list, Optional<Integer> size, Optional<Integer> index, String url, String search, String order) {
		this.list = list;
		fixSize(size);
		fixIndex(index);
		this.search = search == null ? "" : search;
		this.order = order;
		
		this.url = url;
	}
	
	private String formatUrl(int index, int size, String search, String order) {
		return String.format("%s?%s=%d&%s=%d&%s=%s&%s=%s", url, 
				DashboardController.PAGE_INDEX_PARAM, index, 
				DashboardController.PAGE_SIZE_PARAM, size, 
				DashboardController.SEARCH_PARAM, search,
				DashboardController.ORDER_PARAM, order);
	}
	
	public String previousPage() {
		int newPage = (index > 1) ? index - 1 : 1;
		return formatUrl(newPage, size, search, order);
	}
	
	public String nextPage() {		
		int newPage = (index * size < list.size()) ? index + 1 : index;
		return formatUrl(newPage, size, search, order);
	}
	
	public String indexAt(int index) {
		return formatUrl(index, size, search, order);
	}
	
	public String setPageSize(int size) {
		return formatUrl(1, size, search, order);
	}
	
	public PageSize[] sizes() {
		return PageSize.values();
	}
	
	public int getPageCount() {
		return (int) Math.ceil((double)list.size() / size);
	}
	
	public int getFirstIndex() {
		int pageCount = getPageCount();
		
		if (index <= 3) {
			return 1;
		}
		
		if ((index + 2) <= pageCount) {
			return index - 2;
		}
		
		return (Math.max(pageCount - 4, 1));
	}
	
	public int getLastIndex() {
		int s = list.size();
		
		if (index <= 3) {
			return Math.min(getPageCount(), 5);
		}
		
		for (int i = 1; i >= 0; i--) {
			if (s > (index + i) * size) {
				return index + (i + 1);
			}
		}
		
		return index;
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
	
	public String orderByNameAsc() {
		return order(SortMode.NAME_ASC.value());
	}
	public String orderByNameDesc() {
		return order(SortMode.NAME_DESC.value());
	}
	public String orderByIntroAsc() {
		return order(SortMode.INTRO_ASC.value());
	}
	public String orderByIntroDesc() {
		return order(SortMode.INTRO_DESC.value());
	}
	public String orderByDiscAsc() {
		return order(SortMode.DISC_ASC.value());
	}
	public String orderByDiscDesc() {
		return order(SortMode.DISC_DESC.value());
	}
	public String orderByCompAsc() {
		return order(SortMode.COMP_ASC.value());
	}
	public String orderByCompDesc() {
		return order(SortMode.COMP_DESC.value());
	}
	
	public String order(String mode) {
		return formatUrl(index, size, search, mode);
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getIndex() {
		return this.index;
	}
}
