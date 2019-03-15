package com.excilys.computer_database.util;

import java.sql.Timestamp;

public abstract class Util {
	
	public static Integer parseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Timestamp parseTimestamp(String input) {
		try {
			return Timestamp.valueOf(input);
		} catch (IllegalArgumentException e) {
			return null;
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
		if (message == null) return message;
		
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
