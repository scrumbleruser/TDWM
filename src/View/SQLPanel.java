package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
	// private JTextField tablenameField = new JTextField();
	private JTextArea statementField = new JTextArea();
	private JTextArea messageField = new JTextArea();
	private JTextArea resultField = new JTextArea();

	// ComboBox
	private JComboBox<String> tbnamesComboBox = new JComboBox<String>();

	// Buttons
	private JButton resultBt = new JButton("Ausführen");
	private JButton resetBt = new JButton("Reset");
	private JButton sendBt = new JButton("Senden");

	// Radiobuttons
	private JRadioButton rbSelect = new JRadioButton();
	private JRadioButton rbOtherSQL = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();

	private String message = "SQL-Statement is:";
	private String sql = "Select * from ";

	private static Statement stmt = null;
	private String[] tables = { "revision", "rechte", "kategorien", "artikel" };

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
		loginInfoContainer.add(new JLabel("Tablename: "));
		loginInfoContainer.add(tbnamesComboBox, "span");

		preferencesContainer.add(new JLabel("<html><b>Statement: </html>"),
				"wrap");
		preferencesContainer.add(new JLabel("Select"), "gapright 70");
		preferencesContainer.add(rbSelect, "wrap");
		preferencesContainer.add(new JLabel("Other"));
		preferencesContainer.add(rbOtherSQL, "wrap");
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

	public JRadioButton getRbSelect() {
		return this.rbSelect;
	}

	public JRadioButton getRbOtherSQL() {
		return this.rbOtherSQL;
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
	}

	// ActionListener
	private ActionListener resetal = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setDefaultDBCon();
		}
	};

	private ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String error_messages = "";
			String other_messages = "";
			getStatementField().setText(
					sql + "" + tbnamesComboBox.getSelectedItem());
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
					String content = con.getSelectStatement(sql);
					getResultField().setText(content);

				} else {
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
