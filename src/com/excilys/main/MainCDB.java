package com.excilys.main;

import java.sql.Timestamp;
import java.util.Scanner;

import com.excilys.model.Computer;
import com.excilys.model.ComputerDB;

public class MainCDB {
	private static ComputerDB cdb = new ComputerDB();
	private static Scanner scan = new Scanner(System.in);
	
	
	private static int parseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	private static Object parseTimestamp(String input) {
		try {
			if ("null".equals(input) || "abort".equals(input)) {
				return input;
			}
			return Timestamp.valueOf(input);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static Integer userGetInt() {
		String input = scan.nextLine();
		
		return parseInt(input);
	}
	
	private static String userGetString() {
		String input = scan.nextLine();
		
		return input;
	}
	
	private static Object userGetTimestamp() {
		String input = scan.nextLine();
		
		return parseTimestamp(input);
	}
	
	private static Integer userInputMenu() {
		System.out.println("Choose:");
		System.out.println("1 - Show computer list");
		System.out.println("2 - Show company list");
		System.out.println("3 - Show a computer's details");
		System.out.println("4 - Create a computer");
		System.out.println("5 - Update a computer");
		System.out.println("6 - Delete a computer");
		System.out.println("7 - Quit");
		
		String input = scan.nextLine();
		
		return parseInt(input);
	}
	
	private static void showComputerDetails(int computerId) {
		Computer computer = cdb.getComputerDetails(computerId);
		if (computer != null) {
			System.out.println(computer);
		} else {
			System.out.println("There is no computer with this id.");
		}
	}
	
	
	
	
	
	private static Object loopOnName(String message) {
		String compName = null;
		
		boolean loop = true;
		
		while (loop) {
			System.out.println(message);
			String name = userGetString();
			
			if ("abort".equals(name)) {
				return Boolean.FALSE;
			}
			if ("null".equals(name)) {
				compName = null;
				loop = false;
			} else if (name == null || name.equals("")) {
				System.out.println("The computer must have a name.");
			} else {
				compName = name;
				loop = false;
			}
		}
		
		return compName;
	}
	
	private static Object loopOnTimestamp(String message) {
		Timestamp compRet = null;
		
		boolean loop = true;
		
		while (loop) {
			System.out.println(message);
			Object time = userGetTimestamp();
			
			if (time instanceof Timestamp) {
				compRet = (Timestamp) time;
				loop = false;
				
			} else if (time instanceof String) {
				String sTime = (String) time;
				if (sTime.equals("null")) {
					compRet = null;
					loop = false;
				} else if (sTime.equals("abort")) {
					return Boolean.FALSE;
				}
			} else {
				System.out.println("Wrong format.");
			}
		}
		
		return compRet;
	}
	
	private static Object loopOnCompanyId(String message) {
		Integer compId = null;
		
		boolean loop = true;
		
		while (loop) {
			System.out.println(message);
			String response = userGetString();
			
			if ("abort".equals(response)) {
				return Boolean.FALSE;
			} else if ("null".equals(response)) {
				compId = null;
				loop = false;
			} else if (response == null || response.equals("")) {
				System.out.println("Company id must have a value (or null)");
			} else {
				return parseInt(response);
			}
		}
		
		return compId;
	}
	
	private static boolean createComputer() {
		String compName;
		Timestamp compIntroduced;
		Timestamp compDiscontinued;
		Integer compCompanyId;
		
		
		Object name = loopOnName("Please give a computer name ('abort' to abort).");
		if (name instanceof Boolean) {
			return true;
		}
		compName = (String) name;
		
		
		Object introduced = loopOnTimestamp("Please give an introduced date ('yyyy-mm-dd hh:mm:ss' format, "
										+ "'abort' to abort, 'null' to set null).");
		if (introduced instanceof Boolean) {
			return true;
		}
		compIntroduced = (Timestamp) introduced;
		
		
		Object discontinued = loopOnTimestamp("Please give a discontinued date ('yyyy-mm-dd hh:mm:ss' format, "
										+ "'abort' to abort, 'null' to set null).");
		if (discontinued instanceof Boolean) {
			return true;
		}
		compDiscontinued = (Timestamp) discontinued;
		
		return false;
	}
	
	private static void deleteComputer(int computerId) {
		int status = cdb.deleteComputer(computerId);
		switch (status) {
		case 1 : System.out.println("Successfully removed!"); break;
		case 0 : System.out.println("There is no computer with this id"); break;
		default : System.err.println("[Problem] More than 1 computer has been deleted with this id"); break;
		}
	}
	
	private static void loopUser() {
		boolean loop = true;
		
		while (loop) {
			switch(userInputMenu()) {
			// List Computer
			case 1 : cdb.computerList().forEach((c) -> System.out.println(c)); break;
			
			// List Company
			case 2 : cdb.companyList().forEach((c) -> System.out.println(c)); break;
			
			// Computer details
			case 3 :
				String message = "Please give a computer id.";
				System.out.println(message);
				int computerId = userGetInt();
				if (computerId <= 0) {
					System.out.println("Invalid id.");
				} else {
					showComputerDetails(computerId);
				}
				break;
				
			case 4 :
				boolean abort = createComputer();

				if (abort) {
					System.out.println("Aborted operation.");
				}
				break;
				
			case 5 :
				// TODO
				break;
				
			case 6 :
				message = "Please give a computer id.";
				System.out.println(message);
				computerId = userGetInt();
				if (computerId <= 0) {
					System.out.println("Invalid id.");
				} else {
					deleteComputer(computerId);
				}
				break;
			case 7 :
				loop = false;
				System.out.println("Goodbye!");
				break;
			
			default :
				System.out.println("Invalid input.");
				break;
			}
			System.out.println();
		}
		
		if (scan != null) {
			scan.close();
		}
	}
	
	public static void main(String[] args) {
		//db.computerList().forEach((c) -> System.out.println(c));
		loopUser();
	}
}
