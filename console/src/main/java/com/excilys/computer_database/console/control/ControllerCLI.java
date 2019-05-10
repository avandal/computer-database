package com.excilys.computer_database.console.control;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;

@Component
public class ControllerCLI {
	private Page currentPage;
	
	private Logger logger = LoggerFactory.getLogger(ControllerCLI.class);
	
	private final static String RUN = "ControllerCLI - Run";

	@Autowired
	private ControllerCLI(MenuPage currentPage) {
		this.currentPage = currentPage;
	}

	public void run() {
		boolean loop = true;
		
		logger.debug(String.format("%s : initial menu is %s", RUN, currentPage));

		while (loop) {
			String input = this.currentPage.show();
			Optional<Page> page = this.currentPage.exec(input);
			
			if (page.isEmpty()) {
				loop = false;
				logger.debug(String.format("%s : Exit", RUN));
			} else {
				this.currentPage = page.get();
				logger.debug(String.format("%s : changing menu to ", RUN, currentPage));
			}
		}
	}
}
