package Yuconz.authApp.Review;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Yuconz.authApp.Auth;
import Yuconz.controller.AppController;
import javax.swing.JList;
import javax.swing.ListSelectionModel;


/**
 * This is the main display that is generated after a user has logged in successfully, with the reviewer checkbox checked. 
 * @author Tsotne
 *
 */
public class ReviewFrame {

	private JFrame frame;
	private String [] reviewList = { "Monday","Tuesday","Wednesday", 
            "Thursday","Friday","Saturday","Sunday"}; 

	/**
	 * Create the application.
	 */
	public ReviewFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Yuconz System");
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(ReviewFrame.class.getResource("/authApp/img/LogoNoText.png")));
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
		
		
		JLabel lblName = new JLabel("<dynamic> <dynamic>");
		lblName.setText(fName + " " + sName);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(131, 100, 436, 23);
		frame.getContentPane().add(lblName);

		JLabel lblRole = new JLabel("Role: " + role);
		lblRole.setBounds(10, 11, 157, 14);
		frame.getContentPane().add(lblRole);

		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(131, 81, 436, 14);
		frame.getContentPane().add(lblWelcome);
		
		JList list = new JList(reviewList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(284, 160, 132, 203);
		frame.getContentPane().add(list);
		
	}
	public void die() {
		frame.dispose();
	}
}
