package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SQL.Mysql_connect;

import net.miginfocom.swing.MigLayout;

public class SQLPanel {

	public static Mysql_connect con;

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
	private JTextField statusField = new JTextField();
	private JTextArea statementField = new JTextArea();
	private JTextArea messageField = new JTextArea();
	private JTextArea resultField = new JTextArea();

	// ComboBox
	private JComboBox<String> tbnamesComboBox = new JComboBox<String>();
	private JComboBox<String> tbnamesComboBoxUser = new JComboBox<String>();

	// Buttons
	private JButton resultBt = new JButton("Ausführen");
	private JButton resetBt = new JButton("Reset");
	private JButton sendBt = new JButton("Send");
	private JButton createTableBt = new JButton("CreateTable");

	// Radiobuttons
	private JRadioButton rbSelect = new JRadioButton();
	private JRadioButton rbOtherSQL = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();
	
	private JCheckBox cbSQL = new JCheckBox();

	private String message = "SQL-Statement is:";
	private String sql = "Select * from ";

	private static Statement stmt = null;
	private static Connection connect = null;
	private String[] tables = { "revision", "rechte", "kategorien", "artikel" };
	
	private String error_messages = "";
	private String other_messages = "";

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
		// tablenameField.setPreferredSize(new Dimension(345,
		// tablenameField.getHeight()));

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
		loginInfoContainer.add(new JLabel("MySQL-Status: "));
		loginInfoContainer.add(statusField, "span");
		loginInfoContainer.add(new JLabel("Tablename: "));
		loginInfoContainer.add(tbnamesComboBox, "span");
		loginInfoContainer.add(new JLabel("User: "));
		loginInfoContainer.add(tbnamesComboBoxUser, "gapright");
		loginInfoContainer.add(createTableBt, "wrap");
		
		preferencesContainer.add(new JLabel("<html><b>Statement: </html>"), "wrap");
		preferencesContainer.add(new JLabel("Select"), "gapright 70");
		preferencesContainer.add(rbSelect, "wrap");
		preferencesContainer.add(new JLabel("Other"));
		preferencesContainer.add(rbOtherSQL, "wrap");
		preferencesContainer.add(new JLabel("Eigener SQL-Befehl ja/nein"));
		preferencesContainer.add(cbSQL, "wrap");
		preferencesContainer.add(new JLabel("SQL Statement: "));
		preferencesContainer.add(statementField, "span 3");
		preferencesContainer.add(resultBt, "span 2");
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

		resultBt.addActionListener(al);
		resetBt.addActionListener(resetal);
		createTableBt.addActionListener(createTabelal);
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
	
	public JTextField getStatusField() {
		return this.statusField;
	}
	
	public JComboBox<String> getTbnamesComboBox() {
		return tbnamesComboBox;
	}
	public JComboBox<String> getTbnamesComboBoxUser() {
		return tbnamesComboBoxUser;
	}
	

	public String[] getTables() {
		return tables;
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

	public JButton getresultBt() {
		return this.resultBt;
	}

	public JButton getResetBt() {
		return this.resetBt;
	}

	public JButton getSendBt() {
		return this.sendBt;
	}
	
	public JButton getCreateTableBt() {
		return this.createTableBt;
	}
	
	public JRadioButton getRbSelect() {
		return this.rbSelect;
	}

	public JRadioButton getRbOtherSQL() {
		return this.rbOtherSQL;
	}
	
	public JCheckBox getCbSQL() {
		return this.cbSQL;
	}

	// setzen der Default-Einstellungen
	public void setDefaultDBCon() {
		this.getUserField().setText("y9r106037");
		this.getPasswordField().setText("basilius789063");
		this.getHostField().setText("134.0.26.187:3306/");
		this.getNameField().setText("y9r106037_usr_web27_2");
		// this.tablenameField.setText("revision");
		for (String tab : tables) {
			tbnamesComboBox.addItem(tab);
		}
		tbnamesComboBox.setSelectedItem("revision");

		this.statementField.setText("Select * from " + ""
				+ tbnamesComboBox.getSelectedItem());
		this.messageField.setText(message);
		this.rbSelect.setSelected(true);
		this.getStatusField().setText("off");
	}

	// ActionListener
	private ActionListener resetal = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			SQLPanel.con = new Mysql_connect(getHostField().getText(),
					getNameField().getText(), getUserField().getText(),
					String.valueOf(getPasswordField().getPassword()));
			con.getStateMysql();
			ArrayList<String> userlist = new ArrayList<String>();
			userlist = con.getUserName();
			for (String usernames : userlist) {
				tbnamesComboBoxUser.addItem(usernames);
			}

			con.mysql_close();
			con.getStateMysql();
			setDefaultDBCon();
		}
	};
	
	// ActionListener
		private ActionListener createTabelal = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SQLPanel.con = new Mysql_connect(getHostField().getText(),
						getNameField().getText(), getUserField().getText(),
						String.valueOf(getPasswordField().getPassword()));
				String myselectedTab = "" + tbnamesComboBox.getSelectedItem();
