package com.excilys.computer_database.control;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.view.MenuPage;
import com.excilys.computer_database.view.Page;

@Component
public class ControlerCLI {
	private Page currentPage;
	
	private Logger logger = LoggerFactory.getLogger(ControlerCLI.class);

	@Autowired
	private ControlerCLI(MenuPage currentPage) {
		this.currentPage = currentPage;
	}

	public void run() {
		boolean loop = true;
		
		logger.debug("ControlerCLI - Run : initial menu is " + currentPage);

		while (loop) {
			String input = this.currentPage.show();
			Optional<Page> page = this.currentPage.exec(input);
			
			if (!page.isPresent()) {
				loop = false;
				logger.debug("ControlerCLI - Run : Exit");
			} else {
				this.currentPage = page.get();
				logger.debug("ControlerCLI - Run : changing menu to " + currentPage);
			}
		}
	}
}
