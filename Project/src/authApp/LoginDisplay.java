package authApp;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import controller.*;
import javax.swing.JCheckBox;


/**
 * This class generates the GUI for the Login functionality
 * 
 * @author Tsotne
 *
 */
public class LoginDisplay{

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	/**
	* Create the Frame.
	*/
	public LoginDisplay() {
		initialize();
	}

	/**
	* Initialise the contents of the frame.
	*/
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainDisplay.class.getResource("/authApp/img/LogoNoText.png")));
		frame.setTitle("Yuconz System");
		frame.getContentPane().setFont(new Font("Calibri", Font.BOLD, 26));
		frame.setBounds(100, 100, 450, 301);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); 
		
		
		
		JLabel lblTitle = new JLabel("Yuconz");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Calibri", Font.BOLD, 28));
		lblTitle.setBounds(149, 11, 145, 27);
		frame.getContentPane().add(lblTitle);
		
		JCheckBox chkReview = new JCheckBox("Reviewer");
		chkReview.setBounds(178, 175, 88, 23);
		frame.getContentPane().add(chkReview);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calibri Light", Font.PLAIN, 15));
		passwordField.setBounds(128, 136, 190, 27);
		frame.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri Light", Font.PLAIN, 15));
		textField.setBounds(127, 90, 191, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		/**
		 * Creates the login button
		 */
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(128, 212, 88, 38);
		frame.getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String uName = textField.getText();
				char[] pass = passwordField.getPassword();
				String pwd = String.valueOf(pass);
				
				if(chkReview.isSelected()) {
					
					AppController.reviewMenu(uName, pwd);
					
				}else {
					
					AppController.logIn(uName, pwd);
					
				}
			}
		});
		
		JRootPane rootPane = SwingUtilities.getRootPane(btnLogin); 
		rootPane.setDefaultButton(btnLogin);
		
		/**
		 * Creates the Exit button
		 */
		
		JButton btnExit = new JButton("EXIT");
		btnExit.setBounds(230, 212, 88, 38);
		frame.getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(LoginDisplay.class.getResource("/authApp/img/user25.png")));
		lblNewLabel.setBounds(92, 90, 25, 27);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(LoginDisplay.class.getResource("/authApp/img/lock25.png")));
		lblNewLabel_1.setBounds(93, 136, 25, 27);
		frame.getContentPane().add(lblNewLabel_1);
		
		frame.setVisible(true);
		
	}
	public void remove() {
		frame.dispose();
	}
}