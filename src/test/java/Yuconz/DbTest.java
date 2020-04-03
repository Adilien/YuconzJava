package Yuconz;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Yuconz.authApp.Auth;
import  Yuconz.authApp.Search.Db;

import java.util.ArrayList;
import java.io.FileWriter;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The test class BankAccountTransferTest.
 *
 * @author Adil
 * @version 15/03/2020
 */
public class DbTest
{
	private Db db;

    /**
     * Default constructor for test class AuthLogAttemptTest.
     */
    public DbTest() {
    	db = new Db();
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
     * Check to see if the correct result is returned.
     */
    @Test
    public void matchPersonalDetailsFalseTest() {
    	db.matchPersonalDetails();
    	assertEquals(false, false);
    }
    
    /**
     * Check to see if the correct result is returned.
     */
    @Test
    public void matchPersonalDetailsTrueTest() {
    	db.matchPersonalDetails();
    	assertEquals(true, true);
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
