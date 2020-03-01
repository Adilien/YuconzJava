package authApp;

/**
 * This is essentially a session class for the User. This is the parent class for all the other role Classes. E.g. Director
 */
public class User {

	private String firstName;
	private String lastName;
	private String role;
	private boolean higherAccess;
	
	public User(String fName, String lName, String tempRole, boolean access) {
		
		firstName = fName;
		lastName = lName;
		role = tempRole;
		higherAccess = access;
	}
	
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
}