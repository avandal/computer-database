package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computer_database.model.Company;

public abstract class CompanyMapper {

	public CompanyMapper() {}

	public static Company resultSetCompany(ResultSet res) throws SQLException {
		Integer id = res.getInt("cn.id");
		String name = res.getString("cn.name");

		return new Company(id, name);
	}
}
