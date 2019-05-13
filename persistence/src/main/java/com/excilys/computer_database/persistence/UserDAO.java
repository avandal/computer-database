package com.excilys.computer_database.persistence;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.core.model.RoleUser;

@Repository
public class UserDAO extends DAO {
	private Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	private UserDAO() {}
	
	public Optional<RoleUser> getUserRole(String username) {
		logger.info("getUserRole - {}", username);
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
