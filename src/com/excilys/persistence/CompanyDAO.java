package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;

public class CompanyDAO {
	private static String SELECT_ONE_COMPANY = "select cn.id, cn.name from company cn where cn.id = ?";
	private static String SELECT_ALL_COMPANIES = "select cn.id, cn.name from company cn";
	
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	static Company resultSetCompany(ResultSet res) throws SQLException {
		Integer id = res.getInt("cn.id");
		String name = res.getString("cn.name");
		
		return new Company(id, name);
	}
	
	static Company getCompanyById(int id) {
		Company company = null;
		
		try (Connection con = DAO.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_ONE_COMPANY);) {
			
			stmt.setInt(1, id);
			
			try (ResultSet res = stmt.executeQuery();) {
			
				if (res.next()) {
					company = resultSetCompany(res);
				}
			}
		} catch (SQLException e) {
			logger.error("getCompanyId - SQL error");
			e.printStackTrace();
		}
		return company;
	}
	
	
	
	
	
	public static List<Company> companyList() {
		ArrayList<Company> ret = new ArrayList<>();
		
		try (Connection con = DAO.getConnection();
			 Statement stmt = con.createStatement();
			 ResultSet res = stmt.executeQuery(SELECT_ALL_COMPANIES);) {
			
			while(res.next()) {
				Company company = resultSetCompany(res);
				ret.add(company);
			}
		} catch (SQLException e) {
			logger.error("companyList - SQL error, incomplete list");
			e.printStackTrace();
		}
		
		return ret;
	}
}
