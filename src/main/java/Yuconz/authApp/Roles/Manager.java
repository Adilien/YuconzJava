package Yuconz.authApp.Roles;

import Yuconz.authApp.User;

/**
 * Child class of the User, which dictates the User is a Manager.
 * Managers don't have Higher Access or HR Access.
 */
public class Manager extends User {

	public Manager(String fName, String lName,int id) {
		super(fName, lName, "Manager", false, false, id);
		// TODO Auto-generated constructor stub
	}

}
