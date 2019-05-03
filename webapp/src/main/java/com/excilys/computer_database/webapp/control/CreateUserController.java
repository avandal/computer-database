package com.excilys.computer_database.webapp.control;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.service.service.UserService;
import com.excilys.computer_database.service.service.exception.FailUserException;

@Controller
@RequestMapping("/login")
public class CreateUserController {
	static final String URL = "signup";
	static final String VIEW = "/signup";
	static final String REDIRECT_LOGIN = "redirect:/login";
	
	private static final String ERROR_USERNAME = "errorUsername";
	private static final String ERROR_PASSWORD = "errorPassword";
	
	private Logger logger = LoggerFactory.getLogger(CreateUserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String get(Model model) {
		logger.info("Entering get");
		return URL;
	}
	
	@PostMapping("/create")
	public String post(@RequestParam(required = false) Map<String, String> args, Model model) {
		logger.info("Entering post");
		
		String username = args.get("username");
		String password = args.get("password");
		
		try {
			userService.createUser(username, password);
			
			logger.info("User successfully created: [" + username + ", " + password + "]");
			return REDIRECT_LOGIN;
		} catch (FailUserException e) {
			switch (e.getConcerned()) {
			case USERNAME : model.addAttribute(ERROR_USERNAME, e.getReason()); break;
			case PASSWORD : model.addAttribute(ERROR_PASSWORD, e.getReason()); break;
			default: break;
			}
			
			return URL;
		}
	}
}
