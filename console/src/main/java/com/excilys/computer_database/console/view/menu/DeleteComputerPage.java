package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;

@Component
public class DeleteComputerPage extends Page {
	
	@Autowired
	private MenuPage menuPage;
	
	@Autowired
	private DeleteComputerPage that;

	private DeleteComputerPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		System.out.println(boxMessage(String.format("Please give a computer id (%s)", ABORT)));

		return this.scan.nextLine();
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(that);
		}

		if (input.equals("abort")) {
			System.out.println(boxMessage(String.format("[Aborted] %s", BACK_MENU)));
			return backToMenu();
		}

		Optional<Integer> idInput = Util.parseInt(input);

		if (idInput.isEmpty()) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return Optional.of(that);
		}

		if (idInput.get() <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return Optional.of(that);
		}

		Invocation.Builder invoke = client.target(URL_API + "/computer/" + input).request(MediaType.APPLICATION_JSON);
		Response response = invoke.delete();
		
		if (response.getStatus() == HttpStatus.OK.value()) {
			System.out.println(Util.boxMessage("Computer successfully deleted"));
		} else {
			System.out.println(Util.boxMessage("Error when deleting the given computer: " + input));
		}
		
		return backToMenu();
	}

	public String toString() {
		return "Delete computer";
	}
}
