package com.excilys.computer_database.console.view.menu.update.checker;

import java.util.Optional;

public abstract class ItemChecker {
	protected boolean back = false;
	
	public abstract Optional<String> validate(String item);
	
	public boolean back() {
		return back;
	}
}
