package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
	private JTextArea resultArea = new JTextArea();
	private JScrollPane resultField = new JScrollPane(resultArea);


	// ComboBox
	private JComboBox<String> tbnamesComboBox = new JComboBox<String>();

	// Buttons
	private JButton resultBt = new JButton("Ausführen");
	private JButton resetBt = new JButton("Reset");
	private JButton createTableBt = new JButton("CreateTable");

	// Radiobuttons
	private JRadioButton rbSelect = new JRadioButton();
	private JRadioButton rbOtherSQL = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();

	private JCheckBox cbSQL = new JCheckBox();
	
	// Messages
	private String message = "SQL-Statement is:";
	private String sql = "Select * from ";
	private static Statement stmt = null;
	private String error_messages = "";
	private String other_messages = "";
	private String mysql_messages = "";

	// Vordefinierte Tabellen
	private String[] tables = { "revision", "rechte", "kategorie" };

	/**
	 * Panel initalisieren
	 */
	public SQLPanel() {
		init();
	}
	
	// Initialisierung des Panels mit den Komponenten / Layout
	private void init() {

		// Maincontainer
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);

		// Subcontainers
		loginInfoContainer.setPreferredSize(new Dimension(1000,
				loginInfoContainer.getHeight()));
		preferencesContainer.setPreferredSize(new Dimension(1000,
				preferencesContainer.getHeight()));
		resultsContainer.setPreferredSize(new Dimension(1000, 300));

		// Login
		userField.setPreferredSize(new Dimension(150, userField.getHeight()));
		passwordField.setPreferredSize(new Dimension(150, passwordField
				.getHeight()));

		// Host and Name
		hostField.setPreferredSize(new Dimension(345, hostField.getHeight()));
		nameField.setPreferredSize(new Dimension(345, nameField.getHeight()));

		// Statement und Message
		statementField.setPreferredSize(new Dimension(340, 340));
		statementField.setLineWrap(true);
		statementField.setWrapStyleWord(true);
		
		//Message
		messageField.setPreferredSize(new Dimension(340, 500));
		
		//RadioButtons
		bg.add(rbSelect);
		bg.add(rbOtherSQL);

		// Result
		resultField.setPreferredSize(new Dimension(550, 300));
		resultArea.setLineWrap(true);
		resultArea.setWrapStyleWord(true);

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
		loginInfoContainer.add(createTableBt, "span 2");

		preferencesContainer.add(new JLabel("<html><b>Statement: </html>"),
				"wrap");
		preferencesContainer.add(new JLabel("Select"), "gapright 70");
		preferencesContainer.add(rbSelect, "wrap");
		preferencesContainer.add(new JLabel("Other"));
		preferencesContainer.add(rbOtherSQL, "wrap");
		preferencesContainer.add(new JLabel("Eigener SQL-Befehl ja/nein"));
		preferencesContainer.add(cbSQL, "wrap");
		preferencesContainer.add(new JLabel("SQL Statement: "));
		preferencesContainer.add(statementField, "span 3");
		preferencesContainer.add(resultBt, "wrap");
		preferencesContainer.add(new JLabel("Message: "));
		preferencesContainer.add(messageField, "span 3");
		preferencesContainer.add(resetBt, "wrap");

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span 3");

		// Add Subcontainers to Maincontainer
		panel.add(loginInfoContainer, "span");
		panel.add(preferencesContainer, "span");
		panel.add(resultsContainer, "span");
		
		// add ActionListener to Buttons
		resultBt.addActionListener(al);
		resetBt.addActionListener(resetal);
		
		// Selectbox befüllen - Vorauswahl treffen
		createTableBt.addActionListener(createTabelal);
		for (String tab : tables) {
			tbnamesComboBox.addItem(tab);
		}
		tbnamesComboBox.setSelectedItem("revision");
		setDefaultDBCon();
	}

	// MySQL-Verbindung mit den Logindaten
	public Mysql_connect con_mysql() {
		// Localhost
//		this.getUserField().setText("root");
//		this.getPasswordField().setText("usbw");
//		this.getHostField().setText("localhost:3307/");
//		this.getNameField().setText("wikiinfos");
		// Webhoster
		this.getUserField().setText("y9r106037");
		this.getPasswordField().setText("basilius789063");
		this.getHostField().setText("134.0.26.187:3306/");
		this.getNameField().setText("y9r106037_usr_web27_2");
		
		if(SQLPanel.con != null)
			return SQLPanel.con;
		
		return new Mysql_connect(getHostField().getText(),
				getNameField().getText(), getUserField().getText(),
				String.valueOf(getPasswordField().getPassword()));
	}

	// benötigte Tabelle/Tabellen erstellen
	public void createTable(String myselectedTab) {
		String sql_revision = "CREATE TABLE revision (ID mediumint(8) unsigned NOT NULL AUTO_INCREMENT,"
				+ "Artikel varchar(50), RevisionID varchar(50), UserID varchar(50), User varchar(50),"
				+ "DatumUhrzeit varchar(50), Groesse varchar(50),klAenderung varchar(50),"
				+ "PRIMARY KEY (ID))";
		String sql_rechte = "CREATE TABLE rechte (ID mediumint(8) unsigned NOT NULL AUTO_INCREMENT,"
				+ "User varchar(50), rechte varchar(255),"
				+ "PRIMARY KEY (ID))";
		String sql_kateorien = "CREATE TABLE kategorie (ID mediumint(8) unsigned NOT NULL AUTO_INCREMENT,"
				+ "Kategorie varchar(50), Artikel varchar(255),"
				+ "PRIMARY KEY (ID))";
		switch (myselectedTab) {
		case "revision":
			stmt = con_mysql().getOtherStatement(sql_revision);
			error_messages = con.getErrorMessages();
			other_messages = con.getOtherMessage();
			if (error_messages == "") {
				// alles ok
				messageField.setText(other_messages);
			} else {
				messageField.setText(error_messages);
				//con.mysql_close();
			}
			break;
		case "rechte":
			stmt = con_mysql().getOtherStatement(sql_rechte);
			error_messages = con.getErrorMessages();
			other_messages = con.getOtherMessage();
			if (error_messages == "") {
				// alles ok
				messageField.setText(other_messages);
			} else {
				messageField.setText(error_messages);
				//con.mysql_close();
			}
			break;
		case "kategorien":
			stmt = con_mysql().getOtherStatement(sql_kateorien);
			error_messages = con.getErrorMessages();
			other_messages = con.getOtherMessage();
			if (error_messages == "") {
				// alles ok
				messageField.setText(other_messages);
			} else {
				messageField.setText(error_messages);
				//con.mysql_close();
			}
			break;
		default:
			messageField.setText("Bitte eine Tabelle auswählen");
		}
	}

	// SQL-Befehle an die DB richten / Ausgabe im Resultfeld / Nötige Prüfungen vorher durchführen
	public void getResult() {
		// Eigene SQL-Befehl absetzen
		if (cbSQL.isSelected() == true && rbSelect.isSelected() == true) {
			getStatementField().setText(getStatementField().getText());
			messageField.setText("");
		}
		if (cbSQL.isSelected() == false && rbSelect.isSelected() == true) {
			getStatementField().setText(
					sql + "" + tbnamesComboBox.getSelectedItem());
			messageField.setText("");
		}
		
		// Select Befehle
		if (rbSelect.isSelected() == true) {
			String content = con.getSelectStatement(statementField.getText());
			error_messages = con.getErrorMessages();
			other_messages = con.getOtherMessage();
			mysql_messages = con.getmysqlMesage();
			con_mysql();
			if (error_messages == "") {
				// alles ok
				messageField.setText(mysql_messages + other_messages);
				getStatementField().setText(getStatementField().getText());
				getResultField().setText(content);
			} else {
				messageField.setText(mysql_messages + error_messages);
				//getResultField().setText("");
//				con.mysql_close();
			}
		}
		// andere SQL-Befehle
		if (rbOtherSQL.isSelected() == true) {
			stmt = con_mysql().getOtherStatement(statementField.getText());
			error_messages = con.getErrorMessages();
			other_messages = con.getOtherMessage();
			mysql_messages = con.getmysqlMesage();
			if (error_messages == "") {
				messageField.setText(mysql_messages + other_messages);
				String sql = getStatementField().getText();
				String content = con_mysql().getSelectStatement(sql);
				getResultField().setText(content);

			} else {
				messageField.setText(mysql_messages + error_messages);
				getResultField().setText("");
//				con.mysql_close();
			}
		}
		statementField.setText(statementField.getText());
		getStatusField().setText(con.getStateMysql());
	}

	// setzen der Default-Einstellungen und erstellen der Auswahlbox
	public void setDefaultDBCon() {
		this.statementField.setText("Select * from " + ""
				+ tbnamesComboBox.getSelectedItem());
		this.messageField.setText(message);
		this.rbSelect.setSelected(true);
		con = con_mysql();
		getStatusField().setText(con.getStateMysql());
	}

	// ActionListener
	private ActionListener resetal = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			con.mysql_close();
			con = null;
			setDefaultDBCon();
		}
	};

	private ActionListener createTabelal = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String myselectedTab = "" + tbnamesComboBox.getSelectedItem();
			createTable(myselectedTab);
		}
	};

	private ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			SQLPanel.con.deleteMessages();
			getResult();
		}
	};
	
	// ab hier nur noch getter/setter
	public JPanel getSQLPanel() {
		return this.panel;
	}

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
		return this.resultArea;
	}

	public JButton getresultBt() {
		return this.resultBt;
	}

	public JButton getResetBt() {
		return this.resetBt;
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
	
	public static Statement getStmt() {
		return stmt;
	}
	protected void finalize()
	{
	  //SQLPanel.con.mysql_close();
	}
}
