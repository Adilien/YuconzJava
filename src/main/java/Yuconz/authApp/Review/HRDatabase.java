package Yuconz.authApp.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	
	
	public HRDatabase() {
		connectToDb();
		availableReviewers();
		convertData();
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
	
}
