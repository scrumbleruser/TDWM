package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SQL.mysql_connect;

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
	private JTextField messageField = new JTextField();
	private JTextArea resultField = new JTextArea();

	private static Statement stmt = null;
	private static ResultSet result = null;

	// Buttons
	private JButton loginBt = new JButton("Ausführen");
	private JButton sendBt = new JButton("Senden");

	// Radiobuttons
	private JRadioButton rbSelect = new JRadioButton();
	private JRadioButton rbOtherSQL = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();
	
	// Strings
	private String sql ="Select * from wikiinfos";


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
		loginInfoContainer.setPreferredSize(new Dimension(600,
				loginInfoContainer.getHeight()));
		preferencesContainer.setPreferredSize(new Dimension(600,
				preferencesContainer.getHeight()));
		resultsContainer.setPreferredSize(new Dimension(600, 300));

		// Login
		userField.setPreferredSize(new Dimension(150, userField.getHeight()));
		passwordField.setPreferredSize(new Dimension(150, passwordField
				.getHeight()));

		// Host and Name
		hostField.setPreferredSize(new Dimension(345, hostField.getHeight()));
		nameField.setPreferredSize(new Dimension(345, nameField.getHeight()));

		// Statement
		statementField.setPreferredSize(new Dimension(340, 200));
		statementField.setLineWrap(true);
		statementField.setWrapStyleWord(true);
		statementField.setText(sql);
		
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
		preferencesContainer.add(loginBt, "wrap");

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span 3");
		resultsContainer.add(sendBt, "wrap");

		// Add Subcontainers to Maincontainer
		panel.add(loginInfoContainer, "span");
		panel.add(preferencesContainer, "span");
		panel.add(resultsContainer, "span");

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
	
	 public JTextField getMessageField() {
	 return this.messageField;
	 }
	
	 public JTextArea getResultField() {
	 return this.resultField;
	 }
	
	 public JButton getLoginBt() {
	 return this.loginBt;
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

	public void getRS(ResultSet result, mysql_connect con){
		ArrayList<String> list = new ArrayList<String>();
		int i;
		String content = "";
		messageField.setText("Your SQL-Statment was: ");
		String column ="";
		try {
			if (result.first() == true){  // nur wenn Datensätze vorhanden sind ausführen
				int getcolumnncount = result.getMetaData().getColumnCount();
				for(i=1;i<getcolumnncount+1;i++){
					column += result.getMetaData().getColumnName(i);
					column += "  ";
				}
				list.add(column);
				list.add("\n");
				for(int e=1;e<getcolumnncount;e++){
					list.add(result.getString(e)+ "  ");
				}
				list.add("\n");
				while (result.next()) {
					for(int e=1;e<getcolumnncount;e++){
						list.add(result.getString(e)+ "  ");
					}
					list.add("\n");
				}
//				content += ((list.size()/7)) + " Datensätze vorhanden \n";
				for(i=0; i<list.size();i++){
					content += list.get(i).toString();
				}
				resultField.setText(content);
				messageField.setText("complete");
//					con.mysql_close();
//				Update wikiinfos set IP="192.168.8.83" WHERE ID="6"
			}else{
				messageField.setText("Keine Datensätze in der Tabelle vorhanden ");
				resultField.setText("");
				con.mysql_close();
			}
		} catch (SQLException sqle) {
			messageField.setText("SQL-Statement falsch oder MySQL-Db nicht erreichbar");
			resultField.setText("");
			con.mysql_close();
		}
	}
	
	private ActionListener al = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
//	    	String dbhost = userField.getText();
	    	mysql_connect con = new mysql_connect(hostField.getText(),nameField.getText(),userField.getText(),passwordField.getText());
	        
	    	if (rbSelect.isSelected() == true){
	    		// Parameter: SQL-Statement, Wert 1 nur für Select
				// Select - Ausgabe
				stmt = con.getStatement(statementField.getText(),1);
				try {
					result = stmt.executeQuery(statementField.getText());
				} catch (SQLException sqle) {
					messageField.setText("SQL-Statement falsch oder MySQL-Db nicht erreichbar");
					resultField.setText("");
					con.mysql_close();
				}
	    		if(result != null){
//	    			System.out.println(result);
			    	getRS(result,con);
			    	statementField.setText(statementField.getText());
			    	con.mysql_close();
	    		}
	    	}
	    	if (rbOtherSQL.isSelected() == true){
		    		stmt = con.getStatement(statementField.getText(),2);
		    		// Weitere DDL-Befehle - Upade,Create,Delete
					try {
						stmt.executeUpdate(statementField.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // Ausführen des angegeben SQL-Statements
//					System.out.println("SQL-Statement erfolgreich ausgeführt \n");
		    		statementField.setText(statementField.getText());
		    		con.mysql_close();
	    	}
	    	statementField.setText(statementField.getText());
	    }
	  };

}
