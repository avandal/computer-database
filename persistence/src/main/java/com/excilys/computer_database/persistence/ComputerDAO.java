package com.excilys.computer_database.persistence;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.core.model.Computer;

@Repository
public class ComputerDAO {
	public static final String ID_CT_ALIAS = "id_computer";
	public static final String NAME_CT_ALIAS = "name_computer";
	public static final String ID_CN_ALIAS = "id_company";
	public static final String NAME_CN_ALIAS = "name_company";
	
	private static final String HQL_LIST = "select ct from Computer as ct left join ct.company as cn";
	private static final String HQL_BY_NAME = "select ct from Computer as ct left join ct.company as cn where ct.name like :ct_name or cn.name like :cn_name";
	
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session;

	private ComputerDAO() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	private void openSession() {
		if(session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
		}
	}
	
	public List<Computer> getAll(String order) {
		logger.info("computerList");
		openSession();
		Query<Computer> query = session.createQuery(HQL_LIST + " " + order, Computer.class);
		
		return query.list();
	}
	
	public Optional<Computer> getById(int computerId) {
		logger.info("getComputerId");
		openSession();
		return Optional.ofNullable(session.get(Computer.class, computerId));
	}
	
	public List<Computer> getByName(String name, String order) {
		logger.info("getByName");
		openSession();
		Query<Computer> query = session.createQuery(HQL_BY_NAME + " " + order, Computer.class);
		query.setParameter("ct_name", "%" + name + "%");
		query.setParameter("cn_name", "%" + name + "%");
		return query.list();
	}
	
	public void create(Computer computer) {
		openSession();
		session.beginTransaction();
		session.save(computer);
		session.getTransaction().commit();
	}
	
	public void update(Computer computer) {
		openSession();
		session.beginTransaction();
		
		Computer tochange = getById(computer.getId()).get();
		
		tochange.setName(computer.getName());
		tochange.setIntroduced(computer.getIntroduced());
		tochange.setDiscontinued(computer.getDiscontinued());
		tochange.setCompany(computer.getCompany());
		
		session.update(tochange);
		session.getTransaction().commit();
	}

	public int deleteComputer(int computerId) {
		openSession();
		session.beginTransaction();
		Computer toDelete = session.get(Computer.class, computerId);
		session.delete(toDelete);
		session.getTransaction().commit();
		return 0;
	}
}
