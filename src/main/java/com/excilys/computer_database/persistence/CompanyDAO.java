package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

public class CompanyDAO {
	public static final String SELECT_ONE_COMPANY = "select cn.id, cn.name from company cn where cn.id = ?";
	public static final String SELECT_ALL_COMPANIES = "select cn.id, cn.name from company cn";
	
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private static volatile CompanyDAO instance;
	
	private String datasource;
	
	private CompanyDAO(String datasource) {
		this.datasource = datasource;
	}
	
	public static CompanyDAO getInstance(String datasource) {
		if (instance == null) {
			synchronized(CompanyDAO.class) {
				if (instance == null) {
					instance = new CompanyDAO(datasource);
				}
			}
		}
		return instance;
	}
	
	public Optional<Company> getCompanyById(int id) {
		
		Optional<Company> company = Optional.empty();
		
		try (Connection con = ConnectionPool.getInstance(datasource).getDataSource().getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_ONE_COMPANY);) {
			
			stmt.setInt(1, id);
			
			try (ResultSet res = stmt.executeQuery();) {
			
				if (res.next()) {
					company = Optional.of(CompanyMapper.resultSetCompany(res));
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
		
		try (Connection con = ConnectionPool.getInstance(datasource).getDataSource().getConnection();
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
