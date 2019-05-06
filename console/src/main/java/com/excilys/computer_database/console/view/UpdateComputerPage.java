package com.excilys.computer_database.console.view;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.binding.mapper.ComputerMapper;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Component
public class UpdateComputerPage extends Page {
	
	private static enum Item {
		MENU_ITEM("1 - Update name\n2 - Update introduced\n3 - Update discontinued\n4 - Confirm update\n5 - Quit without saving"),
		NAME_ITEM("Please give a computer name ('abort' to abort)"),
		INTRODUCED_ITEM("Please give an introduced date ('abort' to abort, 'null' to set null)"),
		DISCONTINUED_ITEM("Please give a discontinued date ('abort' to abort, 'null' to set null)"),
		UPDATE_ITEM(M_BACK_MENU),
		QUIT_ITEM("Nothing changes, " + BACK_MENU);
		
		private final String text;
		
		private Item(String text) {
			this.text = text;
		}
		
		public String text() {
			return this.text;
		}
		
		public static Item intToItem(int index) {
			switch (index) {
			case 0 : return MENU_ITEM;
			case 1 : return NAME_ITEM;
			case 2 : return INTRODUCED_ITEM;
			case 3 : return DISCONTINUED_ITEM;
			case 4 : return UPDATE_ITEM;
			case 5 : return QUIT_ITEM;
			default : return MENU_ITEM;
			}
		}
	}
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private MenuPage menuPage;
	
	private final static String MSG_ID = "Please give a computer id ('abort' to abort)";
										
	private ComputerDTO toChange;
	
	private String nameComp;
	private String introducedComp;
	private String discontinuedComp;
	
	private boolean start = false;
	private Item index = Item.MENU_ITEM;
	
	private UpdateComputerPage() {}
	
	private String currentChanges() {
		String ret = "Now:\n";
		ret += "name: " + toChange.getName();
		ret += ", introduced: " + toChange.getIntroduced();
		ret += ", discontinued: " + toChange.getDiscontinued() + "\n";
		ret += "Your changes:\n";
		ret += "name: " + nameComp;
		ret += ", introduced: " + introducedComp;
		ret += ", discontinued: "+ discontinuedComp;
		
		return boxMessage(ret);
	}
	
	@Override
	public String show() {
		if (!this.start) {
			System.out.println(boxMessage(MSG_ID));
		} else {
			switch (this.index) {
			case MENU_ITEM :
				System.out.println(currentChanges());
				System.out.println();
				System.out.println(Item.MENU_ITEM.text());
				break;
			case NAME_ITEM : System.out.println(boxMessage(Item.NAME_ITEM.text())); break;
			case INTRODUCED_ITEM : System.out.println(boxMessage(Item.INTRODUCED_ITEM.text())); break;
			case DISCONTINUED_ITEM : System.out.println(boxMessage(Item.DISCONTINUED_ITEM.text())); break;
			case UPDATE_ITEM : System.out.println(boxMessage(Item.UPDATE_ITEM.text())); break;
			case QUIT_ITEM : System.out.println(boxMessage(Item.QUIT_ITEM.text())); break;
			default : break;
			}
		}
		String input = "default";
		
		switch (this.index) {
		case MENU_ITEM : 
		case NAME_ITEM : 
		case INTRODUCED_ITEM : 
		case DISCONTINUED_ITEM : input = this.scan.nextLine(); break;
		default : break;
		}
		
		return input;
	}
	
	private boolean execId(String input) {
		Optional<Integer> id = Util.parseInt(input);
		
		if (id.isEmpty()) {
			System.out.println(boxMessage("Must be an integer"));
			return false;
		}
		
		Optional<ComputerDTO> toChange = service.getById(id.get());
		
		if (toChange.isEmpty()) {
			System.out.println(boxMessage("There is no computer with this id"));
			return false;
		}
		
		this.toChange = new ComputerDTOBuilder()
				.empty()
				.id(input.trim())
				.name(toChange.get().getName())
				.introduced(toChange.get().getIntroduced())
				.discontinued(toChange.get().getDiscontinued())
				.build();
		
		this.nameComp = this.toChange.getName();
		this.introducedComp = this.toChange.getIntroduced();
		this.discontinuedComp = this.toChange.getDiscontinued();
		
		return true;
	}
	
	private void execMenu(String input) {
		Optional<Integer> choice = Util.parseInt(input);
		
		if (choice.isEmpty()) {
			System.out.println(boxMessage("Must be an integer"));
			return;
		}
		
		if (choice.get() < 1 || choice.get() > 5) {
			System.out.println(boxMessage("Must be [1,2,3,4,5]"));
			return;
		}
		
		this.index = Item.intToItem(choice.get());
	}
	
	private void execName(String input) {
		this.nameComp = input;
		this.index = Item.MENU_ITEM;
	}
	
	private void setTimestamp(TimestampChoice choice, String time) {
		switch (choice) {
		case INTRODUCED : this.introducedComp = time; break;
		case DISCONTINUED : this.discontinuedComp = time; break;
		default : break;
		}
	}
	
	private void execTimestamp(TimestampChoice timestamp, String input) {
		if (input.equals("null")) {
			setTimestamp(timestamp, null);
			return;
		}
		
		Optional<Timestamp> time = Util.dateToTimestamp(input);
		
		if (time.isEmpty()) {
			System.out.println(boxMessage("Wrong format"));
			return;
		}
		
		setTimestamp(timestamp, input);
		this.index = Item.MENU_ITEM;
	}
	
	private void execUpdate() {
		try {
			toChange.setName(nameComp);
			toChange.setIntroduced(introducedComp);
			toChange.setDiscontinued(discontinuedComp);
			
			service.update(toChange);
		} catch (FailComputerException e) {
			e.printStackTrace();
		}
		
		this.index = Item.UPDATE_ITEM;
	}
	
	private Optional<Page> initialChecks(String input) {
		if (input == null || input.trim().equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(this);
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted]"));
			this.index = Item.MENU_ITEM;
			return Optional.of(menuPage);
		}
		
		return Optional.empty();
	}
	
	private boolean checkStart(String input) {
		if (!this.start) {
			if (execId(input)) {
				this.start = true;
			}
			return false;
		}
		
		return true;
	}
	
	private void reset() {
		start = true;
		index = Item.MENU_ITEM;
	}
	
	@Override
	public Optional<Page> exec(String input) {
		Optional<Page> initialCheckPage = initialChecks(input);
		if (initialCheckPage.isPresent()) {
			return initialCheckPage;
		}
		
		if (!checkStart(input)) {
			return Optional.of(this);
		}
		
		switch (this.index) {
		case MENU_ITEM :
			execMenu(input);
			break;
			
		case NAME_ITEM :
			execName(input);
			break;
			
		case INTRODUCED_ITEM :
			execTimestamp(TimestampChoice.INTRODUCED, input);
			break;
			
		case DISCONTINUED_ITEM :
			execTimestamp(TimestampChoice.DISCONTINUED, input);
			break;
			
		case UPDATE_ITEM :
			execUpdate();
			reset();
			return Optional.of(menuPage);
			
		case QUIT_ITEM :
			reset();
			return Optional.of(menuPage);
			
		default : break;
		}
		
		return Optional.of(this);
	}
	
	public String toString() {
		return "Update computer";
	}
}