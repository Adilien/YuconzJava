package Yuconz.authApp.Search;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import Yuconz.controller.*;
import Yuconz.authApp.Auth;
import Yuconz.authApp.MainDisplay;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserOverview {

	private JFrame frame;
	private boolean exists;
	
	
	
	/**
	 * Create the application.
	 */
	public UserOverview(boolean input, boolean rev) {
		exists = input;
		initialize(rev);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean beingReviewed) {
		
		int id = Db.getSelectedUser().getId();
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(UserOverview.class.getResource("/LogoNoText.png")));
		frame.setTitle("Yuconz System");
		frame.getContentPane().setFont(new Font("Calibri", Font.BOLD, 26));
		frame.setBounds(100, 100, 545, 392);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
			     UsersDisplay.table.setEnabled(true);
			     die();
			}
		});
		frame.setLocationRelativeTo(null); 
		
		String fName = Db.getSelectedUser().getFirstName();
		String lName = Db.getSelectedUser().getLastName();
		String fullName =fName +" " + lName;
		
		JLabel lblNewLabel = new JLabel(fullName);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(160, 11, 218, 38);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnCreateBtn = new JButton("Create Personal Details");
		btnCreateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.createDetails();
				die();
			}
		});
		btnCreateBtn.setBounds(160, 214, 218, 32);
		frame.getContentPane().add(btnCreateBtn);
		
		JButton btnView = new JButton("View Personal Details");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.viewDetails();
				die();
			}
		});
		btnView.setBounds(160, 271, 218, 32);
		frame.getContentPane().add(btnView);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.showTable();
				die();
			}
		});
		btnBack.setBounds(10, 312, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnViewRev = new JButton("View Review Records");
		btnViewRev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.viewAllRevs();
				die();
			}
		});
		btnViewRev.setBounds(160, 107, 218, 32);
		frame.getContentPane().add(btnViewRev);
		
		JButton btnStartRev = new JButton("Start Review Process");
		btnStartRev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.createNewReview();
				die();
			}
		});
		btnStartRev.setBounds(160, 160, 218, 32);
		frame.getContentPane().add(btnStartRev);
		
		// Checks if Personal Details Exists or not
		if(exists) {
			btnCreateBtn.setVisible(false);
		}else {
			btnView.setVisible(false);
		}
		if(beingReviewed) {
			btnStartRev.setVisible(false);
		}
		
		// Checks to see if the current User has HR Access or not.
		if(!Auth.getCurrentUser().getHrAccess()) {
			btnView.setVisible(false);
			btnCreateBtn.setVisible(false);
			btnStartRev.setVisible(false);
		}
		
		frame.setVisible(true);
		
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
