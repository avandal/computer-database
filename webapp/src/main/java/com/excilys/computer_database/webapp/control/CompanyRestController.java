package com.excilys.computer_database.webapp.control;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.service.service.CompanyService;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@RestController
@RequestMapping(path = "/api/company", produces = "application/json")
public class CompanyRestController {
	private Logger logger = LoggerFactory.getLogger(CompanyRestController.class);
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public ResponseEntity<List<CompanyDTO>> getAll() {
		logger.debug("getAll");
		return new ResponseEntity<>(companyService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CompanyDTO> getById(@PathVariable("id") String id) {
		logger.debug("getById");
		Optional<CompanyDTO> company = companyService.getById(id);
		
		if (company.isPresent()) {
			return new ResponseEntity<>(company.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		logger.debug("delete");
		try {
			companyService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (FailComputerException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
