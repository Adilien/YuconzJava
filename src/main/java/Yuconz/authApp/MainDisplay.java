package Yuconz.authApp;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import Yuconz.controller.*;

/**
 * This class generates the GUI for the main display, which is generated after
 * the user successfully logs in.
 */
public class MainDisplay {

	private JFrame frame;
	private boolean accessLevel;	// This will fluctuate 
	private boolean realAccessLevel;	// This is a constant

	/**
	 * Create the Frame.
	 */
	public MainDisplay(boolean isBeingReviewed) {
		initialize(isBeingReviewed);

	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize(boolean input) {
		frame = new JFrame();
		frame.setTitle("Yuconz System");
		
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(MainDisplay.class.getResource("/LogoNoText.png")));
		frame.setBounds(100, 100, 715, 433);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);

		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.logOut();
			}
		});
		btnLogout.setBounds(581, 360, 89, 23);
		frame.getContentPane().add(btnLogout);

		// Get USER INFO

		String fName = Auth.getCurrentUser().getFirstName();
		String sName = Auth.getCurrentUser().getLastName();
		String role = Auth.getCurrentUser().getRole();
		int userId = Auth.getCurrentUser().getId();
		accessLevel = Auth.getCurrentUser().getAccess();
		realAccessLevel = accessLevel;
		
		
		String accessLvl = "Normal";
		if (accessLevel) {
			accessLvl = "High";
		}

		JLabel lblName = new JLabel("<dynamic> <dynamic>");
		lblName.setText(fName + " " + sName);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(131, 100, 436, 23);
		frame.getContentPane().add(lblName);

		JLabel lblRole = new JLabel("Role: " + role);
		lblRole.setBounds(10, 11, 157, 14);
		frame.getContentPane().add(lblRole);

		JLabel lblAccessLevel = new JLabel("Access Level: " + accessLvl);
		lblAccessLevel.setBounds(10, 36, 186, 14);
		frame.getContentPane().add(lblAccessLevel);

		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(131, 81, 436, 14);
		frame.getContentPane().add(lblWelcome);
		
		JButton btnMyPd = new JButton("My Personal Details");
		btnMyPd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.generatePd();
			}
		});
		btnMyPd.setBounds(260, 158, 179, 23);
		frame.getContentPane().add(btnMyPd);
		JButton btnChangeAccess = new JButton("Toggle Access Level");
		
		btnChangeAccess.setBounds(10, 61, 157, 23);
		frame.getContentPane().add(btnChangeAccess);
		
		JButton btnEmployees = new JButton("All Employees");
		btnEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.showLookUp();
			}
		});
		
		btnEmployees.setBounds(260, 214, 179, 23);
		frame.getContentPane().add(btnEmployees);
		
		JButton btnReviewDoc = new JButton("Download Review Doc");
		btnReviewDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.downloadMyReviewDoc();
			}
		});
		btnReviewDoc.setBounds(260, 270, 179, 23);
		frame.getContentPane().add(btnReviewDoc);
		
		JButton btnUploadRev = new JButton("Upload Review Doc");
		btnUploadRev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AppController.uploadDocument();
				
			}
		});
		btnUploadRev.setBounds(260, 326, 179, 23);
		frame.getContentPane().add(btnUploadRev);
		btnChangeAccess.setVisible(false);
		
		btnChangeAccess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(accessLevel) {
					lblAccessLevel.setText("Access Level: Normal");
					btnEmployees.setVisible(false);
					accessLevel = false;
					AppController.toggleAuthorisation(accessLevel);
					
				}else{
					lblAccessLevel.setText("Access Level: High");
					btnEmployees.setVisible(true);
					accessLevel = true;
					AppController.toggleAuthorisation(accessLevel);
					
				}
			}
		});
		
		if(realAccessLevel) {
			btnChangeAccess.setVisible(true);
		}
		if(!accessLevel) {
			btnEmployees.setVisible(false);
			
		}
		
		if(input) {
			btnReviewDoc.setVisible(true);
			btnUploadRev.setVisible(true);
		}else {
			btnReviewDoc.setVisible(false);
			btnUploadRev.setVisible(false);
		}

	}
	public void hide() {
		frame.setVisible(false);
	}
	public void show() {
		frame.setVisible(true);
	}
	public void die() {
		frame.dispose();
	}
}
