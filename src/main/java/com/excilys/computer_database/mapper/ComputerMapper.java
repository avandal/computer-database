package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

public abstract class ComputerMapper {

	public ComputerMapper() {}
	
	public static Computer resultSetComputer(ResultSet res) throws SQLException {
		Integer id = res.getInt("ct.id");
		String name = res.getString("ct.name");
		Timestamp introduced = res.getTimestamp("ct.introduced");
		Timestamp discontinued = res.getTimestamp("ct.discontinued");
		Company comp = CompanyMapper.resultSetCompany(res);

		return new Computer(id, name, introduced, discontinued, comp);
	}

}
