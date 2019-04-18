package com.excilys.computer_database.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

@Repository
public class ComputerDAO {
	public static final String ID_CT_ALIAS = "id_computer";
	public static final String NAME_CT_ALIAS = "name_computer";
	public static final String ID_CN_ALIAS = "id_company";
	public static final String NAME_CN_ALIAS = "name_company";
	
	private static final String ID_COMPUTER = "ct.id as " + ID_CT_ALIAS;
	private static final String NAME_COMPUTER = "ct.name as " + NAME_CT_ALIAS;
	private static final String ID_COMPANY = "cn.id as " + ID_CN_ALIAS;
	private static final String NAME_COMPANY = "cn.name as " + NAME_CN_ALIAS;
	
	private static final String SELECT_ALL_COMPUTERS = "select " + ID_COMPUTER + ", " + NAME_COMPUTER + ", ct.introduced, ct.discontinued, "
			+ ID_COMPANY + ", " + NAME_COMPANY + " from computer ct left join company cn on ct.company_id = cn.id";

	private static final String SELECT_COMPUTER_DETAILS = "select " + ID_COMPUTER + ", " + NAME_COMPUTER + ", ct.introduced, ct.discontinued, "
			+ ID_COMPANY + ", " + NAME_COMPANY + " from computer ct left join company cn on ct.company_id = cn.id where ct.id = ?";
	private static final String SELECT_BY_NAME = "select " + ID_COMPUTER + ", " + NAME_COMPUTER + ", ct.introduced, ct.discontinued, "
			+ ID_COMPANY + ", " + NAME_COMPANY + " from computer ct left join company cn on ct.company_id = cn.id where ct.name like ? or cn.name like ?";

	private static final String INSERT_COMPUTER = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
	private static final String DELETE_COMPUTER = "delete from computer where id = ?";
	
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CompanyDAO companyDAO;

	private ComputerDAO() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public List<Computer> computerList(String order) {
		try {
			return jdbcTemplate.query(SELECT_ALL_COMPUTERS + " " + order, new ComputerMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.warn("There is no computer in database");
			return new ArrayList<Computer>();
		}
	}

	public Optional<Computer> getComputerDetails(int computerId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_COMPUTER_DETAILS, new Object[] {computerId}, new ComputerMapper()));
		} catch (EmptyResultDataAccessException e) {
			logger.warn("This computer does not exist : " + computerId);
			return Optional.empty();
		}
		
	}
	
	public List<Computer> getByName(String name, String order) {
		try {
			return jdbcTemplate.query(SELECT_BY_NAME + " " + order, new Object[] {"%" + name + "%", "%" + name + "%"}, new ComputerMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.warn("There is no computer with name like " + name);
			return new ArrayList<Computer>();
		}
	}

	public int createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		if (companyId != null) {
			Optional<Company> company = companyDAO.getCompanyById(companyId);
			
			if (!company.isPresent()) {
				logger.error("'createComputer' method - This company id doesn't exist.");
				return 0;
			}
		}
		
		return jdbcTemplate.update(INSERT_COMPUTER, new Object[] {name, introduced, discontinued, companyId});
	}

	public int updateComputer(int computerId, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		if (companyId != null) {
			Optional<Company> company = companyDAO.getCompanyById(companyId);
			
			if (!company.isPresent()) {
				logger.error("'updateComputer' method - This company id doesn't exist.");
				return 0;
			}
		}
		
		return jdbcTemplate.update(UPDATE_COMPUTER, new Object[] {name, introduced, discontinued, companyId, computerId});
	}

	public int deleteComputer(int computerId) {
		return jdbcTemplate.update(DELETE_COMPUTER, new Object[] {computerId});
	}
}
