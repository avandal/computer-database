package com.excilys.computer_database.service.service.exception;

public class FailUserException extends FailException {
	private static final long serialVersionUID = 1L;
	
	public static final String EMPTY_USERNAME = "The username cannot be empty or null";
	public static final String EMPTY_PASSWORD = "The password cannot be empty or null";
	
	public static final String USERNAME_ALREADY_EXISTS = "The given username is already used for another account";
	
	public FailUserException(ConcernedField concerned, String reason) {
		super(concerned, reason);
	}
	

}
