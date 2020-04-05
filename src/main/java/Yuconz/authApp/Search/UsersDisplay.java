package Yuconz.authApp.Search;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import Yuconz.controller.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Generates a JFrame with a JTable that is filled with all current Employees in the Database.
 * @author Tsotne
 *
 */
public class UsersDisplay {

	
	private JFrame frame;
	private String[] columnNames = {"Staff ID","First Name","Last Name","Role"};


    // Column Names  
    static JTable table;
    

	/**
	 * Create the application.
	 */
	public UsersDisplay(String[][] input) {
		initialize(input);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[][] input) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(UsersDisplay.class.getResource("/LogoNoText.png")));
		frame.setTitle("Yuconz System");
		frame.getContentPane().setFont(new Font("Calibri", Font.BOLD, 26));
		frame.setBounds(100, 100, 718, 534);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
			     die();
			}
		});
		table = new JTable(input, columnNames);
		table.setBounds(10, 41, 528, 286);
		table.setDefaultEditor(Object.class, null);
		JScrollPane sp = new JScrollPane(table); 
        frame.getContentPane().add(sp); 
		//frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	int column = 0;
	        	int row = table.getSelectedRow();
	        	String value = table.getModel().getValueAt(row, column).toString();
	        	Db.getSelectedUser().setId(Integer.parseInt(value));
	        	AppController.selectedDetails();
	        	die();
	        }
	    });
	}
	public void die() {
		frame.dispose();
	}
	public void hide() {
		frame.setVisible(false);
	}
	public void show() {
		frame.setVisible(true);
	}
}
