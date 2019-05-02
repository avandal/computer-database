package com.excilys.computer_database.persistence;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.core.model.RoleUser;

@Repository
public class UserDAO {
	private static Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session;
	
	private UserDAO() {}
	
	private void openSession() {
		if(session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
		}
	}
	
	public Optional<RoleUser> getUserRole(String username) {
		logger.info("getUserRole - " + username);
		openSession();
		return Optional.ofNullable(session.get(RoleUser.class, username));
	}
	
	public int createUser(RoleUser role) {
		openSession();
		session.beginTransaction();
		
		session.saveOrUpdate(role.getUser());
		session.saveOrUpdate(role);
		session.getTransaction().commit();
		
		return 1;
	}
}
