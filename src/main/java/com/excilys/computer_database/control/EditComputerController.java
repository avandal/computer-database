package com.excilys.computer_database.control;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.exception.FailComputerException;
import com.excilys.computer_database.util.Util;

@Controller
public class EditComputerController {
	public static final String VIEW = "editComputer";
	
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
	
	private Logger logger = LoggerFactory.getLogger(EditComputerController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping({"editComputer"})
	public String get(@RequestParam(required = false) Map<String, String> args, Model model) {
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		model.addAttribute(LIST_COMP_PARAM, companies);
		
		String computerId = args.get(COMPUTER_ID_PARAM);
		Optional<Integer> id = Util.parseInt(computerId);
		if (id.isPresent()) {
			Optional<ComputerDTO> optComputer = computerService.getComputerDetails(id.get());
			if (optComputer.isPresent()) {
				model.addAttribute(COMPUTER_ID_PARAM, id.get());
				
				ComputerDTO computer = optComputer.get();
				
				model.addAttribute(ORIGINAL_NAME_PARAM, computer.getName());
				model.addAttribute(ORIGINAL_INTR_PARAM, computer.getIntroduced());
				model.addAttribute(ORIGINAL_DISC_PARAM, computer.getDiscontinued());
				model.addAttribute(ORIGINAL_COMP_ID_PARAM, computer.getCompanyId());
				model.addAttribute(ORIGINAL_COMP_NAME_PARAM, computer.getCompanyName() == null ? "No one" : computer.getCompanyName());
				
				return VIEW;
			}
			logger.error("get - This computer (" + optComputer.get() + ") does not exist, back to dashboard");
		} else {
			logger.error("get - Invalid computer ID (" + id.get() + "), back to dashboard");
		}
		
		return "redirect:" + DashboardController.VIEW;
	}
	
	@PostMapping({"editComputer"})
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
		
		Optional<Integer> optId = Util.parseInt(args.get(COMPUTER_ID_PARAM));
		if (!optId.isPresent()) {
			logger.warn("post - invalid computer ID");
			return "redirect:" + DashboardController.VIEW;
		}
		
		int computerId = optId.get();
		
		try {
			computerService.updateComputer(computerId, name, introduced, discontinued, company);
			logger.info("Computer successfully updated, back to dashboard");
			
			return "redirect:" + DashboardController.VIEW;
		} catch (FailComputerException e) {
			switch (e.getConcerned()) {
			case NAME : model.addAttribute(ERROR_NAME, e.getReason());break;
			case INTRODUCED: model.addAttribute(ERROR_INTR, e.getReason());break;
			case DISCONTINUED : model.addAttribute(ERROR_DISC, e.getReason());break;
			case COMPANY : model.addAttribute(ERROR_COMP, e.getReason());break;
			default : break;
			}
			e.printStackTrace();
			
			return get(args, model);
		}
	}
}
