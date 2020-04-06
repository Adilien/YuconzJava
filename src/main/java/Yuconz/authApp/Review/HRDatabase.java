package Yuconz.authApp.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

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
	private ArrayList<ArrayList <String>> resultRev = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList <String>> resultToDo = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList <String>> allCompleted = new ArrayList<ArrayList<String>>();
	private String[][] availableRevs;
	private String[][] toDoRevs;
	private String[][] completedRevs;
	
	private static User reviewer1 = new User(null, null, null, false, false, 0);
	private static User reviewer2 = new User(null, null, null, false, false, 0);
	private static int rid;
	
	
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
		convertRevData();
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
			    resultRev.add(row);
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
	    		    "Cannot connect to the Database",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public boolean checkImBeingReviewed() {
		int userId = Auth.getCurrentUser().getId();
		int foundId;
		String sql = "select targetid from Reviews where targetid='"+userId+"'and Completed='0'";
		connectToDb();
		try(Connection conn = myDb;
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(sql)){
			foundId = rs.getInt("targetid");
				
		}catch(SQLException e){
			
			return false;
		}
		
		if(foundId == userId) {
			return true;
		}else {
			return false;
		}
	}
	public boolean checkIfBeingReviewed() {
		int userId = Db.getSelectedUser().getId();
		int foundId;
		String sql = "select targetid from Reviews where targetid='"+userId+"'and Completed='0'";
		connectToDb();
		try(Connection conn = myDb;
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(sql)){
			foundId = rs.getInt("targetid");
				
		}catch(SQLException e){
			
			return false;
		}
		
		if(foundId == userId) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Checks if the User is a Reviewer or not.
	 * @return boolean
	 */
	public boolean checkIfReviewer() {
		
		int userId = Auth.getCurrentUser().getId();
		int potentialId1;
		int potentialId2;
		String sql = "select r1id,r2id from Reviews where (r1id='"+userId+"'or r2id='"+userId+"') and Completed= '0'";
		connectToDb();
		try(Connection conn = myDb;
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(sql)){
			potentialId1 = rs.getInt("r1id");
			potentialId2 = 	rs.getInt("r2id");
		}catch(SQLException e){
			
			return false;
		}
		
		if(potentialId1 == userId || potentialId2 == userId ) {
			return true;
		}else {
			return false;
		}
		
	}
	/**
	 * Copies template review document in correct Directory for the User. Creates a Database Entry with all the corrosponding infromation.
	 */
	public void createReviewDoc() {
		int targetId = Db.getSelectedUser().getId();
		String lastName = Db.getSelectedUser().getLastName();

		
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM-yyyy");
		DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
		LocalDateTime currentTime = LocalDateTime.now();
		String formattedMonth = currentTime.format(month);
		String formattedDay = currentTime.format(day);
		
		// Create File and Directory.
		File source = new File("rev/reviewDoc.pdf");
		String finalPath = "rev/"+ targetId +"/"+formattedMonth+"/"+ lastName +"-ProgressReview-"+formattedDay+".pdf";
		File destination = new File(finalPath);
		
		try {
			FileUtils.copyFile(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
	    		    "Cannot Create Review Document",
	    		    "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		connectToDb();
		// Store Info into DB
		
		String sql = "INSERT INTO Reviews(targetid,targetFName,targetSName,r1id,r1FName,r1SName,r2id,r2FName,r2SName,DocPath,Completed,Uploaded) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		 
        try (Connection conn = myDb;
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Db.getSelectedUser().getId());
            pstmt.setString(2, Db.getSelectedUser().getFirstName());
            pstmt.setString(3, Db.getSelectedUser().getLastName());
            pstmt.setInt(4, reviewer1.getId());
            pstmt.setString(5, reviewer1.getFirstName());
            pstmt.setString(6, reviewer1.getLastName());
            pstmt.setInt(7, reviewer2.getId());
            pstmt.setString(8, reviewer2.getFirstName());
            pstmt.setString(9, reviewer2.getLastName());
            pstmt.setString(10, finalPath);
            pstmt.setInt(11, 0);
            pstmt.setInt(12, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
	}
	/**
	 * User downloads template review document to fill out.
	 */
	public void downloadMyRev() {
		
		int userId = Auth.getCurrentUser().getId();
		String path ="Test.pdf";
		
		String sql = "select DocPath from Reviews where targetid='"+userId+"' and Completed ='0'";
		
		connectToDb();
		
		try(Connection conn = myDb;
				Statement stmt = conn.createStatement();
				ResultSet rs  = stmt.executeQuery(sql)){
			path = rs.getString("DocPath");
			}catch(SQLException e){
			
			}
		
		String home = System.getProperty("user.home");
		File source = new File(path);
		String spl[]=path.split("/");
		String name = spl[3];
		
		File destination = new File(home+"/Downloads/" + name +""); 
		
		try {
			FileUtils.copyFile(source, destination);
			JOptionPane.showMessageDialog(null,"Successfully Downloaded To Your Devices Downloads Folder.","Alert",JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
	    		    "Could Not Download Review Document",
	    		    "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Uploads Users Document so they can be reviewed.
	*/
	public void uploadMyRev() {
		
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			File source = new File(selectedFile.getAbsolutePath());
			
			int userId = Auth.getCurrentUser().getId();
			String sql = "select DocPath from Reviews where targetid='"+userId+"' and Completed ='0'";
			
			connectToDb();
			
			String path = null;
			
			try(Connection conn = myDb;
					Statement stmt = conn.createStatement();
					ResultSet rs  = stmt.executeQuery(sql)){
				path = rs.getString("DocPath");
				}catch(SQLException e){
				
				}
			
			
			File destination = new File(path);
			

			try {
				FileUtils.copyFile(source, destination);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
		    		    "Cannot Upload Review Document",
		    		    "Error", JOptionPane.ERROR_MESSAGE);
			}
		   connectToDb();
		   sql = "UPDATE Reviews SET Uploaded = ?"+ "WHERE targetid = ?" + "AND Uploaded=?"+ "AND Completed=?";
		   try (Connection conn = myDb;
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, 1); 
	            pstmt.setInt(2, userId);
	            pstmt.setInt(3, 0); 
	            pstmt.setInt(4, 0); 
	            pstmt.executeUpdate();
	            
	            JOptionPane.showMessageDialog(null,
		    		    "Uploaded Document.",
		    		    "Alert", JOptionPane.WARNING_MESSAGE);
	            
	        } catch (SQLException e) {
	        	e.printStackTrace();
				JOptionPane.showMessageDialog(null,
		    		    "Could Not Connect To DB To Update Document.",
		    		    "Error", JOptionPane.ERROR_MESSAGE);
	        }
			
		}
		
		
		
	}
	
	/** 
	 * Get all the Users the User has to perform Reviews of
	 */
	public void getAllRevTasks() {
		
		availableTasks();
		convertToDoData();
		
	}
	public void availableTasks() {
		
		int userId = Auth.getCurrentUser().getId();
		
		String sql = "select rid, targetid, targetFName, targetSName, r1id, r2id from Reviews where (Completed='0') and (Uploaded = '1') and (r1id ='"+userId+"' or r2id='"+userId+"')";
		
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
			    resultToDo.add(row);
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,
	    		    "Cannot connect to the Database",
	    		    "Error",
	    		    JOptionPane.ERROR_MESSAGE);
		}
	}
	public void downloadRev() {
		
		String sql = "select DocPath from Reviews where rid='"+rid+"'";
		String path= "Tester.pdf";
		connectToDb();
		
		try(Connection conn = myDb;
				Statement stmt = conn.createStatement();
				ResultSet rs  = stmt.executeQuery(sql)){
			path = rs.getString("DocPath");
			}catch(SQLException e){
			
			}
		
		String home = System.getProperty("user.home");
		File source = new File(path);
		String spl[]=path.split("/");
		String name = spl[3];
		
		File destination = new File(home+"/Downloads/" + name +""); 
		
		try {
			FileUtils.copyFile(source, destination);
			JOptionPane.showMessageDialog(null,"Successfully Downloaded To Your Devices Downloads Folder.","Alert",JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
	    		    "Could Not Download Review Document",
	    		    "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
	}
	public void uploadRev() {
		
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			File source = new File(selectedFile.getAbsolutePath());
			
			String sql = "select DocPath from Reviews where rid='"+rid+"'";
			
			connectToDb();
			
			String path = null;
			
			try(Connection conn = myDb;
					Statement stmt = conn.createStatement();
					ResultSet rs  = stmt.executeQuery(sql)){
				path = rs.getString("DocPath");
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null,
			    		    "Could not connect to server",
			    		    "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			
			File destination = new File(path);
			

			try {
				FileUtils.copyFile(source, destination);
				
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
		    		    "Cannot Upload Review Document",
		    		    "Error", JOptionPane.ERROR_MESSAGE);
			}
		   connectToDb();
		   sql = "UPDATE Reviews SET Completed = ?"+ "WHERE rid = ?" + "AND Uploaded=?"+ "AND Completed=?";
		   try (Connection conn = myDb;
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, 1); 
	            pstmt.setInt(2, rid);
	            pstmt.setInt(3, 1); 
	            pstmt.setInt(4, 0); 
	            pstmt.executeUpdate();
	            
	            JOptionPane.showMessageDialog(null,
		    		    "Uploaded Document.",
		    		    "Alert", JOptionPane.WARNING_MESSAGE);
	            
	        } catch (SQLException e) {
	        	e.printStackTrace();
				JOptionPane.showMessageDialog(null,
		    		    "Could Not Connect To DB To Update Document.",
		    		    "Error", JOptionPane.ERROR_MESSAGE);
	        }
			
		}
	}
	public void findAllCompletedRevs() {
		selectAllRevs();
		convertCompleted();
	}
	public void selectAllRevs() {
		
		String sql = "select rid ,r1id, r2id from Reviews where targetid ='"+Db.getSelectedUser().getId()+"'";
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
			    allCompleted.add(row);
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
	public void convertRevData(){
		availableRevs = resultRev.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
	}
	
	public void convertToDoData() {
		toDoRevs = resultToDo.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
	}
	public void convertCompleted() {
		completedRevs = allCompleted.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
	}
	public String[][] getAllCompletedRevs() {
		return completedRevs;
	}
	public String[][] getReviewers() {
		return availableRevs;
	}
	
	public String[][] getToDos(){
		return toDoRevs;
	}
	
	public static User getRev1() {
		return reviewer1;
	}
	public static User getRev2() {
		return reviewer2;
	}
	public static int getRid() {
		return rid;
	}
	public static void setRid(int input) {
		rid = input;
	}
}
