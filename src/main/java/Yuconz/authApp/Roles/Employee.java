package Yuconz.authApp.Roles;

import Yuconz.authApp.User;

/**
 * Child class of the User, which dictates the User is an Employee.
 * Employees don't have Higher Access or HR Access.
 */
public class Employee extends User{

	public Employee(String fName, String lName,int id) {
		super(fName, lName, "Employee", false, false, id);
		// TODO Auto-generated constructor stub
	}

}
