package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerDAO {
	private static final String SELECT_ALL_COMPUTERS = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
													+ "from computer ct left join company cn on ct.company_id = cn.id";

	private static final String SELECT_COMPUTER_DETAILS = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
													+ "from computer ct left join company cn on ct.company_id = cn.id where ct.id = ?";
	
	private static final String INSERT_COMPUTER = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER = "update computer set name = ?, introduced = ?, discontinued = ? where id = ?";
	private static final String DELETE_COMPUTER = "delete from computer where id = ?";
	
	public ComputerDAO() {}
	
	static Computer resultSetComputer(ResultSet res) throws SQLException {
		Integer id = res.getInt("ct.id");
		String name = res.getString("ct.name");
		Timestamp introduced = res.getTimestamp("ct.introduced");
		Timestamp discontinued = res.getTimestamp("ct.discontinued");
		Company comp = CompanyDAO.resultSetCompany(res);
		
		return new Computer(id, name, introduced, discontinued, comp);		
	}
	
	
	
	
	
	public static ArrayList<Computer> computerList() {
		ArrayList<Computer> computer_list = new ArrayList<>();
		
		try {
			ResultSet res = DAO.query(SELECT_ALL_COMPUTERS);
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
	
	public static Computer getComputerDetails(int computerId) {
		Computer ret = null;
		
		try {
			PreparedStatement stmt = DAO.getConnection().prepareStatement(SELECT_COMPUTER_DETAILS);
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
	
	public static int createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		try {
			PreparedStatement stmt = DAO.getConnection().prepareStatement(INSERT_COMPUTER);
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
			
			int status = stmt.executeUpdate();
			
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static int updateComputer(int computerId, String name, Timestamp introduced, Timestamp discontinued) {
		try {
			PreparedStatement stmt = DAO.getConnection().prepareStatement(UPDATE_COMPUTER);
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
	
	public static int deleteComputer(int computerId) {
		try {
			PreparedStatement stmt = DAO.getConnection().prepareStatement(DELETE_COMPUTER);
			stmt.setInt(1, computerId);
			
			int status = stmt.executeUpdate();
			
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
}
