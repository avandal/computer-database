package com.excilys.computer_database.console.view.menu.update.checker;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.util.Util;

@Component
public class DateChecker extends ItemChecker {

	@Override
	public Optional<String> validate(String item) {
		if (item.equals("null")) {
			back = true;
			return Optional.empty();
		}
		
		Optional<Timestamp> time = Util.dateToTimestamp(item);
		
		if (time.isEmpty()) {
			System.out.println(boxMessage("Wrong format"));
			back = false;
			return Optional.empty();
		}
		
		back = true;
		return Optional.of(item);
	}

}
