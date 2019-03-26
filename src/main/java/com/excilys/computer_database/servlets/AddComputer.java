package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.CompanyDTOBuilder;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.exception.FailCreateException;

@WebServlet(name = "AddComputer", urlPatterns = {"/addComputer"})
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ADD_COMP_VIEW = "/resources/views/addComputer.jsp";
//	private static final String DASHBOARD_VIEW = "/resources/views/dashboard.jsp";
	
	private static final String LIST_COMP_PARAM = "companyList";
	private static final String STATUS_CREATE_PARAM = "status";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyService service = CompanyService.getInstance();
		List<CompanyDTO> companies = service.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		request.setAttribute(LIST_COMP_PARAM, companies);
		
		if (request.getAttribute(STATUS_CREATE_PARAM) == null) {
			request.setAttribute(STATUS_CREATE_PARAM, "void");
		}
		
		this.getServletContext().getRequestDispatcher(ADD_COMP_VIEW).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company = request.getParameter("companyId");
		
		ComputerService service = ComputerService.getInstance();
		try {
			int status = service.createComputer(name, introduced, discontinued, company);
			if (status == -1) {
				System.out.println("Error creating");
				request.setAttribute(STATUS_CREATE_PARAM, "failed");
			} else {
				request.setAttribute(STATUS_CREATE_PARAM, "success");
			}
		} catch (FailCreateException e) {
			request.setAttribute(STATUS_CREATE_PARAM, "failed");
			e.printStackTrace();
		}
		
		doGet(request, response);
	}
}
