package com.excilys.computer_database.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.core.model.Company;

@Repository
public class CompanyDAO extends DAO {
	public final static String ID_CN_ALIAS = "id_company";
	public final static String NAME_CN_ALIAS = "name_company";
	
	private final static String HQL_SELECT_ALL = "FROM Company";
	
	private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO() {}
	
	public Optional<Company> getCompanyById(int id) {
		logger.info("getCompanyId");
		openSession();
		return Optional.ofNullable(session.get(Company.class, id));
	}
	
	public List<Company> companyList() {
		openSession();
		Query<Company> query = session.createQuery(HQL_SELECT_ALL, Company.class);
		return query.list();
	}
	
	public int deleteCompany(Integer id) {
		openSession();
		Transaction tx = session.beginTransaction();
		Company toDelete = session.get(Company.class, id);
		session.delete(toDelete);
		tx.commit();
		return 1;
	}
}
