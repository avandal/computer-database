package com.excilys.computer_database.control;

import java.util.Optional;

import com.excilys.computer_database.view.MenuPage;
import com.excilys.computer_database.view.Page;

public class ControlerCLI {
	private Page currentPage;

	public ControlerCLI() {
		this.currentPage = new MenuPage();
	}

	public void run() {
		boolean loop = true;

		while (loop) {
			String input = this.currentPage.show();
			Optional<Page> page = this.currentPage.exec(input);
			
			if (!page.isPresent()) {
				loop = false;
			} else {
				this.currentPage = page.get();
			}
		}
	}
}
