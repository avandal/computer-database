package com.excilys.control;

import com.excilys.view.MenuPage;
import com.excilys.view.Page;

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
