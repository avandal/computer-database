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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.CompanyDTOBuilder;
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.dto.ComputerDTOBuilder;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.exception.FailComputerException;
import com.excilys.computer_database.util.Util;

@Controller
public class EditComputerController {
	static final String VIEW = "editComputer";
	static final String REDIRECT_DASHBOARD = "redirect:" + DashboardController.VIEW;
	
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
				model.addAttribute("computer", new ComputerDTOBuilder().empty().build());
				
				model.addAttribute(COMPUTER_ID_PARAM, id.get());
				
				ComputerDTO computer = optComputer.get();
				
				model.addAttribute(ORIGINAL_NAME_PARAM, computer.getName());
				model.addAttribute(ORIGINAL_INTR_PARAM, computer.getIntroduced());
				model.addAttribute(ORIGINAL_DISC_PARAM, computer.getDiscontinued());
				model.addAttribute(ORIGINAL_COMP_ID_PARAM, computer.getCompanyId());
				model.addAttribute(ORIGINAL_COMP_NAME_PARAM, "".equals(computer.getCompanyName()) ? "No one" : computer.getCompanyName());
				
				return VIEW;
			}
			logger.error("get - This computer (" + optComputer.get() + ") does not exist, back to dashboard");
		} else {
			logger.error("get - Invalid computer ID (" + id.get() + "), back to dashboard");
		}
		
		return REDIRECT_DASHBOARD;
	}
	
	@PostMapping({"editComputer"})
	public String post(@ModelAttribute("computer") ComputerDTO computer, Model model) {
		logger.info("entering post");
		
		Optional<Integer> optId = Util.parseInt(computer.getId());
		if (optId.isEmpty()) {
			logger.warn("post - invalid computer ID");
			return REDIRECT_DASHBOARD;
		}
		
		try {
			computerService.updateComputer(computer);
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
			
			return get(Map.of(COMPUTER_ID_PARAM, computer.getId()), model);
		}
	}
}
