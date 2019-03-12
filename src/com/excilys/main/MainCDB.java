package com.excilys.main;

import java.util.Scanner;

import com.excilys.model.ComputerDB;

public class MainCDB {
	private static Integer userInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose:");
		System.out.println("1 - Show computer list");
		System.out.println("2 - Show company list");
		System.out.println("3 - Show a computer's details");
		System.out.println("  - Quit");
		
		String in = sc.nextLine();
		
		try {
			return Integer.parseInt(in);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		ComputerDB db = new ComputerDB();
		//db.companyList().forEach((c) -> System.out.println(c));
		userInput();
	}
}
