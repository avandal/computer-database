package com.excilys.main;

import java.sql.Timestamp;
import java.util.Scanner;

import com.excilys.control.Controller;
import com.excilys.model.Computer;
import com.excilys.model.ComputerDB;
import com.excilys.persistence.DAO;

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
				int input = parseInt(response);
				if (input <= 0) {
					System.out.println("Invalid company id.");
				} else {
					compId = input;
					loop = false;
				}
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
		
		
		Object companyId = loopOnCompanyId("Please give a company id (an integer, 'abort' to abort, 'null' to set null)");
		if (companyId instanceof Boolean) {
			return true;
		}
		compCompanyId = (Integer) companyId;
		
		
		int status = cdb.createComputer(compName, compIntroduced, compDiscontinued, compCompanyId);
		if (status <= 0) {
			System.out.println("[Problem] Nothing has been created.");
		} else {
			System.out.println("Computer successfully created.");
		}
		
		return false;
	}
	
	private static void updateComputer() {
		String message = "Please give a computer id.";
		System.out.println(message);
		int computerId = userGetInt();
		if (computerId <= 0) {
			System.out.println("Invalid computer id.");
		} else {
			Computer computer = cdb.getComputerDetails(computerId);
			if (computer != null) {

				boolean loop = true;
				
				String compName = computer.getName();
				Timestamp compIntroduced = computer.getIntroduced();
				Timestamp compDiscontinued = computer.getDiscontinued();
				
				while (loop) {
					System.out.println("Now:");
					System.out.println("name = " + computer.getName()
									+ ", introduced = " + computer.getIntroduced()
									+ ", discontinued = " + computer.getDiscontinued());
					System.out.println("Your changes:");
					System.out.println("name = " + compName
									+ ", introduced = " + compIntroduced
									+ ", discontinued = " + compDiscontinued);
					System.out.println("1 - Update name");
					System.out.println("2 - Update introduced");
					System.out.println("3 - Update discontinued");
					System.out.println("4 - Confirm update");
					System.out.println("5 - Quit without update");
					
					int newInput = userGetInt();
					switch (newInput) {
					case 1 :
						Object name = loopOnName("Please give a computer name ('abort' to abort).");
						if (name instanceof Boolean) {
							System.out.println("Aborted.");
						}
						compName = (String) name;
						break;
						
					case 2 :
						Object introduced = loopOnTimestamp("Please give an introduced date ('yyyy-mm-dd hh:mm:ss' format, "
														+ "'abort' to abort, 'null' to set null).");
						if (introduced instanceof Boolean) {
							System.out.println("Aborted.");
						}
						compIntroduced = (Timestamp) introduced;
						break;
						
					case 3 :
						Object discontinued = loopOnTimestamp("Please give a discontinued date ('yyyy-mm-dd hh:mm:ss' format, "
														+ "'abort' to abort, 'null' to set null).");
						if (discontinued instanceof Boolean) {
							System.out.println("Aborted.");
						}
						compDiscontinued = (Timestamp) discontinued;
						break;
					
					case 4 :
						int status = cdb.updateComputer(computerId, compName, compIntroduced, compDiscontinued);
						if (status == 1) {
							System.out.println("Computer successfully updated.");
						} else {
							System.out.println("[Problem] Fail updating computer.");
						}
						loop = false;
						break;
					
					case 5 : 
						System.out.println("Nothing changed.");
						loop = false;
						break;
					
					default : System.out.println("Invalid input"); break;
					}
				}
			} else {
				System.out.println("This computer doesn't exist.");
			}
		}
	}
	
	private static void deleteComputer(int computerId) {
		int status = cdb.deleteComputer(computerId);
		switch (status) {
		case 1 : System.out.println("Computer successfully removed!"); break;
		case 0 : System.out.println("There is no computer with this id"); break;
		default : System.out.println("[Problem] More than 1 computer has been deleted with this id"); break;
		}
	}
	
	@SuppressWarnings("unused")
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
				updateComputer();
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
		//loopUser();
		
		DAO.initConnection();
		
		Controller control = new Controller();
		control.run();
		DAO.closeConnection();
	}
}
