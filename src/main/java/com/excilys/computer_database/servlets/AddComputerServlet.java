package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.AppConfig;
import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.CompanyDTOBuilder;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.exception.FailComputerException;

@WebServlet(name = "AddComputer", urlPatterns = {"/addComputer"})
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ADD_COMP_VIEW = "/resources/views/addComputer.jsp";
	
	private static final String LIST_COMP_PARAM = "companyList";
	private static final String STATUS_CREATE_PARAM = "status";
	
	private static final String NAME_PARAM = "computerName";
	private static final String INTR_PARAM = "introduced";
	private static final String DISC_PARAM = "discontinued";
	private static final String COMP_PARAM = "companyId";
	
	private static final String ERROR_NAME = "errorName";
	private static final String ERROR_INTR = "errorIntroduced";
	private static final String ERROR_DISC = "errorDiscontinued";
	private static final String ERROR_COMP = "errorCompany";
	
	private ComputerService computerService;
	private CompanyService companyService;
	
	public AddComputerServlet() {
		computerService = AppConfig.context.getBean(ComputerService.class);
		companyService = AppConfig.context.getBean(CompanyService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		request.setAttribute(LIST_COMP_PARAM, companies);
		
		this.getServletContext().getRequestDispatcher(ADD_COMP_VIEW).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter(NAME_PARAM);
		String introduced = request.getParameter(INTR_PARAM);
		String discontinued = request.getParameter(DISC_PARAM);
		String company = request.getParameter(COMP_PARAM);
		
		request.setAttribute(NAME_PARAM, name);
		request.setAttribute(INTR_PARAM, introduced);
		request.setAttribute(DISC_PARAM, discontinued);
		request.setAttribute(COMP_PARAM, company);
		
		try {
			int status = computerService.createComputer(name, introduced, discontinued, company);
			if (status == -1) {
				System.out.println("Error creating");
				request.setAttribute(STATUS_CREATE_PARAM, "failed");
			} else {
				request.setAttribute(STATUS_CREATE_PARAM, "success");
			}
			
			response.sendRedirect("dashboard");
		} catch (FailComputerException e) {
			switch (e.getConcerned()) {
			case NAME : request.setAttribute(ERROR_NAME, e.getReason());break;
			case INTRODUCED: request.setAttribute(ERROR_INTR, e.getReason());break;
			case DISCONTINUED : request.setAttribute(ERROR_DISC, e.getReason());break;
			case COMPANY : request.setAttribute(ERROR_COMP, e.getReason());break;
			default : break;
			}
			request.setAttribute(STATUS_CREATE_PARAM, "failed");
			e.printStackTrace();
			
			doGet(request, response);
		}
	}
}
