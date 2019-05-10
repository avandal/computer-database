package com.excilys.computer_database.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Computer;
import com.excilys.computer_database.core.model.ComputerBuilder;
import com.excilys.computer_database.persistence.ComputerDAO;

public abstract class ComputerMapper {
	private ComputerMapper() {}
	
	public static Computer resultSetComputer(ResultSet res) throws SQLException {
		return new ComputerBuilder()
				.id(res.getInt(ComputerDAO.ID_CT_ALIAS))
				.name(res.getString(ComputerDAO.NAME_CT_ALIAS))
				.introduced(res.getTimestamp("ct.introduced"))
				.discontinued(res.getTimestamp("ct.discontinued"))
				.company(CompanyMapper.resultSetCompany(res))
				.build();
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
