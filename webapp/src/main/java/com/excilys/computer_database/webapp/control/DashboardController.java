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
import com.excilys.computer_database.service.pagination.WebPage;
import com.excilys.computer_database.service.pagination.WebPageBuilder;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Controller
@RequestMapping({"/", "/dashboard"})
public class DashboardController {
	final static String URL = "dashboard";
	final static String VIEW = "/dashboard";
	final static String REDIRECT = "redirect:" + VIEW;
	
	public final static String NB_COMPUTERS = "nbComputers";
	public final static String LIST_COMP_PARAM_FILTERED = "computerListFiltered";
	public final static String WEB_PAGE_PARAM = "webPage";
	
	private Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@GetMapping
	public String get(@RequestParam(required = false) Map<String, String> args, Model model) {
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
				.url(URL)
				.build();
		
		model.addAttribute(NB_COMPUTERS, list.size());
		model.addAttribute(LIST_COMP_PARAM_FILTERED, list);
		model.addAttribute(WebPage.PAGE_INDEX_PARAM, webPage.getIndex());
		model.addAttribute(WebPage.PAGE_SIZE_PARAM, webPage.getSize());
		model.addAttribute(WEB_PAGE_PARAM, webPage);
		
		return URL;
	}
	
	@PostMapping({"/delete"})
	public String post(@RequestParam(value = "cb", required = false) String[] toDelete, Model model) {
		logger.info("entering post");
		
		if (toDelete != null) {
			for (String computerId : toDelete) {
				try {
					computerService.delete(computerId);
				} catch (FailComputerException e) {
					logger.error("post - Fail deleting computer " + computerId);
				}
			}
		}
		return REDIRECT;
	}
}
