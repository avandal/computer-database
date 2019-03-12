package com.excilys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ComputerDB {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
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
	
	private ResultSet query(String q) {
		ResultSet ret = null;
		
		try {
			Statement stmt = co.createStatement();
			ret = stmt.executeQuery(q);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	private int update(String q) {
		int ret = -1;
		
		try {
			Statement stmt = co.createStatement();
			ret = stmt.executeUpdate(q);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	private Company resultSetCompany(ResultSet res) {
		Company comp = null;
		
		try {
			Integer id = res.getInt("cn.id");
			String name = res.getString("cn.name");
			
			comp = new Company(id, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comp;
	}
	
	private Computer resultSetComputer(ResultSet res) {
		try {
			Timestamp introduced = res.getTimestamp("ct.introduced");
			Timestamp discontinued = res.getTimestamp("ct.discontinued");
			Company comp = resultSetCompany(res);
			
			return new Computer(res.getInt("ct.id"), res.getString("ct.name"), introduced, discontinued, comp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
	@SuppressWarnings("unused")
	private Company getCompanyById(int id) {
		String q = "select cn.id, cn.name from company cn where id = " + id;
		ResultSet res = query(q);
		Company company = resultSetCompany(res);
		return company;
	}
	
	
	
	
	
	
	
	
	public ArrayList<Company> companyList() {
		ArrayList<Company> ret = new ArrayList<>();
		
		String q = "select cn.id, cn.name from company cn";
		ResultSet res = query(q);
		
		try {
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
		
		String q = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
				+ "from computer ct left join company cn on ct.company_id = cn.id;";
		ResultSet res = query(q);
		
		try {
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
		
		String q = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
				+ "from computer ct left join company cn on ct.company_id = cn.id where ct.id = "+computerId;
		ResultSet res = query(q);		
		
		try {
			if (res.next()) {
				ret = resultSetComputer(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public int createComputer(String name, Timestamp introduced, Timestamp discontinued, int companyId) {
		String query = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";
		
		try {
			PreparedStatement stmt = co.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setTimestamp(2, introduced);
			stmt.setTimestamp(3, discontinued);
			stmt.setInt(4, companyId);
			
			String qExists = "select cn.id, cn.name from company cn where id = "+companyId;
			ResultSet res = query(qExists);
			
			// Company exists
			if (res.next()) {
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
	
	// TODO
	public void updateComputer() {}
	
	public int deleteComputer(int computerId) {
		String q = "delete from computer where id = "+computerId;
		return update(q);
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
