package com.excilys.computer_database.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public abstract class Util {
	
	public static Optional<Integer> parseInt(String input) {
		try {
			return Optional.of(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
	
	public static Optional<Timestamp> parseTimestamp(String input) {
		try {
			return Optional.of(Timestamp.valueOf(input));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}
	
	public static Optional<Timestamp> dateToTimestamp(String input) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(input);
			
			return Optional.of(new Timestamp(date.getTime()));
		} catch (ParseException e) {
			try {
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date date = format.parse(input);
				
				return Optional.of(new Timestamp(date.getTime()));
			} catch (ParseException e1) {
				return Optional.empty();
			}
		}
	}
	
	private static int sizeMax(String[] lines) {
		if (lines == null || lines.length <= 0) return 0;
		
		int size = 0;
		for (String line : lines) {
			size = Math.max(size, line.length());
		}
		
		return size;
	}
	
	/**
	 * 
	 * @param n Number of repetitions
	 * @param c The String to repeat
	 * @return A String containing a repetition of <b>n</b> Strings (<b>c</b>)
	 */
	private static String repeatNTimes(int n, String c) {
		if (n <= 0) return "";
		
		return new String(new char[n]).replace("\0", c);
		
	}
	
	/**
	 * 
	 * @param message The message you want to box
	 * @return Your message after boxing
	 */
	public static String boxMessage(String message) {
		if (message == null) return null;
		
		String[] lines = message.split("\n");
		int length = sizeMax(lines);
		
		String ret = "\n";
		ret += " _"+repeatNTimes(length, "_")+"_\n";
		ret += "/ "+repeatNTimes(length, " ")+" \\\n";
		
		for (String line : lines) {
			int gap = length - line.length();
			ret += "| " + line + repeatNTimes(gap, " ") + " |\n";
		}
		
		ret += "\\_"+repeatNTimes(length, "_")+"_/\n";
		
		return ret;
	}
}
