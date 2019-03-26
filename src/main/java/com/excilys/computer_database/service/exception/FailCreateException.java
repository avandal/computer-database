package com.excilys.computer_database.service.exception;

import com.excilys.computer_database.util.Util;

public class FailCreateException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public FailCreateException(String message) {
		this.message = message;
	}
	
	@Override
	public void printStackTrace() {
		System.out.println(Util.boxMessage(message));
		super.printStackTrace();
	}
}
