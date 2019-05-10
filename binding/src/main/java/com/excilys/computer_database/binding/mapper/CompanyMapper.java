package com.excilys.computer_database.binding.mapper;

import java.util.HashMap;
import java.util.function.Predicate;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.binding.dto.CompanyDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Company;
import com.excilys.computer_database.core.model.CompanyBuilder;

public abstract class CompanyMapper {
	private CompanyMapper() {}
	
	public static Company dtoToCompany(CompanyDTO dto) {
		return new CompanyBuilder()
				.id(Util.parseInt(dto.getId()).isEmpty() ? 0 : Util.parseInt(dto.getId()).get())	
				.name(dto.getName())
				.build();
	}
	
	public static CompanyDTO companyToDTO(Company company) {
		return new CompanyDTOBuilder()
				.id(Integer.toString(company.getId()))
				.name(company.getName())
				.build();
	}
	
	public static CompanyDTO hashmapToDTO(HashMap<String, String> map) {
		Predicate<String> condition = s -> s != null;
		
		return new CompanyDTOBuilder()
				.id(Util.accordingTo(condition, map.get("id") ,"0"))
				.name(Util.accordingTo(condition, map.get("name"), ""))
				.build();
	}
}
