package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

public class CompanyDAO {
	public static final String SELECT_ONE_COMPANY = "select cn.id, cn.name from company cn where cn.id = ? order by id";
	public static final String SELECT_ALL_COMPANIES = "select cn.id, cn.name from company cn order by id";
	
	public static final String DELETE_COMPUTERS_OF_COMPANY = "delete from computer where company_id = ?";
	public static final String DELETE_COMPANY = "delete from company where id = ?";
	
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	@Autowired
	private DataSource datasource;
	
	public CompanyDAO() {}
	
	public Optional<Company> getCompanyById(int id) {
		
		Optional<Company> company = Optional.empty();
		
		try (Connection con = datasource.getConnection();
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
		
		try (Connection con = datasource.getConnection();
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
	
	public int deleteCompany(Integer id) {
		try (Connection con = datasource.getConnection();
			 PreparedStatement deleteComputerStmt = con.prepareStatement(DELETE_COMPUTERS_OF_COMPANY);
			 PreparedStatement deleteCompanyStmt = con.prepareStatement(DELETE_COMPANY);) {
			
			if (id != null) {
				deleteComputerStmt.setInt(1, id);
				deleteCompanyStmt.setInt(1, id);
			} else {
				deleteComputerStmt.setNull(1, Types.INTEGER);
				deleteCompanyStmt.setNull(1, Types.INTEGER);
			}
			
			int deleteCount = deleteComputerStmt.executeUpdate();
			int status = deleteCompanyStmt.executeUpdate();
			
			if (status == 0) {
				return -1;
			}
			
			return deleteCount;
			
		} catch (SQLException e) {
			logger.error("deleteCompany - SQL error");
			e.printStackTrace();
		}
		
		return -1;
	}
}
