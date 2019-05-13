package com.excilys.computer_database.binding.mapper;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.binding.dto.CompanyDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Company;
import com.excilys.computer_database.core.model.CompanyBuilder;

public interface CompanyMapper {
	public static Company dtoToCompany(CompanyDTO dto) {
		Optional<Integer> optId = Util.parseInt(dto.getId());
		
		return new CompanyBuilder()
				.id(optId.isEmpty() ? 0 : optId.get())	
				.name(dto.getName())
				.build();
	}
	
	public static CompanyDTO companyToDTO(Company company) {
		return new CompanyDTOBuilder()
				.id(Integer.toString(company.getId()))
				.name(company.getName())
				.build();
	}
	
	public static CompanyDTO hashmapToDTO(Map<String, String> map) {
		Predicate<String> condition = Objects::nonNull;
		
		return new CompanyDTOBuilder()
				.id(Util.accordingTo(condition, map.get("id") ,"0"))
				.name(Util.accordingTo(condition, map.get("name"), ""))
				.build();
	}
}
