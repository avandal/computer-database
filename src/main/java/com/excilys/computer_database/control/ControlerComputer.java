package com.excilys.computer_database.control;

public class ControlerComputer {
	
	private static volatile ControlerComputer instance;
	
	private ControlerComputer() {}
	
	public static ControlerComputer getInstance() {
		if (instance == null) {
			synchronized(ControlerComputer.class) {
				if (instance == null) {
					instance = new ControlerComputer();
				}
			}
		}
		return instance;
	}
	
//	public List<Computer> getAll() {
//		ComputerService service = ComputerService.getInstance();
//		return service.getAll();
//	}
}
