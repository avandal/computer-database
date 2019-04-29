package com.excilys.computer_database.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Company;
import com.excilys.computer_database.core.model.Computer;
import com.excilys.computer_database.core.model.ComputerBuilder;
import com.excilys.computer_database.persistence.ComputerDAO;

public class ComputerMapper implements RowMapper<Computer> {

	public ComputerMapper() {}
	
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return resultSetComputer(rs);
	}
	
	public static Computer resultSetComputer(ResultSet res) throws SQLException {
		return new ComputerBuilder()
				.id(res.getInt(ComputerDAO.ID_CT_ALIAS))
				.name(res.getString(ComputerDAO.NAME_CT_ALIAS))
				.introduced(res.getTimestamp("ct.introduced"))
				.discontinued(res.getTimestamp("ct.discontinued"))
				.company(CompanyMapper.resultSetCompany(res))
				.build();
	}

	public static Optional<Computer> dtoToComputer(ComputerDTO dto) {
		ComputerBuilder builder = new ComputerBuilder();
		
		if (dto.getId() == null || Util.parseInt(dto.getId()).isEmpty()) {
			return Optional.empty();
		}
		builder.id(Util.parseInt(dto.getId()).get());
		
		if (dto.getName() == null || dto.getName().isEmpty()) {
			return Optional.empty();
		}
		builder.name(dto.getName());
		
		if (dto.getIntroduced() != null && Util.dateToTimestamp(dto.getIntroduced()).isEmpty()) {
			return Optional.empty();
		}
		if (dto.getIntroduced() == null) {
			builder.introduced(null);
		} else {
			builder.introduced(Util.dateToTimestamp(dto.getIntroduced()).get());
		}
		
		if (dto.getDiscontinued() != null && Util.dateToTimestamp(dto.getDiscontinued()).isEmpty()) {
			return Optional.empty();
		}
		if (dto.getDiscontinued() == null) {
			builder.discontinued(null);
		} else {
			builder.discontinued(Util.dateToTimestamp(dto.getDiscontinued()).get());
		}
		
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
