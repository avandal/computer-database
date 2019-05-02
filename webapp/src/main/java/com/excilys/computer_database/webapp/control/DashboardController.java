package com.excilys.computer_database.webapp.control;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.SortMode;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;
import com.excilys.computer_database.webapp.control.web_model.WebPage;
import com.excilys.computer_database.webapp.control.web_model.WebPageBuilder;

@Controller
@RequestMapping({"/", "/dashboard"})
public class DashboardController {
	static final String URL = "dashboard";
	static final String VIEW = "/dashboard";
	static final String REDIRECT = "redirect:" + VIEW;
	
	public static final String PAGE_SIZE_PARAM = "pageSize";
	public static final String PAGE_INDEX_PARAM = "pageIndex";
	public static final String NB_COMPUTERS = "nbComputers";
	public static final String LIST_COMP_PARAM_FILTERED = "computerListFiltered";
	public static final String WEB_PAGE_PARAM = "webPage";
	public static final String SEARCH_PARAM = "search";
	public static final String ORDER_PARAM = "order";
	
	private Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@GetMapping
	public String get(@RequestParam(required = false) Map<String, String> args, Model model) {
		logger.info("entering get");
		
		String search = args.get(SEARCH_PARAM);
		String order = args.get(ORDER_PARAM);
		SortMode mode = SortMode.getByValue(order);
		
		List<ComputerDTO> list = search == null || "".equals(search) ? computerService.getAll(mode) : computerService.searchByName(search, mode);
		
		Optional<Integer> size = Util.parseInt(args.get(PAGE_SIZE_PARAM));
		Optional<Integer> index = Util.parseInt(args.get(PAGE_INDEX_PARAM));
		
		WebPage<ComputerDTO> webPage = new WebPageBuilder<ComputerDTO>()
				.list(list)
				.size(size)
				.index(index)
				.search(search)
				.order(order)
				.url(URL)
				.build();
		
		model.addAttribute(NB_COMPUTERS, list.size());
		model.addAttribute(LIST_COMP_PARAM_FILTERED, list);
		model.addAttribute(PAGE_INDEX_PARAM, webPage.getIndex());
		model.addAttribute(PAGE_SIZE_PARAM, webPage.getSize());
		model.addAttribute(WEB_PAGE_PARAM, webPage);
		
		return URL;
	}
	
	@PostMapping({"/delete"})
	public String post(@RequestParam(value = "cb", required = false) String[] toDelete, Model model) {
		logger.info("entering post");
		
		if (toDelete != null) {
			for (String computerId : toDelete) {
				try {
					computerService.deleteComputer(computerId);
				} catch (FailComputerException e) {
					logger.error("post - Fail deleting computer " + computerId);
				}
			}
		}
		return REDIRECT;
	}
}
