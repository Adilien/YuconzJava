package Yuconz;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Yuconz.authApp.Auth;
import java.util.ArrayList;
import java.io.FileWriter;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 *
 * @author Adil
 * @version 15/03/2020
 */
public class AuthTest
{
	private Auth auth;

    /**
     * Default constructor for test class AuthLogAttemptTest.
     */
    public AuthTest() {
    	auth = new Auth();
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
    	
    }

    /**
     *
     */
    @Test
	public void connectToDbTest() {
		assertNotNull("Connected To DB", auth.connectToDb());
	}
	/*
	* Testing existing user (Director type)
	*/
	@Test
	public void checkValidUserTest1() {
		assertTrue(auth.checkValidUser("theRealMiles", "password321"));
	}
	/*
	* Testing real existing user (Employee type)
	*/
	@Test
	public void checkValidUserTest2() {
		assertTrue(auth.checkValidUser("johnIsReal", "password123"));
	}
	/*
	* Testing non existing user
	*/
	@Test
	public void checkValidUserTest3() {
		assertFalse(auth.checkValidUser("Tsotne","asjdhakjsd123"));
	}

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {

    }
}
