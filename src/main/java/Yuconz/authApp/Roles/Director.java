package Yuconz.authApp.Roles;

import Yuconz.authApp.User;

/**
 * Child class of the User, which dictates the User is a Director.
 * Directors have Higher Access and HR Access.
 */
public class Director extends User{

	public Director(String fName, String lName,int id) {
		super(fName, lName, "Director", true, false, id);
	}
}
