package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

public class CompanyDAO {
	public static String SELECT_ONE_COMPANY = "select cn.id, cn.name from company cn where cn.id = ?";
	public static String SELECT_ALL_COMPANIES = "select cn.id, cn.name from company cn";
	
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private static volatile CompanyDAO instance = new CompanyDAO();
	
	private CompanyDAO() {}
	
	public static CompanyDAO getInstance() {
		if (instance == null) {
			synchronized(CompanyDAO.class) {
				if (instance == null) {
					instance = new CompanyDAO();
				}
			}
		}
		return instance;
	}
	
	public Company getCompanyById(int id) {
		
		Company company = null;
		
		try (Connection con = DAO.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_ONE_COMPANY);) {
			
			stmt.setInt(1, id);
			
			try (ResultSet res = stmt.executeQuery();) {
			
				if (res.next()) {
					company = CompanyMapper.resultSetCompany(res);
				}
			}
		} catch (SQLException e) {
			logger.error("getCompanyId - SQL error");
			e.printStackTrace();
		}
		return company;
	}
	
	
	
	
	
	public List<Company> companyList() {
		ArrayList<Company> ret = new ArrayList<>();
		
		try (Connection con = DAO.getConnection();
			 Statement stmt = con.createStatement();
			 ResultSet res = stmt.executeQuery(SELECT_ALL_COMPANIES);) {
			
			while(res.next()) {
				Company company = CompanyMapper.resultSetCompany(res);
				ret.add(company);
			}
		} catch (SQLException e) {
			logger.error("companyList - SQL error, incomplete list");
			e.printStackTrace();
		}
		
		return ret;
	}
}
