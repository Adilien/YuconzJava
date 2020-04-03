package Yuconz.authApp;

/**
 * This is essentially an object blueprint for a User. This is the parent class for all the other role Classes. E.g. Director
 */
public class User {

	private String firstName;
	private String lastName;
	private String role;
	private boolean higherAccess;	// If the user possess Higher Access (Director or HR).
	private boolean hrAccess;	//If the user belongs to HR or not
	private int id;
	
	public User(String fName, String lName, String tempRole, boolean access, boolean hr ,int staffId) {
		
		firstName = fName;
		lastName = lName;
		role = tempRole;
		higherAccess = access;
		id = staffId;
		hrAccess = hr;
	}
	
	
	/**
	 * 
	 * Getters and Setters are all below.
	 * 
	 */
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getRole() {
		return role;
	}
	public boolean getAccess() {
		return higherAccess;
	}
	public boolean getHrAccess() {
		return hrAccess;
	}
	
	public void setAccess(boolean newAccess) {
		higherAccess = newAccess;
	}
	public int getId() {
		return id;
	}
	public void setId(int input) {
		id = input;
	}
	public void setFirstName(String input) {
		firstName = input;
	}
	public void setLastName(String input) {
		lastName = input;
	}
	public void setRole(String input) {
		role = input;
	}
	public void setHrAccess(boolean input) {
		hrAccess = input;
	}
}
