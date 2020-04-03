package Yuconz.authApp.Roles;

import Yuconz.authApp.User;

/**
 * Child class of the User, which dictates the User is a HR Employee
 * HR Employees have Higher Access and HR Access.
 */
public class HREmployee extends User {

	public HREmployee(String fName, String lName,int id) {
		super(fName, lName, "HR Employee", true, true, id);
		// TODO Auto-generated constructor stub
	}
	
}
