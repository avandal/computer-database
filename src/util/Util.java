package util;

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
	
	/**
	 * 
	 * @author Alexander van Dalen
	 * @param n Number of repetitions
	 * @param c The String to repeat
	 * @return A String containing a repetition of <b>n</b> Strings (<b>c</b>)
	 */
	private static String repeatNTimes(int n, String c) {
		if (n <= 0) return null;
		
		return new String(new char[n]).replace("\0", c);
		
	}
	
	/**
	 * @author Alexander van Dalen
	 * @param message The messgae you want to box
	 * @return Your message after boxing
	 */
	public static String boxMessage(String message) {
		int length = message.length();
		String ret = "\n";
		ret += " _"+repeatNTimes(length, "_")+"_\n";
		ret += "/ "+repeatNTimes(length, " ")+" \\\n";
		ret += "| "+message+" |\n";
		ret += "\\_"+repeatNTimes(length, "_")+"_/\n";
		
		return ret;
	}
}
