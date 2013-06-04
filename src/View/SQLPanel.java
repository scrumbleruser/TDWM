package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SQL.Mysql_connect;

import net.miginfocom.swing.MigLayout;

public class SQLPanel {

	// Main- and Subcontainer
	private JPanel panel = new JPanel();
	private JPanel loginInfoContainer = new JPanel(new MigLayout());
	private JPanel preferencesContainer = new JPanel(new MigLayout());
	private JPanel resultsContainer = new JPanel(new MigLayout());

	// Textfields
	private JTextField userField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JTextField hostField = new JTextField();
	private JTextField nameField = new JTextField();
	private JTextArea statementField = new JTextArea();
	private JTextArea messageField = new JTextArea();
	private JTextArea resultField = new JTextArea();

	private static Statement stmt = null;
	private static ResultSet result = null;

	// Buttons
	private JButton loginBt = new JButton("Ausführen");
	private JButton resetBt = new JButton("Reset");
	private JButton sendBt = new JButton("Senden");

	// Radiobuttons
	private JRadioButton rbSelect = new JRadioButton();
	private JRadioButton rbOtherSQL = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();
	
	private String message = "SQL-Statement is:";
	private String sql = "Select * from wikiinfos";

	/**
	 * Create the application.
	 */
	public SQLPanel() {
		init();
	}

	/**
	 * Initialize the contents of the panel.
	 */
	private void init() {
		
		// Maincontainer
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);

		// Subcontainers
		loginInfoContainer.setPreferredSize(new Dimension(800,
				loginInfoContainer.getHeight()));
		preferencesContainer.setPreferredSize(new Dimension(800,
				preferencesContainer.getHeight()));
		resultsContainer.setPreferredSize(new Dimension(800, 300));

		// Login
		userField.setPreferredSize(new Dimension(150, userField.getHeight()));
		passwordField.setPreferredSize(new Dimension(150, passwordField
				.getHeight()));

		// Host and Name
		hostField.setPreferredSize(new Dimension(345, hostField.getHeight()));
		nameField.setPreferredSize(new Dimension(345, nameField.getHeight()));

		// Statement und Message
		statementField.setPreferredSize(new Dimension(340, 200));
		statementField.setLineWrap(true);
		statementField.setWrapStyleWord(true);
		messageField.setPreferredSize(new Dimension(340, 200));
		
		bg.add(rbSelect);
		bg.add(rbOtherSQL);

		// Result
		resultField.setPreferredSize(new Dimension(450, 300));
		resultField.setLineWrap(true);
		resultField.setWrapStyleWord(true);

		// Add components to Subcontainer
		loginInfoContainer.add(new JLabel("<html><b>Datenbank: </html>"),
				"wrap");
		loginInfoContainer.add(new JLabel("User: "), "gapright 67");
		loginInfoContainer.add(userField);
		loginInfoContainer.add(new JLabel("Pass: "));
		loginInfoContainer.add(passwordField, "wrap");
		loginInfoContainer.add(new JLabel("DB Host: "));
		loginInfoContainer.add(hostField, "span");
		loginInfoContainer.add(new JLabel("DB Name: "));
		loginInfoContainer.add(nameField, "span");
		
		preferencesContainer.add(new JLabel("<html><b>Statement: </html>"), "wrap");
		preferencesContainer.add(new JLabel("Select"), "gapright 70");
		preferencesContainer.add(rbSelect, "wrap");
		preferencesContainer.add(new JLabel("Other"));
		preferencesContainer.add(rbOtherSQL, "wrap");
		preferencesContainer.add(new JLabel("SQL Statement: "));
		preferencesContainer.add(statementField, "span 3");
		preferencesContainer.add(loginBt, "span 2");
		preferencesContainer.add(resetBt, "wrap");
		preferencesContainer.add(new JLabel("Message: "));
		preferencesContainer.add(messageField, "span 3");

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span 3");
		resultsContainer.add(sendBt, "wrap");

		// Add Subcontainers to Maincontainer
		panel.add(loginInfoContainer, "span");
		panel.add(preferencesContainer, "span");
		panel.add(resultsContainer, "span");
		
		loginBt.addActionListener(al);
		resetBt.addActionListener(resetal);
		setDefaultDBCon();
	}

	public JPanel getSQLPanel() {
		return this.panel;
	}

	// Getter
	 public JTextField getUserField() {
	 return this.userField;
	 }
	
	 public JPasswordField getPasswordField() {
	 return this.passwordField;
	 }
	
	 public JTextField getHostField() {
	 return this.hostField;
	 }
	
	 public JTextField getNameField() {
	 return this.nameField;
	 }
	
	 public JTextArea getStatementField() {
	 return this.statementField;
	 }
	
	 public JTextArea getMessageField() {
	 return this.messageField;
	 }
	
	 public JTextArea getResultField() {
	 return this.resultField;
	 }
	
	 public JButton getLoginBt() {
	 return this.loginBt;
	 }
	 
	 public JButton getResetBt() {
	 return this.resetBt;
	 }
	
	 public JButton getSendBt() {
	 return this.sendBt;
	 }
	
	 public JRadioButton getRbSelect() {
	 return this.rbSelect;
	 }
	
	 public JRadioButton getRbOtherSQL() {
	 return this.rbOtherSQL;
	 }
	 
	 public void setDefaultDBCon(){
		 this.getUserField().setText("root");
		 this.getPasswordField().setText("usbw");
		 this.getHostField().setText("localhost:3307/");
		 this.getNameField().setText("wikiinfos");
		 this.statementField.setText(sql);
		 this.messageField.setText(message);
		 this.rbSelect.setSelected(true);
	 }
	
	private ActionListener resetal = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	setDefaultDBCon();
	    }
	};
	
	private ActionListener al = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	String error_messages ="";
	    	String other_messages = "";
	    	Mysql_connect con = new Mysql_connect(hostField.getText(),nameField.getText(),userField.getText(),passwordField.getText());
	    	if (rbSelect.isSelected() == true){
	    		String content = con.getSelectStatement(statementField.getText());
	    		error_messages = con.getErrorMessages();
	    		other_messages = con.getOtherMessage();
	    		if(error_messages == ""){
	    			// alles ok
	    			messageField.setText(other_messages);
	    			getStatementField().setText(getStatementField().getText());
	    			getResultField().setText(content);
	    		}else{
	    		    messageField.setText(error_messages);
	    			getResultField().setText("");
	    			con.mysql_close();
	    		}
	    	}
	    	if (rbOtherSQL.isSelected() == true){
	    		stmt = con.getOtherStatement(statementField.getText());
	    		error_messages = con.getErrorMessages();
	    		other_messages = con.getOtherMessage();
	    		if(error_messages == ""){
	    			messageField.setText(other_messages);
	    			String content = con.getSelectStatement(sql);
	    			getResultField().setText(content);
//	    			Update wikiinfos set Benutzername="Testuser" WHERE ID="13"
	    			
	    		}else{
	    		    messageField.setText(error_messages);
	    			getResultField().setText("");
	    			con.mysql_close();
	    		}
	    	}
	    	statementField.setText(statementField.getText());
	    	con.mysql_close();
	    }
	  };
}
