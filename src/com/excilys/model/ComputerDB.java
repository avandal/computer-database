package com.excilys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

public class ComputerDB {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	private static final String SELECT_ONE_COMPANY = "select cn.id, cn.name from company cn where cn.id = ?";
	
	private static final String SELECT_ALL_COMPANIES = "select cn.id, cn.name from company cn";
	private static final String SELECT_ALL_COMPUTERS = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
													+ "from computer ct left join company cn on ct.company_id = cn.id";
	
	private static final String SELECT_COMPUTER_DETAILS = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
														+ "from computer ct left join company cn on ct.company_id = cn.id where ct.id = ?";
	
	private static final String INSERT_COMPUTER = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER = "update computer set name = ?, introduced = ?, discontinued = ? where id = ?";
	private static final String DELETE_COMPUTER = "delete from computer where id = ?";
	
	private static Connection co = null;
	
	public ComputerDB() {
		try {
			Class.forName(DRIVER);
			co = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private ResultSet query(String q) throws SQLException {
		Statement stmt = co.createStatement();
		ResultSet ret = stmt.executeQuery(q);
		
		return ret;
	}
	
	private Company resultSetCompany(ResultSet res) throws SQLException {
		Integer id = res.getInt("cn.id");
		String name = res.getString("cn.name");
		
		return new Company(id, name);
	}
	
	private Computer resultSetComputer(ResultSet res) throws SQLException {
		Integer id = res.getInt("ct.id");
		String name = res.getString("ct.name");
		Timestamp introduced = res.getTimestamp("ct.introduced");
		Timestamp discontinued = res.getTimestamp("ct.discontinued");
		Company comp = resultSetCompany(res);
		
		return new Computer(id, name, introduced, discontinued, comp);		
	}
	
	
	@SuppressWarnings("unused")
	private Company getCompanyById(int id) throws SQLException {
		PreparedStatement stmt = co.prepareStatement(SELECT_ONE_COMPANY);
		stmt.setInt(1, id);
		ResultSet res = stmt.executeQuery();
		Company company = resultSetCompany(res);
		return company;
	}
	
	
	
	
	
	
	
	
	public ArrayList<Company> companyList() {
		ArrayList<Company> ret = new ArrayList<>();
		
		try {
			ResultSet res = query(SELECT_ALL_COMPANIES);
			while(res.next()) {
				Company company = resultSetCompany(res);
				ret.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public ArrayList<Computer> computerList() {
		ArrayList<Computer> computer_list = new ArrayList<>();
		
		try {
			ResultSet res = query(SELECT_ALL_COMPUTERS);
			while(res.next()) {
				Computer computer = resultSetComputer(res);
				
				if (computer != null) {
					computer_list.add(computer);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computer_list;
	}
	
	public Computer getComputerDetails(int computerId) {
		Computer ret = null;
		
		try {
			PreparedStatement stmt = co.prepareStatement(SELECT_COMPUTER_DETAILS);
			stmt.setInt(1, computerId);
			
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				ret = resultSetComputer(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public int createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		try {
			PreparedStatement stmt = co.prepareStatement(INSERT_COMPUTER);
			stmt.setString(1, name);
			
			if (introduced != null) {
				stmt.setTimestamp(2, introduced);
			} else {
				stmt.setNull(2, Types.TIMESTAMP);
			}
			
			if (discontinued != null) {
				stmt.setTimestamp(3, discontinued);
			} else {
				stmt.setNull(3, Types.TIMESTAMP);
			}
			if (companyId != null) {
				stmt.setInt(4, companyId);
			} else {
				stmt.setNull(4, Types.INTEGER);
			}
			
			PreparedStatement stmtExists = co.prepareStatement(SELECT_ONE_COMPANY);
			stmtExists.setInt(1, companyId);
			
			ResultSet res = stmtExists.executeQuery();
			
			// Company exists
			if (res.next() || companyId == null) {
				int status = stmt.executeUpdate();
				return status;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int updateComputer(int computerId, String name, Timestamp introduced, Timestamp discontinued) {
		try {
			PreparedStatement stmt = co.prepareStatement(UPDATE_COMPUTER);
			stmt.setString(1, name);
			
			if (introduced != null) {
				stmt.setTimestamp(2, introduced);
			} else {
				stmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			
			if (discontinued != null) {
				stmt.setTimestamp(3, discontinued);
			} else {
				stmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			stmt.setInt(4, computerId);
			
			int status = stmt.executeUpdate();
			
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int deleteComputer(int computerId) {
		try {
			PreparedStatement stmt = co.prepareStatement(DELETE_COMPUTER);
			stmt.setInt(1, computerId);
			
			int status = stmt.executeUpdate();
			
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	
	
	public void finalize() {
		try {
			if (co != null && !co.isClosed()) {
				co.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
