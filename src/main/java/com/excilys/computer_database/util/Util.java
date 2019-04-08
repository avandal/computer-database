package com.excilys.computer_database.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Util {
	private static Logger logger = LoggerFactory.getLogger(Util.class);
	
	public static Optional<Integer> parseInt(String input) {
		try {
			return Optional.of(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			logger.warn("parseInt - invalid input: " + input);
			return Optional.empty();
		}
	}
	
	public static Optional<Timestamp> parseTimestamp(String input) {
		try {
			return Optional.of(Timestamp.valueOf(input));
		} catch (IllegalArgumentException e) {
			logger.warn("parseTimestamp - Invalid input: " + input);
			return Optional.empty();
		}
	}
	
	public static Optional<Timestamp> dateToTimestamp(String input) {
		List<String> formats = Arrays.asList("dd/MM/yyyy", "yyyy-MM-dd");
	
		for (String format : formats) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			try {
				LocalDate parsedDate = LocalDate.parse(input, formatter);
				Timestamp time = Timestamp.valueOf(parsedDate.atStartOfDay());
				
				return Optional.of(time);
			} catch (DateTimeParseException e) {
				logger.warn("dateToTimestamp - Invalid input `" + input + "` with this format: " + format);
			}
		}
		
		return Optional.empty();
	}
	
	public static String timestampToDate(Timestamp time) {
		String timeFormat = "yyyy-MM-dd hh:mm:ss.n";
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat);
		LocalDate parsedDate = LocalDate.parse(time.toString(), timeFormatter);
		
		String dateFormat = "dd/MM/yyyy";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
		String ret = parsedDate.format(dateFormatter);
		return ret;
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
