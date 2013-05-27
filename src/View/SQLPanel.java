package View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class SQLPanel {

	private JPanel panel;

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
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);
		// ------------------------------------------------

		JPanel dbInfoPanel = new JPanel(new MigLayout());
		dbInfoPanel.setBackground(Color.LIGHT_GRAY);
		dbInfoPanel.setPreferredSize(new Dimension(600,500));
		
		JPanel dbResultPanel = new JPanel(new MigLayout());
		dbResultPanel.setBackground(Color.LIGHT_GRAY);
		dbResultPanel.setPreferredSize(new Dimension(600,400));

		JLabel loginlbl = new JLabel("Database: ");
		JLabel dbUser = new JLabel("User: ");
		JTextField dbUserField = new JTextField();
		dbUserField.setPreferredSize(new Dimension(150, dbUserField.getHeight()));

		JLabel dbPassword = new JLabel("Pass: ");
		JPasswordField dbPasswordField = new JPasswordField();
		dbPasswordField.setPreferredSize(new Dimension(150, dbPasswordField
				.getHeight()));
		
		JLabel dbhostlbl = new JLabel("DB Host: ");
		JTextField dbhostField = new JTextField();
		dbhostField.setPreferredSize(new Dimension(345, dbhostField.getHeight()));

		JLabel dbnamelbl = new JLabel("DB Name: ");
		JPasswordField dbnameField = new JPasswordField();
		dbnameField.setPreferredSize(new Dimension(345, dbnameField
				.getHeight()));
		
		JLabel sqlStatementlbl = new JLabel("SQL Statement: ");
		JTextArea sqlStatementField = new JTextArea();
		sqlStatementField.setPreferredSize(new Dimension(340,200));
		sqlStatementField.setLineWrap(true);
		sqlStatementField.setWrapStyleWord(true);

		JButton loginBt = new JButton("Ausführen");
		
		JLabel sqlResultlbl = new JLabel("Ergebnis: ");
		JTextArea sqlResultField = new JTextArea();
		sqlResultField.setPreferredSize(new Dimension(340,300));
		sqlResultField.setLineWrap(true);
		sqlResultField.setWrapStyleWord(true);
		
		JButton sendBt = new JButton("Senden");

		dbInfoPanel.add(loginlbl, "wrap");
		dbInfoPanel.add(dbUser);
		dbInfoPanel.add(dbUserField); // Wrap to next row
		dbInfoPanel.add(dbPassword);
		dbInfoPanel.add(dbPasswordField, "wrap");
		dbInfoPanel.add(dbhostlbl);
		dbInfoPanel.add(dbhostField, "span");
		dbInfoPanel.add(dbnamelbl);
		dbInfoPanel.add(dbnameField, "span");
		dbInfoPanel.add(sqlStatementlbl);
		dbInfoPanel.add(sqlStatementField, "span 3");
		dbInfoPanel.add(loginBt, "wrap");
		
		dbResultPanel.add(sqlResultlbl, "gapright 38");
		dbResultPanel.add(sqlResultField, "span 3");
		dbResultPanel.add(sendBt, "wrap");


		panel.add(dbInfoPanel, "span");
		panel.add(dbResultPanel, "span");

	}

	public JPanel getSQLPanel() {
		return this.panel;
	}

}
