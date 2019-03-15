package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

public class ComputerDAO {
	private static final String SELECT_ALL_COMPUTERS = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
			+ "from computer ct left join company cn on ct.company_id = cn.id";

	private static final String SELECT_COMPUTER_DETAILS = "select cn.id, cn.name, ct.id, ct.name, ct.introduced, ct.discontinued "
			+ "from computer ct left join company cn on ct.company_id = cn.id where ct.id = ?";

	private static final String INSERT_COMPUTER = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER = "update computer set name = ?, introduced = ?, discontinued = ? where id = ?";
	private static final String DELETE_COMPUTER = "delete from computer where id = ?";
	
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

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

		try (Connection con = DAO.getConnection();
			 Statement stmt = con.createStatement();
			 ResultSet res = stmt.executeQuery(SELECT_ALL_COMPUTERS);) {
			
			while (res.next()) {
				Computer computer = resultSetComputer(res);

				if (computer != null) {
					computer_list.add(computer);
				}
			}
		} catch (SQLException e) {
			logger.error("computerList - SQL error, incomplete list");
			e.printStackTrace();
		}

		return computer_list;
	}

	public static Computer getComputerDetails(int computerId) {
		Computer ret = null;

		try (Connection con = DAO.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_COMPUTER_DETAILS);) {
			
			stmt.setInt(1, computerId);
			
			try (ResultSet res = stmt.executeQuery();) {

				if (res.next()) {
					ret = resultSetComputer(res);
				}
			}
		} catch (SQLException e) {
			logger.error("getComputerDetails - SQL error");
			e.printStackTrace();
		}

		return ret;
	}

	public static int createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		try (Connection con = DAO.getConnection();
			 PreparedStatement stmt = con.prepareStatement(INSERT_COMPUTER);) {
			
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
				Company company = CompanyDAO.getCompanyById(companyId);
				if (company != null) {
					stmt.setInt(4, companyId);
				} else {
					logger.error("'createComputer' method - This company id doesn't exist.");
					return -2;
				}
			} else {
				stmt.setNull(4, Types.INTEGER);
			}

			int status = stmt.executeUpdate();

			return status;
		} catch (SQLException e) {
			logger.error("SQL error");
			e.printStackTrace();
		}

		return -1;
	}

	public static int updateComputer(int computerId, String name, Timestamp introduced, Timestamp discontinued) {
		try (Connection con = DAO.getConnection();
			 PreparedStatement stmt = con.prepareStatement(UPDATE_COMPUTER);) {
			
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
			logger.error("updateComputer - SQL error");
			e.printStackTrace();
		}
		return -1;
	}

	public static int deleteComputer(int computerId) {
		try (Connection con = DAO.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_COMPUTER);) {
			
			stmt.setInt(1, computerId);

			int status = stmt.executeUpdate();

			return status;
		} catch (SQLException e) {
			logger.error("deleteComputer - SQL error");
			e.printStackTrace();
		}

		return -1;
	}
}
