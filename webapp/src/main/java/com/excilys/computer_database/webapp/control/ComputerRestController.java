package com.excilys.computer_database.webapp.control;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.SortMode;
import com.excilys.computer_database.service.pagination.WebPage;
import com.excilys.computer_database.service.pagination.WebPageBuilder;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;
import com.excilys.computer_database.webapp.validator.ComputerDTOValidator;

@RestController
@RequestMapping(path = "/api/computer", produces = "application/json")
public class ComputerRestController {
	private Logger logger = LoggerFactory.getLogger(ComputerRestController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}
	
	@GetMapping
	public ResponseEntity<List<ComputerDTO>> getAll(@RequestParam Map<String, String> args) {
		logger.debug("getAll");
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
				.build();
		
		return new ResponseEntity<>(webPage.indexPage(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTO> getById(@PathVariable("id") String id) {
		logger.debug("getById");
		Optional<ComputerDTO> computer = computerService.getById(id);
		if (computer.isPresent()) {
			return new ResponseEntity<>(computer.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<ComputerDTO> create(@Validated @RequestBody ComputerDTO computer) {
		logger.debug("create");
		try {
			computerService.create(computer);
			return new ResponseEntity<>(computer, HttpStatus.OK);
		} catch (FailComputerException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<ComputerDTO> update(@Validated @RequestBody ComputerDTO computer) {
		logger.debug("update");
		try {
			computerService.update(computer);
			return new ResponseEntity<>(computer, HttpStatus.OK);
		} catch (FailComputerException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ComputerDTO> delete(@PathVariable("id") String id) {
		logger.debug("delete");
		try {
			computerService.delete(id);
			System.out.println(boxMessage("Computer successfully deleted"));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (FailComputerException e) {
			System.out.println(boxMessage("[Problem] Fail deleting computer"));
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
