package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.control.ControlerComputer;
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.ui.WebPage;
import com.excilys.computer_database.ui.WebPageBuilder;
import com.excilys.computer_database.util.Util;

@WebServlet(name = "Dashboard", urlPatterns = {"/dashboard"})
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LIST_COMP_VIEW = "/resources/views/dashboard.jsp";
	
	public static final String PAGE_SIZE_PARAM = "pageSize";
	public static final String PAGE_INDEX_PARAM = "pageIndex";
	public static final String NB_COMPUTERS = "nbComputers";
	public static final String LIST_COMP_PARAM_FILTERED = "computerListFiltered";
	public static final String WEB_PAGE_PARAM = "webPage";

	private PageSize size;
	private int index;
	
	private WebPage<ComputerDTO> webPage;
	private ComputerService service;
	
	private List<ComputerDTO> list;

	private int extractPageSize(HttpServletRequest request) {
		String pageSizeParam = request.getParameter(PAGE_SIZE_PARAM);
		if (pageSizeParam != null) {
			Optional<Integer> pageSize = Util.parseInt(pageSizeParam);
			
			if (pageSize.isPresent()) {
				size = PageSize.getById(pageSize.get());
			} else {
				size = PageSize.SHOW_10;
			}
		} else {
			size = PageSize.SHOW_10;
		}

		return size.getSize();
	}
	
	private int extractPageIndex(HttpServletRequest request) {
		String pageIndexParam = request.getParameter(PAGE_INDEX_PARAM);
		Optional<Integer> pageIndex = Util.parseInt(pageIndexParam);
		
		if (pageIndexParam != null) {
			if (pageIndex.isPresent()) {
				index = pageIndex.get();
				
				if (index * size.getSize() > list.size()) {
					index = 1;
					System.out.println("index page out of list range");
				}
			} else {
				index = 1;
				System.out.println("Invalid input, set page index to 1");
			}
		} else {
			index = 1;
		}
		return index;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service = ComputerService.getInstance();
		list = service.getAll();
		
		request.setAttribute(NB_COMPUTERS, list.size());
		
		request.setAttribute(PAGE_SIZE_PARAM, extractPageSize(request));
		request.setAttribute(PAGE_INDEX_PARAM, extractPageIndex(request));
		
		webPage = new WebPageBuilder<ComputerDTO>()
				.list(list)
				.index(index)
				.size(size.getSize())
				.build();
		
		request.setAttribute(WEB_PAGE_PARAM, webPage);
		webPage.indexPage().forEach(System.out::println);
		
		this.getServletContext().getRequestDispatcher(LIST_COMP_VIEW).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
