package com.excilys.computer_database.service.exception;

import com.excilys.computer_database.util.Util;

public class FailCreateException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final String NULL_NAME = "The computer must have a name.";
	
	public static final String OUT_OF_RANGE = "The given year is out of reasonable range.";
	public static final String NOT_A_DATE = "The given date isn't a real date.";
	public static final String WRONG_FORMAT = "Wrong format.";

	public static final String DISC_WITHOUT_INTRO = "If the introduced date is null, the discontinued date must be null too.";
	public static final String DISC_LESS_THAN_INTRO = "The discontinued date must be larger than the introduced one.";

	public static final String TOO_MUCH_DAYS = "There is too much days in the given month.";
	public static final String NOT_A_LEAP_YEAR = "The given date isn't a leap year.";
	
	public static final String INVALID_COMPANY_ID = "The given company id is invalid";
	public static final String NONEXISTENT_COMPANY = "The given company does not exist";
	
	private ConcernedField concerned;
	private String reason;
	
	public FailCreateException(ConcernedField concerned, String reason) {
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
