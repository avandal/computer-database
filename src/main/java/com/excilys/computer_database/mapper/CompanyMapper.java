package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.CompanyDTOBuilder;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;

public abstract class CompanyMapper {

	public CompanyMapper() {}

	public static Company resultSetCompany(ResultSet res) throws SQLException {
		return new CompanyBuilder()
				.id(res.getInt("cn.id"))
				.name(res.getString("cn.name"))
				.build();
	}
	
	public static Company dtoToCompany(CompanyDTO dto) {
		return new CompanyBuilder()
				.id(dto.getId())
				.name(dto.getName())
				.build();
	}
	
	public static CompanyDTO companyToDTO(Company company) {
		return new CompanyDTOBuilder()
				.id(company.getId())
				.name(company.getName())
				.build();
	}
}
