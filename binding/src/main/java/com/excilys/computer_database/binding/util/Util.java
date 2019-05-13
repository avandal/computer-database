package com.excilys.computer_database.binding.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Util {
	private static Logger logger = LoggerFactory.getLogger(Util.class);
	
	private Util() {}
	
	public static Optional<Integer> parseInt(String input) {
		try {
			return Optional.of(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			logger.warn(String.format("parseInt - invalid input: %s", input));
			return Optional.empty();
		}
	}
	
	public static Optional<Timestamp> dateToTimestamp(String input) {
		if (input == null || input.trim().equals("")) {
			logger.warn("Empty input");
			return Optional.empty();
		}
		List<String> formats = Arrays.asList("dd/MM/yyyy", "yyyy-MM-dd");
	
		for (String format : formats) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			try {
				LocalDate parsedDate = LocalDate.parse(input, formatter);
				Timestamp time = Timestamp.valueOf(parsedDate.atStartOfDay());
				
				return Optional.of(time);
			} catch (DateTimeParseException e) {
				logger.warn(String.format("dateToTimestamp - Invalid input `%s` with this format: %s", input, format));
			}
		}
		
		return Optional.empty();
	}
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}
	
	public static <T> T accordingTo(Predicate<T> condition, T initialValue, T defaultValue) {
		if (!condition.test(initialValue)) {
			return defaultValue;
		}
		
		return initialValue;
	}
	
	public static <T> T extract(Optional<T> opt) {
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	public static String timestampToDate(Timestamp time) {
		String timeFormat = "yyyy-MM-dd HH:mm:ss.n";
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
	
	private static String toString(Object object) {
		if (!(object instanceof String)) {
			return object.toString();
		} else {
			return (String) object;
		}
	}
	
	private static String boxMessage(String message) {
		String[] lines = message.split("\n");
		
		int k = 0;
		
		int length = sizeMax(lines);
		
		StringBuilder ret = new StringBuilder();
		ret.append(" _").append(repeatNTimes(length, "_")).append("_\n");
		
		if (lines[0].equals(" "+repeatNTimes(length - 2, "_"))) {
			k++;
			ret.append("/ ").append(lines[0]).append("  \\\n");
		} else {
			ret.append("/ ").append(repeatNTimes(length, " ")).append(" \\\n");
		}
		
		
		for (int i = k; i < lines.length; i++) {
			String line = lines[i];
			
			int gap = length - line.length();
			ret.append("| ").append(line).append(repeatNTimes(gap, " ")).append(" |\n");
		}
		
		ret.append("\\_").append(repeatNTimes(length, "_")).append("_/\n");
		
		return ret.toString();
	}
	
	/**
	 * 
	 * @param object The message you want to box
	 * @return Your message after boxing
	 */
	public static String boxMessage(Object object) {
		if (object == null) return null;
		
		return boxMessage(toString(object));
	}
	
	public static String boxMessage(Object message, int n) {
		if (message == null) return null;
		
		String ret = toString(message);
		
		ret = boxMessage(ret);
		while (--n > 0) {
			ret = boxMessage(ret);
		}
		return "\n" + ret;
	}
}
