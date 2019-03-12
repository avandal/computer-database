package com.excilys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ComputerDB {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	private static HashMap<Integer, Company> memoize_companies = new HashMap<>();
	private static HashMap<Integer, Computer> memoize_computers = new HashMap<>();
	
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
			Date introduced = res.getTimestamp("ct.introduced");
			Date discontinued = res.getTimestamp("ct.discontinued");
			Company comp = resultSetCompany(res);
			
			return new Computer(res.getInt("ct.id"), res.getString("ct.name"), introduced, discontinued, comp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
	@SuppressWarnings("unused")
	private Company getCompanyById(int id) {
		if (memoize_companies.containsKey(id)) {
			return memoize_companies.get(id);
		} else {
			String q = "select cn.id, cn.name from company cn where id = " + id;
			ResultSet res = query(q);
			Company company = resultSetCompany(res);
			memoize_companies.put(id, company);
			return company;
		}
	}
	
	private Computer getComputerById(int id) {
		if (memoize_computers.containsKey(id)) {
			return memoize_computers.get(id);
		} else {
			Computer ret = null;
			
			String q = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
					+ "from computer ct left join company cn on ct.company_id = cn.id where ct.id = "+id;
			ResultSet res = query(q);
			
			try {
				if (res.next()) {
					ret = resultSetComputer(res);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			memoize_computers.put(id, ret);
			return ret;
		}
	}
	
	
	
	
	
	
	
	
	public ArrayList<Company> companyList() {
		ArrayList<Company> ret = new ArrayList<>();
		
		String q = "select cn.id, cn.name from company cn";
		ResultSet res = query(q);
		
		try {
			while(res.next()) {
				int comp_id = res.getInt("cn.id");
				Company company = resultSetCompany(res);
				memoize_companies.put(comp_id, company);
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
					int comp_id = res.getInt("ct.id");
					memoize_computers.put(comp_id, computer);
					computer_list.add(computer);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computer_list;
	}
	
	public void showComputerDetails(int id) {
		System.out.println(getComputerById(id));
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
