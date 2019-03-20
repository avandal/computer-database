package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.control.ControlerComputer;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.util.Util;

public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LIST_COMP_VIEW = "/resourcesviews/dashboard.jsp";
	
	public static final String PAGE_SIZE_PARAM = "pageSize";
	public static final String PAGE_INDEX_PARAM = "pageIndex";
	
	public static final String LIST_COMP_PARAM = "computerList";

	private PageSize size;
	private int index;
	
	private ControlerComputer controler;
	private List<Computer> list;

	private void extractPageSize(HttpServletRequest request) {
		String pageSizeParam = request.getParameter(PAGE_SIZE_PARAM);
		Optional<Integer> pageSize = Util.parseInt(pageSizeParam);
		
		if (pageSize.isPresent()) {
			size = PageSize.getById(pageSize.get());
			
			if (size.getSize() != pageSize.get()) {
				System.out.println("Changing the page size");
				request.setAttribute(PAGE_SIZE_PARAM, size.getSize());
			}
		} else {
			size = PageSize.SHOW_10;
			System.out.println("Invalid input, set page size to 10");
			request.setAttribute(PAGE_SIZE_PARAM, size.getSize());
		}
	}
	
	private void extractPageIndex(HttpServletRequest request) {
		String pageIndexParam = request.getParameter(PAGE_INDEX_PARAM);
		Optional<Integer> pageIndex = Util.parseInt(pageIndexParam);
		
		if (pageIndex.isPresent()) {
			index = pageIndex.get();
			
			if (index * size.getSize() > list.size()) {
				System.out.println("index page out of list range");
				request.setAttribute(PAGE_INDEX_PARAM, 1);
			}
		} else {
			System.out.println("Invalid input, set page index to 1");
			request.setAttribute(PAGE_INDEX_PARAM, 1);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controler = ControlerComputer.getInstance();
		
		list = controler.getAll();
		request.setAttribute(LIST_COMP_PARAM, list);
		
		extractPageSize(request);
		extractPageIndex(request);
		
		this.getServletContext().getRequestDispatcher(LIST_COMP_VIEW).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
