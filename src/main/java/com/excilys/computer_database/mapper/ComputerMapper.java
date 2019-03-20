package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;

public abstract class ComputerMapper {

	public ComputerMapper() {}
	
	public static Computer resultSetComputer(ResultSet res) throws SQLException {
		return new ComputerBuilder()
				.id(res.getInt("ct.id"))
				.name(res.getString("ct.name"))
				.introduced(res.getTimestamp("ct.introduced"))
				.discontinued(res.getTimestamp("ct.discontinued"))
				.company(CompanyMapper.resultSetCompany(res))
				.build();
	}

	public static Computer dtoToComputer(ComputerDTO dto) {
		return new ComputerBuilder()
				.id(dto.getId())
				.name(dto.getName())
				.introduced(Timestamp.valueOf(dto.getIntroduced()))
				.discontinued(Timestamp.valueOf(dto.getDiscontinued()))
				.company(new Company(dto.getCompanyId(), dto.getCompanyName()))
				.build();
	}
}
