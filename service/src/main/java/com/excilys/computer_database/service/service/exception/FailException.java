package com.excilys.computer_database.service.service.exception;

import com.excilys.computer_database.binding.util.Util;

public abstract class FailException extends Exception {
	private static final long serialVersionUID = 1L;
	
	protected final ConcernedField concerned;
	protected final String reason;
	
	public FailException(ConcernedField concerned, String reason) {
		this.concerned = concerned;
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
	
	public ConcernedField getConcerned() {
		return concerned;
	}
	
	@Override
	public void printStackTrace() {
		System.out.println(Util.boxMessage(concerned + ": " + reason));
		super.printStackTrace();
	}
}
