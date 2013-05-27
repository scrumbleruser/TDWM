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

public class APIPanel {

	private JPanel panel;

	/**
	 * Create the application.
	 */
	public APIPanel() {
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
		JPanel loginInfo = new JPanel(new MigLayout());
		JPanel dbResultPanel = new JPanel(new MigLayout());
		dbResultPanel.setBackground(Color.LIGHT_GRAY);
		loginInfo.setBackground(Color.LIGHT_GRAY);
		// loginInfo.setPreferredSize(new Dimension(loginInfo.getWidth(), 300));
		// loginInfo.setMaximumSize(loginInfo.getPreferredSize());

		JLabel wikiSitelbl = new JLabel("Wikipedia: ");
		JLabel deWikilbl = new JLabel("Deutschland");
		JRadioButton rbWikiDE = new JRadioButton();
		rbWikiDE.setSelected(true);

		JLabel enWikilbl = new JLabel("Global (Englisch)");
		JRadioButton rbWikiEN = new JRadioButton();

		JLabel loginlbl = new JLabel("Login: ");
		JLabel user = new JLabel("User: ");
		JTextField userField = new JTextField();
		userField.setPreferredSize(new Dimension(150, userField.getHeight()));

		JLabel password = new JLabel("Pass: ");
		JPasswordField passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(150, passwordField
				.getHeight()));

		ButtonGroup bg = new ButtonGroup();
		bg.add(rbWikiDE);
		bg.add(rbWikiEN);

		JButton loginBt = new JButton("Login");
		
		
		//*****************
		JLabel sqlResultlbl = new JLabel("Ergebnis: ");
		JTextArea sqlResultField = new JTextArea();
		sqlResultField.setPreferredSize(new Dimension(373,300));
		sqlResultField.setLineWrap(true);
		sqlResultField.setWrapStyleWord(true);
		
		JButton sendBt = new JButton("Senden");
		//*****************
		

		loginInfo.add(wikiSitelbl, "gapright 30");
		loginInfo.add(rbWikiDE);
		loginInfo.add(deWikilbl);
		loginInfo.add(rbWikiEN);
		loginInfo.add(enWikilbl, "wrap");
		loginInfo.add(loginlbl);
		loginInfo.add(user);
		loginInfo.add(userField); // Wrap to next row
		loginInfo.add(password);
		loginInfo.add(passwordField);
		loginInfo.add(loginBt);
		
		dbResultPanel.add(sqlResultlbl, "gapright 38");
		dbResultPanel.add(sqlResultField, "span 3");
		dbResultPanel.add(sendBt, "wrap");

		panel.add(loginInfo, "span");
		panel.add(dbResultPanel, "span");

	}

	public JPanel getAPIPanel() {
		return this.panel;
	}

}
