package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.mapper.CompanyMapper;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;

@Component
public class ListCompanyPage extends Page {
	
	@Autowired
	private MenuPage menuPage;
	
	private ListCompanyPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		Invocation.Builder invoke = client.target(URL_API + "/company").request(MediaType.APPLICATION_JSON);
		
		StringBuilder sbList = new StringBuilder("Here is the database's company list:\n");
		List<HashMap<String, String>> list = Util.castList(HashMap.class, invoke.get().readEntity(List.class));
		list.forEach(s -> sbList.append(CompanyMapper.hashmapToDTO(s)+"\n"));
		
		System.out.println(String.format("%s\n%s\n", boxMessage(sbList, 3), M_BACK_MENU));
		return null;
	}

	@Override
	public Optional<Page> exec(String input) {
		return backToMenu();
	}
	
	public String toString() {
		return "List company";
	}
}
