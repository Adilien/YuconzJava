package Yuconz.authApp.Search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Yuconz.authApp.User;
import Yuconz.authApp.PD.PdStore;

/**
 * This class connects to the DB and retrieves all information that is not associated with the current logged in user.
 * @author Tsotne
 *
 */
public class Db {

	private static User selectedUser;
	private Connection myDb = null;
	private ArrayList<ArrayList <String>> result = new ArrayList<ArrayList<String>>();
	private String[][] data;
	private static PdStore selectedUserDetails;
	
	public Db() {
		connectToDb();
		populateAllEmployees();
		convertData();
		selectedUserDetails = new PdStore();
		selectedUser = new User(" ", " ", " ", false, false, 0);
	}
	
	/**
	 * Connects to the Database
	 * @return myDb
	 */
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
	 * Gets all Employees inside the Employee Table in the Database and places the Data inside ArrayLists.
	 */
	public void populateAllEmployees() {
		
		String sql = "select id,fName,sName,role from Employees";
		
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
	
	
	/**
	 * Finds the data matching the id, inside the Employees table 
	 * @param id
	 */
	public void findUser() {
		int id = selectedUser.getId();
		
		String sql = "select * from Employees where id='"+id+"'";
		connectToDb();
		try(Connection conn = myDb;
				Statement stmt = conn.createStatement();
				ResultSet rs  = stmt.executeQuery(sql)){
				
				selectedUser.setFirstName(rs.getString("fName"));
				selectedUser.setLastName(rs.getString("sName"));
				selectedUser.setRole(rs.getString("role"));
				selectedUser.setAccess(false);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,
	    		    "Cannot connect to the Database",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Checks PersonalDetails Table in the Database, to see if there is an entry matching the userId.
	 * @param userId
	 * @return boolean
	 */
	public boolean matchPersonalDetails() {
		
		int selectedUserId = selectedUser.getId();
		int foundId;
		String sql = "select id from PersonalDetails where id='"+selectedUserId+"'";
		connectToDb();
		try(Connection conn = myDb;
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(sql)){
			foundId = rs.getInt("id");
				
		}catch(SQLException e){
			
			return false;
		}
		
		if(foundId== selectedUserId) {
			return true;
		}else {
			return false;
		}
	}
	public void pullDetails() {
		
		int selectedUserId = selectedUser.getId();
		
		String sql = "select * from PersonalDetails where id='"+selectedUserId+"'";
		
		connectToDb();
		
		try(Connection conn = myDb;
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(sql)){
			
				int foundId = rs.getInt("id");
				if(foundId == selectedUserId) {
					// Get new details from database
					String firstName = rs.getString("fName");
					String lastName = rs.getString("sName");
					String dob = rs.getString("dob");
					String address1 = rs.getString("address1");
					String address2 = rs.getString("address2");
					String city = rs.getString("city");
					String county = rs.getString("county");
					String postCode = rs.getString("postcode");
					String mobileNum = rs.getString("mobileNum");
					String telephoneNum = rs.getString("telephoneNum");
					String emergencyContact = rs.getString("emergencyContact");
					String emergencyNum = rs.getString("emergencyContactNum");
			
				
					// Set new Details
					selectedUserDetails.setFirstName(firstName);
					selectedUserDetails.setLastName(lastName);
					selectedUserDetails.setDoB(dob);
					selectedUserDetails.setAddress1(address1);
					selectedUserDetails.setAddress2(address2);
					selectedUserDetails.setCity(city);
					selectedUserDetails.setCounty(county);
					selectedUserDetails.setPostcode(postCode);
					selectedUserDetails.setMobile(mobileNum);
					selectedUserDetails.setTelenum(telephoneNum);
					selectedUserDetails.setEmergencyContact(emergencyContact);
					selectedUserDetails.setEmergencyNum(emergencyNum);
			}else {
				JOptionPane.showMessageDialog(null,
		    		    "Connection Made but User not found!",
		    		    "Error-User Not Found",
		    		    JOptionPane.ERROR_MESSAGE);
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,
	    		    "Cannot Fetch Your Details!",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Sets user with no Personal Details document with empty fields in the selectedUserDetails object.
	 */
	public void creatingPd() {
		selectedUserDetails.setFirstName(" ");
		selectedUserDetails.setLastName(" ");
		selectedUserDetails.setDoB(" ");
		selectedUserDetails.setAddress1(" ");
		selectedUserDetails.setAddress2(" ");
		selectedUserDetails.setCity(" ");
		selectedUserDetails.setCounty(" ");
		selectedUserDetails.setPostcode(" ");
		selectedUserDetails.setMobile(" ");
		selectedUserDetails.setTelenum(" ");
		selectedUserDetails.setEmergencyNum(" ");
		selectedUserDetails.setEmergencyContact(" ");
	}
	public void insertNew() {
		
		int selectedUserId = selectedUser.getId();
		
		connectToDb();
		String sql = "INSERT INTO PersonalDetails(id,sName,fName,dob,address1,address2,city,county,postcode,telephoneNum,mobileNum,emergencyContact,emergencyContactNum) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try(Connection conn = myDb;
                PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1,selectedUserId);
			pstmt.setString(2,selectedUserDetails.getLastName());
            pstmt.setString(3,selectedUserDetails.getFirstName());
            pstmt.setString(4,selectedUserDetails.getDoB());
            pstmt.setString(5,selectedUserDetails.getAddress1());
            pstmt.setString(6,selectedUserDetails.getAddress2());
            pstmt.setString(7,selectedUserDetails.getCity());
            pstmt.setString(8,selectedUserDetails.getCounty());
            pstmt.setString(9,selectedUserDetails.getPostcode());
            pstmt.setString(10,selectedUserDetails.getTelenum());
            pstmt.setString(11,selectedUserDetails.getMobile());
            pstmt.setString(12,selectedUserDetails.getEmergencyContact());
            pstmt.setString(13,selectedUserDetails.getEmergencyNum());
            pstmt.executeUpdate();
            System.out.println("here");
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
	    		    "Cannot connect to the Database",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void pushDetails() {
		
		int selectedUserId = selectedUser.getId();
		
		String sql = "UPDATE PersonalDetails SET fName = ? , "
                + "sName = ? , " + "dob = ? , " + "address1 = ? , " + "address2 = ? , " + "city = ? , " 
				+ "county = ? , " + "postcode = ? , " + "telephoneNum = ? , " + "mobileNum = ? , " 
                + "emergencyContact = ? , " + "emergencyContactNum = ? "
                + "WHERE id = ?";
		connectToDb();
		
        try (Connection conn = myDb;
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, selectedUserDetails.getFirstName());
            pstmt.setString(2, selectedUserDetails.getLastName());
            pstmt.setString(3, selectedUserDetails.getDoB());
            
            
            pstmt.setString(4, selectedUserDetails.getAddress1());
            pstmt.setString(5, selectedUserDetails.getAddress2());
            pstmt.setString(6, selectedUserDetails.getCity());
            pstmt.setString(7, selectedUserDetails.getCounty());
            pstmt.setString(8, selectedUserDetails.getPostcode());
            pstmt.setString(9, selectedUserDetails.getTelenum());
            pstmt.setString(10, selectedUserDetails.getMobile());
            pstmt.setString(11, selectedUserDetails.getEmergencyContact());
            pstmt.setString(12, selectedUserDetails.getEmergencyNum());
            pstmt.setInt(13, selectedUserId);
            		
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,
	    		    "Cannot connect to the Database",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
        }
	}
	
	
	/**
	 * Get Method for the 2D Array
	 * @return data
	 */
	public String[][] getEmployees() {
		return data;
	}
	public static User getSelectedUser() {
		return selectedUser;
	}
	public static PdStore getSelectedUserDetails() {
		return selectedUserDetails;
	}
	
}
