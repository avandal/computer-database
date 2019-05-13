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
import com.excilys.computer_database.core.model.SortMode;
import com.excilys.computer_database.service.pagination.WebPage;
import com.excilys.computer_database.service.pagination.WebPageBuilder;
import com.excilys.computer_database.service.service.CompanyService;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;
import com.excilys.computer_database.webapp.validator.ComputerDTOValidator;

@Controller
@RequestMapping({"/", "/dashboard"})
public class ComputerController {
	
	// Views
	
	private static final String DASHBOARD_URL = "dashboard";
	private static final String DASHBOARD_VIEW = "/dashboard";
	private static final String DASHBOARD_REDIRECT = "redirect:" + DASHBOARD_VIEW;
	
	private static final String ADD_COMPUTER_URL = "addComputer";
	
	private static final String EDIT_COMPUTER_URL = "editComputer";
	
	// Dashboard
	
	public static final String NB_COMPUTERS = "nbComputers";
	public static final String LIST_COMP_PARAM_FILTERED = "computerListFiltered";
	public static final String WEB_PAGE_PARAM = "webPage";
	
	// Modifying (Add/Edit)
	
	private static final String LIST_COMP_PARAM = "companyList";
	
	private static final String NAME_PARAM = "computerName";
	private static final String INTR_PARAM = "introduced";
	private static final String DISC_PARAM = "discontinued";
	private static final String COMP_PARAM = "companyId";
	
	private static final String ERROR_NAME = "errorName";
	private static final String ERROR_INTR = "errorIntroduced";
	private static final String ERROR_DISC = "errorDiscontinued";
	private static final String ERROR_COMP = "errorCompany";
	
	// Edit
	
	private static final String COMPUTER_ID_PARAM = "computerId";
	
	private static final String ORIGINAL_NAME_PARAM = "originalComputerName";
	private static final String ORIGINAL_INTR_PARAM = "originalIntroduced";
	private static final String ORIGINAL_DISC_PARAM = "originalDiscontinued";
	private static final String ORIGINAL_COMP_ID_PARAM = "originalCompanyId";
	private static final String ORIGINAL_COMP_NAME_PARAM = "originalCompanyName";
	
	private Logger logger = LoggerFactory.getLogger(ComputerController.class);

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	// Validator (Add/Edit)
	
	@InitBinder("/computer")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}
	
	// dashboard.jsp
	
	@GetMapping
	public String home(@RequestParam(required = false) Map<String, String> args, Model model) {
		logger.info("entering get");
		
		String search = args.get(WebPage.SEARCH_PARAM);
		String order = args.get(WebPage.ORDER_PARAM);
		String size = args.get(WebPage.PAGE_SIZE_PARAM);
		SortMode mode = SortMode.getByValue(order);
		
		List<ComputerDTO> list = search == null || "".equals(search) ? computerService.getAll(mode) : computerService.getByName(search, mode);
		
		Optional<Integer> index = Util.parseInt(args.get(WebPage.PAGE_INDEX_PARAM));
		
		WebPage<ComputerDTO> webPage = new WebPageBuilder<ComputerDTO>()
				.list(list)
				.size(size)
				.index(index)
				.search(search)
				.order(order)
				.url(DASHBOARD_URL)
				.build();
		
		model.addAttribute(NB_COMPUTERS, list.size());
		model.addAttribute(LIST_COMP_PARAM_FILTERED, list);
		model.addAttribute(WebPage.PAGE_INDEX_PARAM, webPage.getIndex());
		model.addAttribute(WebPage.PAGE_SIZE_PARAM, webPage.getSize());
		model.addAttribute(WEB_PAGE_PARAM, webPage);
		
		return DASHBOARD_URL;
	}
	
	@PostMapping({"/delete"})
	public String delete(@RequestParam(value = "cb", required = false) String[] toDelete, Model model) {
		logger.info("entering post");
		
		if (toDelete != null) {
			for (String computerId : toDelete) {
				try {
					computerService.delete(computerId);
				} catch (FailComputerException e) {
					logger.error("post - Fail deleting computer {}", computerId);
				}
			}
		}
		return DASHBOARD_REDIRECT;
	}
	
	// addComputer.jsp
	
	private String fillCompaniesAndback(Model model) {
		List<CompanyDTO> companies = companyService.getAll();
		companies.add(0, new CompanyDTOBuilder().empty().build());
		
		model.addAttribute(LIST_COMP_PARAM, companies);
		
		return ADD_COMPUTER_URL;
	}
	
	@GetMapping("/computer/new")
	public String newComputer(Model model) {
		logger.info("entering get");
		
		model.addAttribute("computer", new ComputerDTOBuilder().empty().build());
		return fillCompaniesAndback(model);
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
	
	@PostMapping({"/computer/add"})
	public String postNewComputer(@ModelAttribute("computer") @Validated ComputerDTO computer, BindingResult result, Model model) {
		logger.info("entering post");
		
		if (setStackTrace(model, result)) {
			logger.info("post - There is errors");
			return fillCompaniesAndback(model);
		}
		
		try {
			computerService.create(computer);
			logger.info("Computer successfully created, back to dashboard");
			
			return DASHBOARD_REDIRECT;
		} catch (FailComputerException e) {
			logger.error("post - Fail creating the computer : {}", computer);
			
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
			
			return fillCompaniesAndback(model);
		}
	}
	
	private String execEdit(Map<String, String> args, Model model) {
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
				
				return EDIT_COMPUTER_URL;
			}
			logger.error("get - This computer (id: {}) does not exist, back to dashboard", id.get());
		} else {
			logger.error("get - Invalid computer ID ({}), back to dashboard", computerId);
		}
		
		return DASHBOARD_REDIRECT;
	}
	
	@GetMapping({"/computer/edit"})
	public String editComputer(@RequestParam(required = false) Map<String, String> args, Model model) {
		logger.info("entering get");
		model.addAttribute("computer", new ComputerDTOBuilder().empty().build());
		
		return execEdit(args, model);
	}
	
	@PostMapping({"/computer/validate"})
	public String validateEdit(@Validated @ModelAttribute("computer") ComputerDTO computer, BindingResult result, Model model) {
		logger.info("entering post");
		
		if (result.hasErrors()) {
			logger.info("post - There is errors");
			return execEdit(Map.of(COMPUTER_ID_PARAM, computer.getId()), model);
		}
		
		Optional<Integer> optId = Util.parseInt(computer.getId());
		if (optId.isEmpty()) {
			logger.warn("post - invalid computer ID");
			return DASHBOARD_REDIRECT;
		}
		
		try {
			computerService.update(computer);
			logger.info("Computer successfully updated, back to dashboard");
			
			return DASHBOARD_REDIRECT;
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
			
			return execEdit(Map.of(COMPUTER_ID_PARAM, computer.getId()), model);
		}
	}
}
