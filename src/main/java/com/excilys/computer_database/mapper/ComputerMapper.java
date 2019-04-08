package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.dto.ComputerDTOBuilder;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.util.Util;

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

	public static Optional<Computer> dtoToComputer(ComputerDTO dto) {
		ComputerBuilder builder = new ComputerBuilder();
		
		if (dto.getId() == null || !Util.parseInt(dto.getId()).isPresent()) {
			return Optional.empty();
		}
		builder.id(Util.parseInt(dto.getId()).get());
		
		if (dto.getName() == null || dto.getName().isEmpty()) {
			return Optional.empty();
		}
		builder.name(dto.getName());
		
		if (dto.getIntroduced() == null || !Util.parseTimestamp(dto.getIntroduced()).isPresent()) {
			return Optional.empty();
		}
		builder.introduced(Util.parseTimestamp(dto.getIntroduced()).get());
		
		if (dto.getDiscontinued() == null || !Util.parseTimestamp(dto.getDiscontinued()).isPresent()) {
			return Optional.empty();
		}
		builder.discontinued(Util.parseTimestamp(dto.getDiscontinued()).get());
		
		if (dto.getCompanyId() != null && Util.parseInt(dto.getCompanyId()).isPresent()) {
			builder.company(new Company(Util.parseInt(dto.getCompanyId()).get(), null));
			
			Computer computer = builder.build();
			
			return Optional.of(computer);
		} else {
			return Optional.empty();
		}
	}
	
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
}