//				System.out.println(myselectedTab);
				String sql_revsion=
						"CREATE TABLE revision_test (ID mediumint(8) unsigned NOT NULL AUTO_INCREMENT," +
						"Artikel varchar(50), RevisionID varchar(50), UserID varchar(50), User varchar(50)," +
						"DatumUhrzeit varchar(50),Groesse varchar(50),klAenderung varchar(50)," +
						"PRIMARY KEY (ID))";
				String sql_rechte = 
						"CREATE TABLE rechte_test (ID mediumint(8) unsigned NOT NULL AUTO_INCREMENT," +
						"UserID varchar(50), rechte varchar(255)," +
						"PRIMARY KEY (ID))";
				switch(myselectedTab){
					case "revision":
							stmt = SQLPanel.con.getOtherStatement(sql_revsion);
							error_messages = con.getErrorMessages();
							other_messages = con.getOtherMessage();
							if (error_messages == "") {
								// alles ok
								messageField.setText(other_messages);
							} else {
								messageField.setText(error_messages);
								con.mysql_close();
							}
							break;
					case "rechte":
							stmt = SQLPanel.con.getOtherStatement(sql_rechte);
							error_messages = con.getErrorMessages();
							other_messages = con.getOtherMessage();
							if (error_messages == "") {
								// alles ok
								messageField.setText(other_messages);
							} else {
								messageField.setText(error_messages);
								con.mysql_close();
							}
							break;
					case "weitereTabellen":
							break;
					default:
						messageField.setText("Bitte eine Tabelle auswählen");		
				}
			}
		};

	private ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (cbSQL.isSelected() == true && rbSelect.isSelected() == true){
				getStatementField().setText(
						getStatementField().getText());
			}
			if (cbSQL.isSelected() == false && rbSelect.isSelected() == true){
				getStatementField().setText(
						sql + "" + tbnamesComboBox.getSelectedItem());
			}
			SQLPanel.con = new Mysql_connect(getHostField().getText(),
					getNameField().getText(), getUserField().getText(),
					String.valueOf(getPasswordField().getPassword()));

			// ArrayList<String> listColumn = new ArrayList<String>();
			// listColumn = con.getColumnName(tablenameField.getText());
			// for(int i=0; i<listColumn.size();i++){
			// System.out.println("Das ist der: " + i + "Datensatz");
			// System.out.println(listColumn.get(i).toString());
			// }

			// Select Befehle
			if (rbSelect.isSelected() == true) {
				String content = con.getSelectStatement(statementField
						.getText());
				error_messages = con.getErrorMessages();
				other_messages = con.getOtherMessage();
				if (error_messages == "") {
					// alles ok
					messageField.setText(other_messages);
					getStatementField().setText(getStatementField().getText());
					getResultField().setText(content);
				} else {
					messageField.setText(error_messages);
					getResultField().setText("");
					con.mysql_close();
				}
			}
			// Other SQL-Befehle
			if (rbOtherSQL.isSelected() == true) {
				stmt = con.getOtherStatement(statementField.getText());
				error_messages = con.getErrorMessages();
				other_messages = con.getOtherMessage();
				if (error_messages == "") {
					messageField.setText(other_messages);
					String sql = getStatementField().getText();
					String content = con.getSelectStatement(sql);
					getResultField().setText(content);

				} else {
					messageField.setText(error_messages);
					getResultField().setText("");
					con.mysql_close();
				}
			}
			statementField.setText(statementField.getText());
			con.getStateMysql();
		}
	};
}
