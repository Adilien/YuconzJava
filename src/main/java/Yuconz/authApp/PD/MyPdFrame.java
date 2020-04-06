package Yuconz.authApp.PD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Toolkit;

import Yuconz.controller.*;
import Yuconz.authApp.Auth;

//import authApp.AppController;

public class MyPdFrame {

private JFrame frame;


private JTextField fNameField;
private JTextField sNameField;
private JTextField dobField;
private JTextField address1Field;
private JTextField address2Field;
private JTextField cityField;
private JTextField countyField;
private JTextField postcodeField;
private JTextField teleNumField;
private JTextField mobNumField;
private JTextField emerNumField;
private JTextField emerContactField;


/**
 * Create the application.
 */
public MyPdFrame() {
	initialize();
}

/**
 * Initialise the contents of the frame.
 */
private void initialize() {
	frame = new JFrame();
	frame.setResizable(false);
	frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MyPdFrame.class.getResource("/LogoNoText.png")));
	frame.setTitle("Yuconz System");
	frame.getContentPane().setFont(new Font("Calibri", Font.BOLD, 26));
	frame.setBounds(100, 100, 800, 534);
	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent evt) {
		     backFunction();
		}
	});
	frame.setLocationRelativeTo(null); 
	frame.setVisible(true);

	//Get ID
	int userId =  Auth.getCurrentUser().getId();
	String myid = Integer.toString(userId);
	//Get Personal Details
	String firstName = MyDetails.getMyCurrentDetails().getFirstName();
	String lastName = MyDetails.getMyCurrentDetails().getLastName();
	String dob = MyDetails.getMyCurrentDetails().getDoB();
	String address1 = MyDetails.getMyCurrentDetails().getAddress1();
	String address2 = MyDetails.getMyCurrentDetails().getAddress2();
	String city = MyDetails.getMyCurrentDetails().getCity();
	String county = MyDetails.getMyCurrentDetails().getCounty();
	String postCode = MyDetails.getMyCurrentDetails().getPostcode();
	String mobileNum = MyDetails.getMyCurrentDetails().getMobile();
	String telephoneNum = MyDetails.getMyCurrentDetails().getTelenum();
	String emergencyContact = MyDetails.getMyCurrentDetails().getEmergencyContact();
	String emergencyNum = MyDetails.getMyCurrentDetails().getEmergencyNum(); 

    JLabel lblRole = new JLabel("Role: "+Auth.getCurrentUser().getRole());
    lblRole.setBounds(10, 11, 157, 14);
    frame.getContentPane().add(lblRole);

    JLabel lblNewLabel = new JLabel("My Personal Details");
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    lblNewLabel.setBounds(194, 10, 393, 16);
    frame.getContentPane().add(lblNewLabel);

    JLabel lblNewLabel_1 = new JLabel("Staff No: "+myid);
    lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
    lblNewLabel_1.setBounds(10, 35, 91, 16);
    frame.getContentPane().add(lblNewLabel_1);

    JLabel lblNewLabel_3 = new JLabel("Forename");
    lblNewLabel_3.setBounds(10, 98, 91, 16);
    frame.getContentPane().add(lblNewLabel_3);

    JLabel lblNewLabel_3_1 = new JLabel("Surname");
    lblNewLabel_3_1.setBounds(10, 152, 72, 16);
    frame.getContentPane().add(lblNewLabel_3_1);

    JLabel lblNewLabel_3_1_1 = new JLabel("Date of Birth");
    lblNewLabel_3_1_1.setBounds(10, 206, 80, 16);
    frame.getContentPane().add(lblNewLabel_3_1_1);

    fNameField = new JTextField();
    fNameField.setBounds(90, 95, 116, 22);
    frame.getContentPane().add(fNameField);
    fNameField.setText(firstName);
    fNameField.setColumns(10);

    sNameField = new JTextField();
    sNameField.setBounds(90, 149, 116, 22);
    frame.getContentPane().add(sNameField);
    sNameField.setText(lastName);
    sNameField.setColumns(10);

    dobField = new JTextField();
    dobField.setBounds(90, 203, 116, 22);
    frame.getContentPane().add(dobField);
    dobField.setColumns(10);
    dobField.setText(dob);

    JLabel lblNewLabel_3_2 = new JLabel("Address");
    lblNewLabel_3_2.setBounds(231, 98, 56, 16);
    frame.getContentPane().add(lblNewLabel_3_2);

    address1Field = new JTextField();
    address1Field.setColumns(10);
    address1Field.setBounds(321, 95, 116, 22);
    frame.getContentPane().add(address1Field);
    address1Field.setText(address1);

    JLabel lblNewLabel_3_1_2 = new JLabel("Town/City");
    lblNewLabel_3_1_2.setBounds(231, 206, 80, 16);
    frame.getContentPane().add(lblNewLabel_3_1_2);

    address2Field = new JTextField();
    address2Field.setColumns(10);
    address2Field.setBounds(321, 146, 116, 22);
    frame.getContentPane().add(address2Field);
    address2Field.setText(address2);

    cityField = new JTextField();
    cityField.setColumns(10);
    cityField.setBounds(321, 203, 116, 22);
    frame.getContentPane().add(cityField);
    cityField.setText(city);

    JLabel lblNewLabel_3_1_2_1 = new JLabel("County");
    lblNewLabel_3_1_2_1.setBounds(231, 262, 80, 16);
    frame.getContentPane().add(lblNewLabel_3_1_2_1);

    countyField = new JTextField();
    countyField.setColumns(10);
    countyField.setBounds(321, 259, 116, 22);
    frame.getContentPane().add(countyField);
    countyField.setText(county);


    JLabel lblNewLabel_3_2_1 = new JLabel("Postcode");
    lblNewLabel_3_2_1.setBounds(510, 98, 56, 16);
    frame.getContentPane().add(lblNewLabel_3_2_1);

    postcodeField = new JTextField();
    postcodeField.setColumns(10);
    postcodeField.setBounds(655, 95, 116, 22);
    postcodeField.setText(postCode);
    frame.getContentPane().add(postcodeField);


    JLabel lblNewLabel_3_2_1_1 = new JLabel("Telephone Number");
    lblNewLabel_3_2_1_1.setBounds(510, 239, 129, 16);
    frame.getContentPane().add(lblNewLabel_3_2_1_1);

    teleNumField = new JTextField();
    teleNumField.setColumns(10);
    teleNumField.setBounds(655, 236, 116, 22);
    frame.getContentPane().add(teleNumField);
    teleNumField.setText(telephoneNum);

    JLabel lblNewLabel_3_1_2_2 = new JLabel("Mobile Number");
    lblNewLabel_3_1_2_2.setBounds(510, 174, 101, 16);
    frame.getContentPane().add(lblNewLabel_3_1_2_2);

    mobNumField = new JTextField();
    mobNumField.setColumns(10);
    mobNumField.setBounds(655, 171, 116, 22);
    frame.getContentPane().add(mobNumField);
    mobNumField.setText(mobileNum);

    JLabel lblNewLabel_3_2_1_2_1 = new JLabel("Emergency Contact");
    lblNewLabel_3_2_1_2_1.setBounds(450, 327, 116, 16);
    frame.getContentPane().add(lblNewLabel_3_2_1_2_1);

    JLabel lblNewLabel_3_2_1_2_1_1 = new JLabel("Emergency Contact Number");
    lblNewLabel_3_2_1_2_1_1.setBounds(10, 327, 170, 16);
    frame.getContentPane().add(lblNewLabel_3_2_1_2_1_1);

    emerNumField = new JTextField();
    emerNumField.setColumns(10);
    emerNumField.setBounds(231, 324, 160, 22);
    frame.getContentPane().add(emerNumField);
    emerNumField.setText(emergencyNum);

    emerContactField = new JTextField();
    emerContactField.setColumns(10);
    emerContactField.setBounds(577, 324, 194, 22);
    emerContactField.setText(emergencyContact);
    frame.getContentPane().add(emerContactField);

    JButton btnBack = new JButton("BACK");
    btnBack.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		backFunction();
    	}
    });
    btnBack.setBounds(269, 429, 89, 23);
    frame.getContentPane().add(btnBack);
    
    JButton btnSaveDetails = new JButton("Save");
    btnSaveDetails.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		
    		//Get Entered Details from TextFields.
    		String newFName = fNameField.getText();
    		String newSName = sNameField.getText();
    		String newDob = dobField.getText();
    		String newAddress1 = address1Field.getText();
    		String newAddress2 = address2Field.getText();
    		String newCity = cityField.getText();
    		String newCounty = countyField.getText();
    		String newPostCode = postcodeField.getText();
    		String newMobileNum = mobNumField.getText();
    		String newTelephoneNum = teleNumField.getText();
    		String newEmergencyNum = emerNumField.getText();
    		String newEmergencyContact = emerContactField.getText();
    		
    		
    		//Set them as the new details within the MyCurrentDetails obj.
    		MyDetails.getMyCurrentDetails().setFirstName(newFName);
    		MyDetails.getMyCurrentDetails().setLastName(newSName);
    		MyDetails.getMyCurrentDetails().setDoB(newDob);
    		MyDetails.getMyCurrentDetails().setAddress1(newAddress1);
    		MyDetails.getMyCurrentDetails().setAddress2(newAddress2);
    		MyDetails.getMyCurrentDetails().setCity(newCity);
    		MyDetails.getMyCurrentDetails().setCounty(newCounty);
    		MyDetails.getMyCurrentDetails().setPostcode(newPostCode);
    		MyDetails.getMyCurrentDetails().setMobile(newMobileNum);
    		MyDetails.getMyCurrentDetails().setTelenum(newTelephoneNum);
    		MyDetails.getMyCurrentDetails().setEmergencyNum(newEmergencyNum);
    		MyDetails.getMyCurrentDetails().setEmergencyContact(newEmergencyContact);
    		
    		AppController.saveMyDetails();
    		
    		saveAndBack();
    		
    		
    	}
    });
    btnSaveDetails.setBounds(399, 429, 89, 23);
    frame.getContentPane().add(btnSaveDetails);

    
	}
	public void hide() {
		frame.setVisible(false);
	}
	public void show() {
		frame.setVisible(true);
	}
	public void backFunction(){
		AppController.showMain();
		hide();
	}
	public void die() {
		frame.dispose();
	}
	public void saveAndBack() {
		AppController.showMain();
		die();
	}
}