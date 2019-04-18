package com.excilys.computer_database.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.CompanyDTOBuilder;
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.dto.ComputerDTOBuilder;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.exception.FailComputerException;

@Controller
public class AddComputerController {
	
	static final String VIEW = "addComputer";
	static final String REDIRECT_DASHBOARD = "redirect:" + DashboardController.VIEW;
	
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
	
	private Logger logger = LoggerFactory.getLogger(AddComputerController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping({"addComputer"})
	public String get(Model model) {
		logger.info("entering get");

		model.addAttribute("computer", new ComputerDTOBuilder().empty().build());
		
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		model.addAttribute(LIST_COMP_PARAM, companies);
		
		return VIEW;
	}
	
	@PostMapping({"addComputer"})
	public String post(@ModelAttribute("computer") ComputerDTO computer, Model model) {
		logger.info("entering post");
		
		try {
			int status = computerService.createComputer(computer);
			
			if (status == 0) {
				logger.error(String.format("Fail creating the computer : %s", computer));
				model.addAttribute(STATUS_CREATE_PARAM, "failed");
			} else {
				logger.info(String.format("Successfully created the computer : %s", computer));
				model.addAttribute(STATUS_CREATE_PARAM, "success");
			}
			
			return REDIRECT_DASHBOARD;
		} catch (FailComputerException e) {
			logger.error(String.format("post - Fail creating the computer : %s", computer));
			
			model.addAttribute(NAME_PARAM, computer.getName());
			model.addAttribute(INTR_PARAM, computer.getIntroduced());
			model.addAttribute(DISC_PARAM, computer.getDiscontinued());
			model.addAttribute(COMP_PARAM, computer.getCompanyId());
			
			switch (e.getConcerned()) {
			case NAME : model.addAttribute(ERROR_NAME, e.getReason());break;
			case INTRODUCED: model.addAttribute(ERROR_INTR, e.getReason());break;
			case DISCONTINUED : model.addAttribute(ERROR_DISC, e.getReason());break;
			case COMPANY : model.addAttribute(ERROR_COMP, e.getReason());break;
			default : break;
			}
			
			model.addAttribute(STATUS_CREATE_PARAM, "failed");
			
			return get(model);
		}
	}
}
