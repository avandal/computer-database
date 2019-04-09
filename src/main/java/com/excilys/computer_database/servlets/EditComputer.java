package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.App;
import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.CompanyDTOBuilder;
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.exception.FailComputerException;
import com.excilys.computer_database.util.Util;

@WebServlet(name = "EditComputer", urlPatterns = {"/editComputer"})
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String EDIT_COMP_VIEW = "/resources/views/editComputer.jsp";
	
	private static final String LIST_COMP_PARAM = "companyList";
	
	private static final String COMPUTER_ID_PARAM = "computerId";
	
	private static final String NAME_PARAM = "computerName";
	private static final String INTR_PARAM = "introduced";
	private static final String DISC_PARAM = "discontinued";
	private static final String COMP_PARAM = "companyId";
	
	private static final String ORIGINAL_NAME_PARAM = "originalComputerName";
	private static final String ORIGINAL_INTR_PARAM = "originalIntroduced";
	private static final String ORIGINAL_DISC_PARAM = "originalDiscontinued";
	private static final String ORIGINAL_COMP_ID_PARAM = "originalCompanyId";
	private static final String ORIGINAL_COMP_NAME_PARAM = "originalCompanyName";
	
	private static final String ERROR_NAME = "errorName";
	private static final String ERROR_INTR = "errorIntroduced";
	private static final String ERROR_DISC = "errorDiscontinued";
	private static final String ERROR_COMP = "errorCompany";
	
	private static Logger logger = LoggerFactory.getLogger(EditComputer.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService computerService = ComputerService.getInstance(App.DATASOURCE);
		CompanyService companyService = CompanyService.getInstance(App.DATASOURCE);
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		request.setAttribute(LIST_COMP_PARAM, companies);
		
		String computerId = request.getParameter(COMPUTER_ID_PARAM);
		Optional<Integer> id = Util.parseInt(computerId);
		if (id.isPresent()) {
			Optional<ComputerDTO> optComputer = computerService.getComputerDetails(id.get());
			if (optComputer.isPresent()) {
				request.setAttribute(COMPUTER_ID_PARAM, id.get());
				ComputerDTO computer = optComputer.get();
				
				request.setAttribute(ORIGINAL_NAME_PARAM, computer.getName());
				request.setAttribute(ORIGINAL_INTR_PARAM, computer.getIntroduced());
				request.setAttribute(ORIGINAL_DISC_PARAM, computer.getDiscontinued());
				request.setAttribute(ORIGINAL_COMP_ID_PARAM, computer.getCompanyId());
				request.setAttribute(ORIGINAL_COMP_NAME_PARAM, computer.getCompanyName() == null ? "No one" : computer.getCompanyName());
				
				this.getServletContext().getRequestDispatcher(EDIT_COMP_VIEW).forward(request, response);
				return;
			}
		}
		
		response.sendRedirect("dashboard");
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
		
		Optional<Integer> optId = Util.parseInt(request.getParameter(COMPUTER_ID_PARAM));
		if (!optId.isPresent()) {
			logger.warn("doPost - invalid computer ID");
			response.sendRedirect("dashboard");
			return;
		}
		
		int computerId = optId.get();
		
		ComputerService service = ComputerService.getInstance(App.DATASOURCE);
		try {
			service.updateComputer(computerId, name, introduced, discontinued, company);
			
			response.sendRedirect("dashboard");
		} catch (FailComputerException e) {
			switch (e.getConcerned()) {
			case NAME : request.setAttribute(ERROR_NAME, e.getReason());break;
			case INTRODUCED: request.setAttribute(ERROR_INTR, e.getReason());break;
			case DISCONTINUED : request.setAttribute(ERROR_DISC, e.getReason());break;
			case COMPANY : request.setAttribute(ERROR_COMP, e.getReason());break;
			default : break;
			}
			e.printStackTrace();
			
			doGet(request, response);
		}
	}
}
