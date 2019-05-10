package com.excilys.computer_database.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DAO {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Session session;
	
	protected DAO() {}
	
	protected void openSession() {
		if(session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
		}
	}
}
