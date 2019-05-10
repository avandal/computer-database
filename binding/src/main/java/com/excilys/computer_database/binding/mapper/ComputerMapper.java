package com.excilys.computer_database.binding.mapper;

import java.util.HashMap;
import java.util.function.Predicate;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Computer;

public abstract class ComputerMapper {
	private ComputerMapper() {}
	
	public static ComputerDTO computerToDTO(Computer computer) {
		ComputerDTOBuilder builder = new ComputerDTOBuilder().empty();
		builder.id(Integer.toString(computer.getId())).name(computer.getName());
		
		if (computer.getIntroduced() != null) {
			builder.introduced(Util.timestampToDate(computer.getIntroduced()));
		}
		if (computer.getDiscontinued() != null) {
			builder.discontinued(Util.timestampToDate(computer.getDiscontinued()));
		}
		if (computer.getCompany() != null) {
			builder.companyId(Integer.toString(computer.getCompany().getId()));
			builder.companyName(computer.getCompany().getName());
		}
		
		return builder.build();
	}
	
	public static ComputerDTO hashmapToDTO(HashMap<String, String> map) {
		Predicate<String> condition = s -> s != null;
		
		return new ComputerDTOBuilder()
				.id(Util.accordingTo(condition, map.get("id") ,"0"))
				.name(Util.accordingTo(condition, map.get("name"), ""))
				.introduced(Util.accordingTo(condition, map.get("introduced"), null))
				.discontinued(Util.accordingTo(condition, map.get("discontinued"), null))
				.companyId(Util.accordingTo(condition, map.get("companyId"), null))
				.companyName(Util.accordingTo(condition, map.get("companyName"), null))
				.build();
	}
}
