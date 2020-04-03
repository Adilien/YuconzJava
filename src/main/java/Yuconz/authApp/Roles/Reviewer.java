package Yuconz.authApp.Roles;

import Yuconz.authApp.User;

/**
 * Child class of the User, which dictates the User is a Reviewer.
 * Reviewers don't have Higher Access or HR Access.
 */
public class Reviewer extends User{

	public Reviewer(String fName, String lName,int id) {
		super(fName, lName, "Reviewer", false, false, id);
		// TODO Auto-generated constructor stub
	}

}
