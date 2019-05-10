package com.excilys.computer_database.service.service.exception;

public class FailUserException extends FailException {
	private final static long serialVersionUID = 1L;
	
	public final static String EMPTY_USERNAME = "The username cannot be empty or null";
	public final static String EMPTY_PASSWORD = "The password cannot be empty or null";
	
	public final static String USERNAME_ALREADY_EXISTS = "The given username is already used for another account";
	
	public FailUserException(ConcernedField concerned, String reason) {
		super(concerned, reason);
	}
	

}
