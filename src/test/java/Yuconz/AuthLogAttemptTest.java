package Yuconz;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Yuconz.authApp.Auth;

/**
 * 
 *
 * @author Adil
 * @version 15/03/2020
 */
public class AuthLogAttemptTest
{
	private Auth auth;

    /**
     * Default constructor for test class AuthLogAttemptTest.
     */
    public AuthLogAttemptTest() {
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
    public void successfullLogAttemptTest() {
    	auth.logAttempt("success", "pass321", true);
    	assertEquals(auth.getOutcome(), "Successful");
    }
    
    /**
    *
    */
   @Test
   public void failLogAttemptTest() {
   	auth.logAttempt("failure", "pass321", false);
   	assertEquals(auth.getOutcome(), "Failed");
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
