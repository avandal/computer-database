package com.excilys.computer_database.console.view.menu.update.checker;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class NameChecker extends ItemChecker {

	@Override
	public Optional<String> validate(String item) {
		back = true;
		return Optional.of(item);
	}
}
