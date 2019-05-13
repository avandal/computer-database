package com.excilys.computer_database.service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.binding.dto.RoleUserDTO;
import com.excilys.computer_database.binding.mapper.RoleUserMapper;
import com.excilys.computer_database.core.model.Role;
import com.excilys.computer_database.core.model.RoleUser;
import com.excilys.computer_database.core.model.User;
import com.excilys.computer_database.persistence.UserDAO;
import com.excilys.computer_database.service.service.exception.ConcernedField;
import com.excilys.computer_database.service.service.exception.FailUserException;

@Service
public class UserService {
	@Autowired
	private UserDAO dao;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Optional<RoleUserDTO> getUserRole(String username) {
		logger.info("getUserRole - {}", username);
		
		Optional<RoleUser> userRole = dao.getUserRole(username);
		if (userRole.isPresent()) {
			return Optional.of(RoleUserMapper.userRoleToDTO(userRole.get()));
		}
		
		return Optional.empty();
	}
	
	public int createUser(String username, String password) throws FailUserException {
		logger.info("createUser - [{}, {}]", username, password);
		
		if (username == null || username.isEmpty()) {
			logger.error("Empty username");
			throw new FailUserException(ConcernedField.USERNAME, FailUserException.EMPTY_USERNAME);
		}
		
		if (password == null || password.isEmpty()) {
			logger.error("Empty password");
			throw new FailUserException(ConcernedField.PASSWORD, FailUserException.EMPTY_PASSWORD);
		}
		
		Optional<RoleUser> optUserRole = dao.getUserRole(username);
		if (optUserRole.isPresent()) {
			logger.error("This username already exists");
			throw new FailUserException(ConcernedField.USERNAME, FailUserException.USERNAME_ALREADY_EXISTS);
		}
		
		return dao.createUser(
			new RoleUser(
				new User(
					username,
					new BCryptPasswordEncoder().encode(password),
					true
				),
				Role.GUEST.getValue()
			)
		);
	}
}
