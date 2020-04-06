package Yuconz.authApp.Review;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Yuconz.authApp.Search.Db;
import Yuconz.controller.AppController;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AllRevsFrame {

	private JFrame frame;
	private String[] columnNames = {"Review ID", "Reviewer1 ID", "Reviewer2 ID"};
	//For Tests
	String[][] data = { 
	           { "3", "4031", "CSE" }, 
	           { "2", "6014", "IT" } 
	};
	private static JTable table;
	private int row;


	/**
	 * Create the application.
	 */
	public AllRevsFrame(String[][] input) {
		initialize(input);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[][] input) {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(AllRevsFrame.class.getResource("/LogoNoText.png")));
		frame.setTitle("Yuconz System");
		frame.setBounds(100, 100, 774, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String fullName = Db.getSelectedUser().getFirstName()+" "+ Db.getSelectedUser().getLastName();
		
		JLabel lblTitle = new JLabel("All Current and Past Reviews For "+fullName);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(26, 11, 705, 29);
		frame.getContentPane().add(lblTitle);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 94, 610, 285);
		frame.getContentPane().add(panel);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fullname = Db.getSelectedUser().getFirstName() + " " + Db.getSelectedUser().getLastName();
				int a=JOptionPane.showConfirmDialog(null,"Download Review Document for "+fullname +"? \n Do you wish to continue?","Download Review Document",JOptionPane.YES_NO_CANCEL_OPTION);  
				if(a==0){
				   int rid = Integer.parseInt(table.getValueAt(row, 0).toString());
				   HRDatabase.setRid(rid);
				   AppController.downloadReviewDoc();
				}  
			}
		});
		btnDownload.setBounds(630, 237, 118, 23);
		frame.getContentPane().add(btnDownload);
		
		table = new JTable(input,columnNames);
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		panel.setLayout(null);
		JScrollPane sp = new JScrollPane(table);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(0, 0, 608, 285);
		panel.add(sp);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.showUserDisplay();
				die();
			}
		});
		btnBack.setBounds(630, 294, 118, 23);
		frame.getContentPane().add(btnBack);
		
		
		frame.setVisible(true);
	}

	public void die() {
		frame.dispose();
	}
}
