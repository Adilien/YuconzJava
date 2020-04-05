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
		String sql = "select targetid from Reviews where targetid='"+userId+"'and Completed1='0' and Completed2='0'";
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
	public boolean checkIfReviewer() {
		
		int userId = Auth.getCurrentUser().getId();
		int potentialId1;
		int potentialId2;
		String sql = "select r1id,r2id from Reviews where (r1id='"+userId+"' and Completed1 ='0') or (r2id='"+userId+"'and Completed2='0')";
		connectToDb();
		try(Connection conn = myDb;
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(sql)){
			potentialId1 = rs.getInt("r1id");
			potentialId2 = 	rs.getInt("r2id");
		}catch(SQLException e){
			
			return false;
		}
		
		if(potentialId1 == userId ||potentialId2 == userId ) {
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
		
		// Create File and Directory.
		File source = new File("rev/reviewDoc.pdf");
		String finalPath = "rev/"+ targetId +"/"+formattedMonth+"/ProgressReview-"+formattedDay+".pdf";
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
		
		String sql = "INSERT INTO Reviews(targetid,targetFName,targetSName,r1id,r1FName,r1SName,r2id,r2FName,r2SName,DocPath,Completed1,Completed2) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		 
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
	
	public void downloadMyRev() {
		
		int userId = Auth.getCurrentUser().getId();
		String path ="Test.pdf";
		
		String sql = "select DocPath from Reviews where targetid='"+userId+"'";
		
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
			JOptionPane.showMessageDialog(null,"Successfully Downloaded To Your Downloads Folder.","Alert",JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
	    		    "Could Not Download Review Document",
	    		    "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void uploadMyRev() {
		
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			File source = new File(selectedFile.getAbsolutePath());
			
			int userId = Auth.getCurrentUser().getId();
			String sql = "select DocPath from Reviews where targetid='"+userId+"'";
			
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
				JOptionPane.showMessageDialog(null,
		    		    "Uploaded Document.",
		    		    "Alert", JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
		    		    "Cannot Upload Review Document",
		    		    "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		
		
	}
	
	public static User getRev1() {
		return reviewer1;
	}
	public static User getRev2() {
		return reviewer2;
	}
}
