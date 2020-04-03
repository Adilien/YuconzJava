package Yuconz.authApp.Roles;

import Yuconz.authApp.User;

/**
 * Child class of the User, which dictates the User is a HR Director.
 * HR Directors have Higher Access and HR Access.
 */

public class HRDirector extends User {

	public HRDirector(String fName, String lName,int id) {
		super(fName, lName, "HR Director", true, true, id);
	}

}
