package com.excilys.computer_database.webapp.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.binding.dto.CompanyDTOBuilder;
import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.service.service.CompanyService;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;
import com.excilys.computer_database.webapp.validator.ComputerDTOValidator;

@Controller
@RequestMapping("/computer")
public class AddComputerController {
	static final String URL = "addComputer";
	static final String VIEW = "/new";
	static final String REDIRECT_DASHBOARD = "redirect:" + DashboardController.VIEW;
	
	private static final String LIST_COMP_PARAM = "companyList";
	
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
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}
	
	private String doGet(Model model) {
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		model.addAttribute(LIST_COMP_PARAM, companies);
		
		return URL;
	}
	
	@GetMapping("/new")
	public String get(Model model) {
		logger.info("entering get");
		
		model.addAttribute("computer", new ComputerDTOBuilder().empty().build());
		return doGet(model);
	}
	
	private boolean setStackTrace(Model model, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder stacktrace = new StringBuilder();
			result.getAllErrors().forEach(stacktrace::append);
			model.addAttribute("log", stacktrace);
			return true;
		}
		
		return false;
	}
	
	@PostMapping({"/add"})
	public String post(@ModelAttribute("computer") @Validated ComputerDTO computer, BindingResult result, Model model) {
		logger.info("entering post");
		
		if (setStackTrace(model, result)) {
			logger.info("post - There is errors");
			return doGet(model);
		}
		
		try {
			computerService.create(computer);
			logger.info("Computer successfully created, back to dashboard");
			
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
			
			return doGet(model);
		}
	}
}
