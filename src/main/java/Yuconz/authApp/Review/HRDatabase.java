package Yuconz.authApp.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import Yuconz.authApp.Auth;
import Yuconz.authApp.User;
import Yuconz.authApp.Search.Db;

/**
 * This class interacts with the database when concerning anything to do with the Review Process.
 * @author Tsotne
 *
 */
public class HRDatabase {
	
	
	private Connection myDb = null;
	private ArrayList<ArrayList <String>> result = new ArrayList<ArrayList<String>>();
	private String[][] data;
	
	private static User reviewer1 = new User(null, null, null, false, false, 0);
	private static User reviewer2 = new User(null, null, null, false, false, 0);
	
	
	public HRDatabase() {
		connectToDb();
	}

	public Connection connectToDb() {
		try {
			 // load the SQLite-JDBC driver using the current class loader
		      Class.forName("org.sqlite.JDBC");
		      myDb = DriverManager.getConnection("jdbc:sqlite:Auth.db");
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
	
	public void getAllReviewers() {
		availableReviewers();
		convertData();
	}
	
	/**
	 * Get all available reviewers from the database
	 */
	public void availableReviewers() {
		String sql = "select id,fName,sName,role from Employees where id != '"+Db.getSelectedUser().getId()+"'";
		
		connectToDb();
		try(Connection conn = myDb;
				Statement stmt = conn.createStatement();
				ResultSet rs  = stmt.executeQuery(sql)){
			int columnCount = rs.getMetaData().getColumnCount();
			while(rs.next())
			{
			    ArrayList<String> row = new  ArrayList<String>();
			    for (int i=0; i <columnCount ; i++)
			    {
			       row.add( rs.getString(i + 1));
			    }
			    result.add(row);
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
	    		    "Cannot connect to the Database",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Converts ArrayLists into a 2D Array, that can be used by the JTable.
	 */
	public void convertData(){
		data = result.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
	}

	public String[][] getReviewers() {
		return data;
	}
	public boolean checkIfBeingReviewed() {
		int userId = Auth.getCurrentUser().getId();
		int foundId;
		String sql = "select targetid from Reviews where targetid='"+userId+"'and Completed='0'";
		connectToDb();
		try(Connection conn = myDb;
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(sql)){
			foundId = rs.getInt("id");
				
		}catch(SQLException e){
			
			return false;
		}
		
		if(foundId == userId) {
			return true;
		}else {
			return false;
		}
	}
	public void createReviewDoc() {
		int targetId = Db.getSelectedUser().getId();

		
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM-yyyy");
		DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
		LocalDateTime currentTime = LocalDateTime.now();
		String formattedMonth = currentTime.format(month);
		String formattedDay = currentTime.format(day);
		
		File source = new File("rev/reviewDoc.pdf");
		File destination = new File("rev/"+ targetId +"/"+formattedMonth+"/ProgressReview-"+formattedDay+".pdf");
		
		try {
			FileUtils.copyFile(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
	    		    "Cannot Create Review Document",
	    		    "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public static User getRev1() {
		return reviewer1;
	}
	public static User getRev2() {
		return reviewer2;
	}
}
