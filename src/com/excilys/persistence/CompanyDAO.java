package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.model.Company;

public class CompanyDAO {
	private static String SELECT_ONE_COMPANY = "select cn.id, cn.name from company cn where cn.id = ?";
	private static String SELECT_ALL_COMPANIES = "select cn.id, cn.name from company cn";
	
	static Company resultSetCompany(ResultSet res) throws SQLException {
		Integer id = res.getInt("cn.id");
		String name = res.getString("cn.name");
		
		return new Company(id, name);
	}
	
	static Company getCompanyById(int id) throws SQLException {
		PreparedStatement stmt = DAO.getConnection().prepareStatement(SELECT_ONE_COMPANY);
		stmt.setInt(1, id);
		ResultSet res = stmt.executeQuery();
		Company company = resultSetCompany(res);
		return company;
	}
	
	
	
	
	
	public static List<Company> companyList() {
		ArrayList<Company> ret = new ArrayList<>();
		
		try {
			ResultSet res = DAO.query(SELECT_ALL_COMPANIES);
			while(res.next()) {
				Company company = resultSetCompany(res);
				ret.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
