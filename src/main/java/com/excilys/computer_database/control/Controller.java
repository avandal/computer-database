package com.excilys.computer_database.control;

import com.excilys.computer_database.view.MenuPage;
import com.excilys.computer_database.view.Page;

public class Controller {
	private Page currentPage;

	public Controller() {
		this.currentPage = new MenuPage();
	}

	public void run() {
		boolean loop = true;

		while (loop) {
			String input = this.currentPage.show();
			Page page = this.currentPage.exec(input);
			if (page == null) {
				loop = false;
			} else {
				this.currentPage = page;
			}
		}
	}
}
