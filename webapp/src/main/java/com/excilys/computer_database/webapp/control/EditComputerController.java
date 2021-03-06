package com.excilys.computer_database.webapp.control;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.binding.dto.CompanyDTOBuilder;
import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.service.service.CompanyService;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;
import com.excilys.computer_database.webapp.validator.ComputerDTOValidator;

@Controller
@RequestMapping("/computer")
public class EditComputerController {
	final static String URL = "editComputer";
	final static String VIEW = "/edit";
	final static String REDIRECT_DASHBOARD = "redirect:" + DashboardController.VIEW;
	
	private final static String LIST_COMP_PARAM = "companyList";
	
	private final static String COMPUTER_ID_PARAM = "computerId";
	
	private final static String NAME_PARAM = "computerName";
	private final static String INTR_PARAM = "introduced";
	private final static String DISC_PARAM = "discontinued";
	private final static String COMP_PARAM = "companyId";
	
	private final static String ORIGINAL_NAME_PARAM = "originalComputerName";
	private final static String ORIGINAL_INTR_PARAM = "originalIntroduced";
	private final static String ORIGINAL_DISC_PARAM = "originalDiscontinued";
	private final static String ORIGINAL_COMP_ID_PARAM = "originalCompanyId";
	private final static String ORIGINAL_COMP_NAME_PARAM = "originalCompanyName";
	
	private final static String ERROR_NAME = "errorName";
	private final static String ERROR_INTR = "errorIntroduced";
	private final static String ERROR_DISC = "errorDiscontinued";
	private final static String ERROR_COMP = "errorCompany";
	
	private Logger logger = LoggerFactory.getLogger(EditComputerController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}
	
	private String doGet(Map<String, String> args, Model model) {
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		model.addAttribute(LIST_COMP_PARAM, companies);
		
		String computerId = args.get(COMPUTER_ID_PARAM);
		Optional<Integer> id = Util.parseInt(computerId);
		if (id.isPresent()) {
			Optional<ComputerDTO> optComputer = computerService.getById(computerId);
			if (optComputer.isPresent()) {
				model.addAttribute(COMPUTER_ID_PARAM, id.get());
				
				ComputerDTO computer = optComputer.get();
				
				model.addAttribute(ORIGINAL_NAME_PARAM, computer.getName());
				model.addAttribute(ORIGINAL_INTR_PARAM, computer.getIntroduced());
				model.addAttribute(ORIGINAL_DISC_PARAM, computer.getDiscontinued());
				model.addAttribute(ORIGINAL_COMP_ID_PARAM, computer.getCompanyId());
				model.addAttribute(ORIGINAL_COMP_NAME_PARAM, "".equals(computer.getCompanyName()) ? "No one" : computer.getCompanyName());
				
				return URL;
			}
			logger.error("get - This computer (" + optComputer.get() + ") does not exist, back to dashboard");
		} else {
			logger.error("get - Invalid computer ID (" + id.get() + "), back to dashboard");
		}
		
		return REDIRECT_DASHBOARD;
	}
	
	@GetMapping({"edit"})
	public String get(@RequestParam(required = false) Map<String, String> args, Model model) {
		logger.info("entering get");
		model.addAttribute("computer", new ComputerDTOBuilder().empty().build());
		
		return doGet(args, model);
	}
	
	@PostMapping({"validate"})
	public String post(@Validated @ModelAttribute("computer") ComputerDTO computer, BindingResult result, Model model) {
		logger.info("entering post");
		
		if (result.hasErrors()) {
			logger.info("post - There is errors");
			return doGet(Map.of(COMPUTER_ID_PARAM, computer.getId()), model);
		}
		
		Optional<Integer> optId = Util.parseInt(computer.getId());
		if (optId.isEmpty()) {
			logger.warn("post - invalid computer ID");
			return REDIRECT_DASHBOARD;
		}
		
		try {
			computerService.update(computer);
			logger.info("Computer successfully updated, back to dashboard");
			
			return REDIRECT_DASHBOARD;
		} catch (FailComputerException e) {
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
			e.printStackTrace();
			
			return doGet(Map.of(COMPUTER_ID_PARAM, computer.getId()), model);
		}
	}
}
