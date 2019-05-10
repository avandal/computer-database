package com.excilys.computer_database.service.service.exception;

public class FailComputerException extends FailException {
	private final static long serialVersionUID = 1L;
	
	public final static String ID_ERROR = "The given id isn't correct.";
	
	public final static String NULL_NAME = "The computer must have a name.";
	
	public final static String OUT_OF_RANGE = "The given year is out of reasonable range.";
	public final static String NOT_A_DATE = "The given date isn't a real date.";
	public final static String WRONG_FORMAT = "Wrong format.";

	public final static String DISC_WITHOUT_INTRO = "If the introduced date is null, the discontinued date must be null too.";
	public final static String DISC_LESS_THAN_INTRO = "The discontinued date must be larger than the introduced one.";

	public final static String TOO_MUCH_DAYS = "There is too much days in the given month.";
	public final static String NOT_A_LEAP_YEAR = "The given date isn't a leap year.";
	
	public final static String INVALID_COMPANY_ID = "The given company id is invalid";
	public final static String NONEXISTENT_COMPANY = "The given company does not exist";
	
	public FailComputerException(ConcernedField concerned, String reason) {
		super(concerned, reason);
	}
}
