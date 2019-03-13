package view;

public enum PageDescriptor {
	LIST_COMPUTER("Show computer list"),
	LIST_COMPANY("Show company list"),
	SHOW_COMPUTER("Show a computer's details"),
	CREATE_COMPUTER("Create a new computer"),
	UPDATE_COMPUTER("Update an existing computer"),
	DELETE_COMPUTER("Delete an existing computer");
	
	private final String value;
	
	private PageDescriptor(String val) {
		this.value = val;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
