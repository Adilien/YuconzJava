package Yuconz.authApp.Review;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Yuconz.authApp.Auth;
import Yuconz.controller.AppController;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;


/**
 * This is the main display that is generated after a user has logged in successfully, with the reviewer checkbox checked. 
 * @author Tsotne
 *
 */
public class ReviewFrame {

	private JFrame frame;
	private String[] columnNames = {"Review ID","Staff ID","First Name","Last Name", "Reviewer1 ID", "Reviewer2 ID"};
	private static JTable table;
	private int row;

	/**
	 * Create the application.
	 */
	public ReviewFrame(String[][] input) {
		initialize(input);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[][] input) {
		frame = new JFrame();
		frame.setTitle("Yuconz System");
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(ReviewFrame.class.getResource("/LogoNoText.png")));
		frame.setBounds(100, 100, 804, 433);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.logOut();
			}
		});
		btnLogout.setBounds(689, 360, 89, 23);
		frame.getContentPane().add(btnLogout);
		
		
		// Get USER INFO

		String fName = Auth.getCurrentUser().getFirstName();
		String sName = Auth.getCurrentUser().getLastName();
		String role = Auth.getCurrentUser().getRole();
		int userId = Auth.getCurrentUser().getId();
		
		
		JLabel lblName = new JLabel("<dynamic> <dynamic>");
		lblName.setText(fName + " " + sName);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(131, 61, 436, 23);
		frame.getContentPane().add(lblName);

		JLabel lblRole = new JLabel("Role: " + role);
		lblRole.setBounds(10, 11, 157, 14);
		frame.getContentPane().add(lblRole);

		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(131, 36, 436, 14);
		frame.getContentPane().add(lblWelcome);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 153, 548, 230);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Awaiting To Be Reviewed");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(161, 119, 222, 23);
		frame.getContentPane().add(lblNewLabel);
		
		table = new JTable(input,columnNames);
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		panel.setLayout(null);
		JScrollPane sp = new JScrollPane(table);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(0, 0, 548, 230);
		panel.add(sp);
		
		JLabel lblMyId = new JLabel("Staff ID: "+userId);
		lblMyId.setBounds(10, 36, 95, 14);
		frame.getContentPane().add(lblMyId);
		
		JButton btnDownload = new JButton("Download Document");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String fullname = table.getValueAt(row, 2).toString()+" "+table.getValueAt(row, 3).toString();
				JOptionPane.showMessageDialog(null, "You have downloaded the Review Document To Your Devices Downloads Folder.");
				int rid = Integer.parseInt(table.getValueAt(row, 0).toString());
				HRDatabase.setRid(rid);
				AppController.downloadReviewDoc();
			}
		});
		btnDownload.setBounds(621, 181, 157, 23);
		frame.getContentPane().add(btnDownload);
		btnDownload.setVisible(false);
		
		JButton btnUpload = new JButton("Upload Document");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fullname = table.getValueAt(row, 2).toString()+" "+table.getValueAt(row, 3).toString();
				int a=JOptionPane.showConfirmDialog(null,"Upload Final Review Document for "+fullname +"? \n You will not be able to undo this action. \n Do you wish to continue?","Upload Final Review Document",JOptionPane.YES_NO_CANCEL_OPTION);  
				if(a==0){  
				   int rid = Integer.parseInt(table.getValueAt(row, 0).toString());
				   HRDatabase.setRid(rid);
				   AppController.uploadFinalReviewDoc();
				   AppController.logOut();
				}  
			}
		});
		btnUpload.setBounds(621, 232, 157, 23);
		frame.getContentPane().add(btnUpload);
		btnUpload.setVisible(false);
		
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	row = table.getSelectedRow();
	        	if (!event.getValueIsAdjusting() & row >= 0 ) {
	        		String r1 = table.getValueAt(row, 4).toString();
	        		int r1id = Integer.parseInt(r1);
		        		btnDownload.setVisible(true);
		        		
		        		if(userId == r1id) {
		        			btnUpload.setVisible(true);
		        		}
	        	}
	        	
	        }
	    });
		
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
		
	}
	public void die() {
		frame.dispose();
	}
}
