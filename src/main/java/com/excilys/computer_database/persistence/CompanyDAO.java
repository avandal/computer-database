package com.excilys.computer_database.persistence;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

@Repository
public class CompanyDAO {
	public static final String ID_CN_ALIAS = "id_company";
	public static final String NAME_CN_ALIAS = "name_company";
	
	private static final String ID_COMPANY = "cn.id as " + ID_CN_ALIAS;
	private static final String NAME_COMPANY = "cn.name as " + NAME_CN_ALIAS;
	
	public static final String SELECT_ONE_COMPANY = "select " + ID_COMPANY + ", " + NAME_COMPANY + " from company cn where cn.id = ? order by id";
	public static final String SELECT_ALL_COMPANIES = "select " + ID_COMPANY + ", " + NAME_COMPANY + " from company cn order by id";
	
	public static final String DELETE_COMPUTERS_OF_COMPANY = "delete from computer where company_id = ?";
	public static final String DELETE_COMPANY = "delete from company where id = ?";
	
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public CompanyDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Optional<Company> getCompanyById(int id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_ONE_COMPANY, new Object[] {id}, new CompanyMapper()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	
	public List<Company> companyList() {
		return jdbcTemplate.query(SELECT_ALL_COMPANIES, new CompanyMapper());
	}
	
	public int deleteCompany(Integer id) {
		try {
			int deleteCount = jdbcTemplate.update(DELETE_COMPUTERS_OF_COMPANY, new Object[] {id});
			int status = jdbcTemplate.update(DELETE_COMPANY, new Object[] {id});
			
			if (status == 0) {
				return -1;
			}
			
			return deleteCount;
		} catch (DataAccessException e) {
			logger.error("Error when deleting company " + id);
			return -1;
		}
	}
}
