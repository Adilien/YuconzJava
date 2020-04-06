package Yuconz.authApp;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import javax.swing.JOptionPane;
import Yuconz.authApp.Roles.*;



/**
 * This class ensures authentication. It connects to the SQL Lite Database and tries to find the user thats currently logging in. 
 * Depending on the user's role within the company then creates an object of the correct role for the user.
 * 
 * @author Tsotne
 *
 */
public class Auth {
	
	private Connection myDb = null;
	private static User currentUser;
	private String outcome;
	
	/**
	* Constructor Method for Auth, which automatically tries to connect to the DB.
	*/
	public Auth() {
		connectToDb();
		outcome = "Failed";
	}
	/**
	 * Logs the successful and unsuccessful login attempts inside AuthorisationLogs.txt File.
	 * @param uName
	 * @param pwd
	 * @param success
	 */
	public void logAttempt(String uName, String pwd, boolean success) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		String formatted = currentTime.format(dtf);
		
		if(success) {
			outcome = "Successful";
			
			try {
				
	            FileWriter writer = new FileWriter("Logs/Authentication_Logs.txt", true);
	            writer.write("\r\n"+"--------------------------------------------------------------------"); 
	            writer.write("\r\n");
	            writer.write(outcome +"\t "+ uName +" \t \t "+ pwd +" \t "+ formatted);
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}else {
			try {
	            FileWriter writer = new FileWriter("Logs/Authentication_Logs.txt", true);
	            writer.write("\r\n"+"--------------------------------------------------------------------"); 
	            writer.write("\r\n");
	            writer.write(outcome +"\t \t "+ uName +" \t \t "+ pwd +" \t "+ formatted);
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
        
	}
	
	/**
	 * Logs Authorisation of the Authenticated User.
	 */
	public void logAuth(boolean isReviewer) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		String formatted = currentTime.format(dtf);
		String auth = "Authorised";
		String notAuth = "Not Authorised";
		String higherAccess;
		String hrAccess;
		
		String name = currentUser.getFirstName()+" "+currentUser.getLastName();
		String role = currentUser.getRole();
		
		if(isReviewer) {
			role = "Reviewer";
		}
		
		if(currentUser.getAccess()) {
			higherAccess = auth;
		}else {
			higherAccess = notAuth;
		}
		
		if(currentUser.getHrAccess()) {
			hrAccess = auth;
		}else {
			hrAccess = notAuth;
		}
		
		try {
            FileWriter writer = new FileWriter("Logs/Authorisation_Logs.txt", true);
            writer.write("\r\n"+"------------------------------------------------------------------------------------------------------------"); 
            writer.write("\r\n");
            
            if(currentUser.getAccess()) {
            	writer.write(name + " \t  "+ role + " \t \t "+ higherAccess + " \t \t "+ hrAccess+ " \t \t " + formatted);
            }else {
            	writer.write(name + " \t  "+ role + " \t \t "+ higherAccess + " \t "+ hrAccess+ " \t " + formatted);
            }
            
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
	}

	public void toggleLog(boolean input) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		String formatted = currentTime.format(dtf);
		String auth = "Authorised";
		String notAuth = "Not Authorised";
		String higherAccess;
		String hrAccess;
		
		String name = currentUser.getFirstName()+" "+currentUser.getLastName();
		String role = currentUser.getRole();
		
		if(input) {
			higherAccess = auth;
		}else {
			higherAccess = notAuth;
		}
		
		if(currentUser.getHrAccess()) {
			hrAccess = auth;
		}else {
			hrAccess = notAuth;
		}
		
		try {
            FileWriter writer = new FileWriter("Logs/Authorisation_Logs.txt", true);
            writer.write("\r\n"+"------------------------------------------------------------------------------------------------------------"); 
            writer.write("\r\n");
            
            if(input) {
            	writer.write(name + " \t  "+ role + " \t \t "+ higherAccess + " \t \t "+ hrAccess+ " \t \t " + formatted);
            }else {
            	writer.write(name + " \t  "+ role + " \t \t "+ higherAccess + " \t "+ hrAccess+ " \t \t " + formatted);
            }
            
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	/**
	* Connect to the LOCAL Database
	*/
	public Connection connectToDb() {
		
		// load the SQLite-JDBC driver using the current class loader
		try {
			Class.forName("org.sqlite.JDBC");
			myDb = DriverManager.getConnection("jdbc:sqlite:Auth.db");
		}catch(Exception e){
				JOptionPane.showMessageDialog(null,
				    "Cannot Load DB Dirver",
				    "Error",
				JOptionPane.ERROR_MESSAGE);
						
		}
		
		try {
		      Statement statement = myDb.createStatement();
		      statement.setQueryTimeout(30);  // set timeout to 30 seconds.
		      return myDb;
		}catch(Exception e){
			
			JOptionPane.showMessageDialog(null,
	    		    "Cannot Connect to DB",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
			
			return null;
		}
	}
	/**
	* Checks if the user is inside the database or not. 
	* If the user exists, creates the correct currentUser object with the data provided in the Database
	* 
	*/
	public boolean checkValidUser(String uName, String pwd) {
		
		String sql = "select * from Employees where username='"+uName+"' and password='"+pwd+"'";
		
		try(Connection conn = myDb;
				Statement stmt = conn.createStatement();
				ResultSet rs  = stmt.executeQuery(sql)){
			
			if(rs.getString("username").contentEquals(uName) && rs.getString("password").contentEquals(pwd)) {
				
				String foundRole =  rs.getString("role");
				switch(foundRole) {
				case "Director":
					currentUser = new Director(rs.getString("fName"),rs.getString("sName"),rs.getInt("id"));
					break;
				case "HR Employee":
					currentUser = new HREmployee(rs.getString("fname"),rs.getString("sName"),rs.getInt("id"));
					break;
				case "HR Director":
					currentUser = new HRDirector(rs.getString("fname"),rs.getString("sName"),rs.getInt("id"));
					break;
				case "Manager":
					currentUser = new Manager(rs.getString("fname"),rs.getString("sName"),rs.getInt("id"));
					break;
				case "Employee":
					currentUser = new Employee(rs.getString("fname"),rs.getString("sName"),rs.getInt("id"));
					break;
				}
				return true;
			}
			return false;
		}catch(SQLException e){
			return false;
		}
	}

	/**
	 * Getter method for Current User
	 * @return
	 */
	public static User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Get the current outcome of login.
	 * @return outcome
	 */
	public String getOutcome() {
		return outcome;
	}

}
