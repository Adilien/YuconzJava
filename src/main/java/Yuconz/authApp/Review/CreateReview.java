package Yuconz.authApp.Review;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Yuconz.authApp.Search.Db;
import Yuconz.controller.AppController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * This class renders the GUI for creating a review process. It takes all employees apart from the selected user, as a parameter and displays this data in a Jtable.
 * @author Tsotne
 *
 */
public class CreateReview {

	private JFrame frame;
	private String[] columnNames = {"Staff ID","First Name","Last Name","Role"};
	private static JTable table;
	private JTextField txtRev1;
	private JTextField txtRev2;
	private boolean firstLocked = false;
	private boolean secondLocked = false;
	private int firstId;
	private int secondId;

	/** 
	 * Create the application.
	 */
	public CreateReview(String[][] input) {
		initialize(input);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[][] input) {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CreateReview.class.getResource("/LogoNoText.png")));
		frame.setTitle("Yuconz System");
		frame.setBounds(100, 100, 1000, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(518, 0, 456, 361);
		frame.getContentPane().add(panel);
		
		String selectedName = Db.getSelectedUser().getFirstName()+" "+Db.getSelectedUser().getLastName();
		
		
		JLabel lblTitle = new JLabel("Choose the Reviewers for "+selectedName);
		lblTitle.setBounds(10, 11, 280, 21);
		frame.getContentPane().add(lblTitle);
		
		
		table = new JTable(input, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(232, 0, 452, 427);
		panel.add(sp);
		
		txtRev1 = new JTextField();
		txtRev1.setEditable(false);
		txtRev1.setBounds(127, 130, 193, 28);
		frame.getContentPane().add(txtRev1);
		txtRev1.setColumns(10);
		
		JLabel lblRev1 = new JLabel("Reviewer 1");
		lblRev1.setBounds(28, 130, 77, 28);
		frame.getContentPane().add(lblRev1);
		
		JLabel lblRev2 = new JLabel("Reviewer 2");
		lblRev2.setBounds(28, 200, 77, 28);
		frame.getContentPane().add(lblRev2);
		
		txtRev2 = new JTextField();
		txtRev2.setEditable(false);
		txtRev2.setColumns(10);
		txtRev2.setBounds(127, 200, 193, 28);
		frame.getContentPane().add(txtRev2);
		
		JButton btnFirstLock = new JButton("");
		btnFirstLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(firstLocked) {
					btnFirstLock.setIcon(new ImageIcon(CreateReview.class.getResource("/unlocked25.png")));
					firstLocked = false;
				}else {
					btnFirstLock.setIcon(new ImageIcon(CreateReview.class.getResource("/lock25.png")));
					firstLocked = true;
				}
			}
		});
		btnFirstLock.setIcon(new ImageIcon(CreateReview.class.getResource("/unlocked25.png")));
		btnFirstLock.setBounds(328, 124, 89, 37);
		btnFirstLock.setBorder(BorderFactory.createEmptyBorder());
		btnFirstLock.setContentAreaFilled(false);
		frame.getContentPane().add(btnFirstLock);
		
		JButton btnSecondLock = new JButton("");
		btnSecondLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(secondLocked) {
					btnSecondLock.setIcon(new ImageIcon(CreateReview.class.getResource("/unlocked25.png")));
					secondLocked = false;
				}else {
					btnSecondLock.setIcon(new ImageIcon(CreateReview.class.getResource("/lock25.png")));
					secondLocked = true;

				}
			}
		});
		btnSecondLock.setIcon(new ImageIcon(CreateReview.class.getResource("/unlocked25.png")));
		btnSecondLock.setBounds(330, 195, 89, 37);
		btnSecondLock.setBorder(BorderFactory.createEmptyBorder());
		btnSecondLock.setContentAreaFilled(false);
		frame.getContentPane().add(btnSecondLock);
		
		JButton btnCreate = new JButton("Create");
		
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String[] splitRev1 = txtRev1.getText().split("\\s+");
				String[] splitRev2 = txtRev2.getText().split("\\s+");
				
				String Rev1FName = splitRev1[0];
				String Rev1SName = splitRev1[1];
				String Rev2FName = splitRev2[0];
				String Rev2SName = splitRev2[1];
				
				HRDatabase.getRev1().setId(firstId);
				HRDatabase.getRev1().setFirstName(Rev1FName);
				HRDatabase.getRev1().setLastName(Rev1SName);
				
				HRDatabase.getRev2().setId(secondId);
				HRDatabase.getRev2().setFirstName(Rev2FName);
				HRDatabase.getRev2().setLastName(Rev2SName);
				AppController.createReview();
				JOptionPane.showMessageDialog(null, "You have successfully started a review process.");
				AppController.selectedDetails();
				die();
				
			}	
		});
		btnCreate.setBounds(231, 291, 89, 23);
		frame.getContentPane().add(btnCreate);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppController.selectedDetails();
				die();
			}
		});
		btnBack.setBounds(132, 291, 89, 23);
		frame.getContentPane().add(btnBack);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	int row = table.getSelectedRow();
	        	if (!event.getValueIsAdjusting() & row >= 0 ) {
		        	String staffId = table.getValueAt(row, 0).toString();
		        	String staffFirstName = table.getValueAt(row, 1).toString();
		        	String staffLastName = table.getValueAt(row, 2).toString();
		        	int selectedId = Integer.parseInt(staffId);
		        		if(!firstLocked) {
			        		txtRev1.setText(staffFirstName+" "+ staffLastName);
			        		firstId = selectedId;
			        		
			        	}else {
			        		if(!secondLocked) {
			        			txtRev2.setText(staffFirstName+" "+ staffLastName);
			        			secondId = selectedId;
			        		}else {
			        			JOptionPane.showMessageDialog(null,
			                		    "You have locked in both Reviewers. Unlock before changing a Reviewer.",
			                		    "Error",
			                		    JOptionPane.ERROR_MESSAGE);
			        		}
			        	}
		        		if(secondId == firstId) {
		        			JOptionPane.showMessageDialog(null,
		                		    "You cannot pick the same person twice. Choose someone else.",
		                		    "Error",
		                		    JOptionPane.ERROR_MESSAGE);
		        			
		        			txtRev2.setText(null);
		        		}
		        		table.getSelectionModel().clearSelection();
	        	}
	        	
	        }
	    });
		
		
		//frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
	}
	public void die() {
		frame.dispose();
	}
}
