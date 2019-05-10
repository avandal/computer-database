package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.mapper.ComputerMapper;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.service.pagination.PageSize;

@Component
public class ListComputerPage extends Page {
	
	@Autowired
	private MenuPage menuPage;
	
	private ListComputerPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}
	
	private String url() {
		return new StringBuilder()
				.append(URL_API).append("/computer?")
				.append("pageSize=").append(PageSize.SHOW_ALL.getSize())
				.toString();
	}

	@Override
	public String show() {

		Invocation.Builder invoke = client.target(url()).request(MediaType.APPLICATION_JSON);
		
		StringBuilder sbList = new StringBuilder("Here is the database's computer list:\n");
		List<HashMap<String, String>> list = Util.castList(HashMap.class, invoke.get().readEntity(List.class));
		list.forEach(s -> sbList.append(ComputerMapper.hashmapToDTO(s)+"\n"));
		
		System.out.println(String.format("%s\n%s\n", boxMessage(sbList.toString(), 3), M_BACK_MENU));
		return null;
	}
	
	@Override
	public Optional<Page> exec(String input) {
		return backToMenu();
	}
	
	public String toString() {
		return "ListComputer";
	}
}
