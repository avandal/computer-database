package com.excilys.computer_database.control;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.CompanyDTOBuilder;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.exception.FailComputerException;

@Controller
public class AddComputerController {
	
	public static final String VIEW = "addComputer";
	
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
	public String get(@RequestParam(required = false) Map<String, String> args, Model model) {
		logger.info("entering get");
		
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		model.addAttribute(LIST_COMP_PARAM, companies);
		
		return VIEW;
	}
	
	@PostMapping({"addComputer"})
	public String post(@RequestParam(required = false) Map<String, String> args, Model model) {
		logger.info("entering post");
		
		String name = args.get(NAME_PARAM);
		String introduced = args.get(INTR_PARAM);
		String discontinued = args.get(DISC_PARAM);
		String company = args.get(COMP_PARAM);
		
		model.addAttribute(NAME_PARAM, name);
		model.addAttribute(INTR_PARAM, introduced);
		model.addAttribute(DISC_PARAM, discontinued);
		model.addAttribute(COMP_PARAM, company);
		
		try {
			int status = computerService.createComputer(name, introduced, discontinued, company);
			
			if (status == 0) {
				System.out.println("Error creating");
				model.addAttribute(STATUS_CREATE_PARAM, "failed");
			} else {
				model.addAttribute(STATUS_CREATE_PARAM, "success");
			}
			
			return DashboardController.VIEW;
		} catch (FailComputerException e) {
			logger.error(String.format("post - Fail creating the computer : %s, %s, %s, %s", name, introduced, discontinued, company));
			
			switch (e.getConcerned()) {
			case NAME : model.addAttribute(ERROR_NAME, e.getReason());break;
			case INTRODUCED: model.addAttribute(ERROR_INTR, e.getReason());break;
			case DISCONTINUED : model.addAttribute(ERROR_DISC, e.getReason());break;
			case COMPANY : model.addAttribute(ERROR_COMP, e.getReason());break;
			default : break;
			}
			
			model.addAttribute(STATUS_CREATE_PARAM, "failed");
			
			return get(args, model);
		}
	}
}
