package View;

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

	private JPanel panel = new JPanel();;
	private JPanel loginInfoContainer = new JPanel(new MigLayout());
	private JPanel preferencesContainer = new JPanel(new MigLayout());
	private JPanel resultsContainer = new JPanel(new MigLayout());

	private JTextField userField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JTextField usernameField = new JTextField();
	private JTextField ipField = new JTextField();
	private JTextField articleField = new JTextField();
	private JTextField revisionField = new JTextField();
	private JTextField categoryField = new JTextField();
	private JTextField dateField = new JTextField();
	private JTextField timeField = new JTextField();
	private JTextArea resultField = new JTextArea();

	private JRadioButton rbWikiDE = new JRadioButton();
	private JRadioButton rbWikiEN = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();

	private JButton loginBt = new JButton("Login");
	private JButton sendBt = new JButton("Speichern");

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

		// Maincontainer
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);

		// Subcontainers
		loginInfoContainer.setPreferredSize(new Dimension(600,
				loginInfoContainer.getHeight()));
		preferencesContainer.setPreferredSize(new Dimension(600,
				preferencesContainer.getHeight()));
		resultsContainer.setPreferredSize(new Dimension(600, 300));

		// Wiki and Login
		rbWikiDE.setSelected(true);
		userField.setPreferredSize(new Dimension(150, userField.getHeight()));
		passwordField.setPreferredSize(new Dimension(150, passwordField
				.getHeight()));

		bg.add(rbWikiDE);
		bg.add(rbWikiEN);

		// Preferences
		usernameField
				.setPreferredSize(new Dimension(150, userField.getHeight()));
		ipField.setPreferredSize(new Dimension(150, userField.getHeight()));
		articleField
				.setPreferredSize(new Dimension(419, userField.getHeight()));
		revisionField
				.setPreferredSize(new Dimension(150, userField.getHeight()));
		categoryField
				.setPreferredSize(new Dimension(150, userField.getHeight()));
		dateField.setPreferredSize(new Dimension(150, userField.getHeight()));
		timeField.setPreferredSize(new Dimension(150, userField.getHeight()));

		// Results
		resultField.setPreferredSize(new Dimension(500, 300));
		resultField.setLineWrap(true);
		resultField.setWrapStyleWord(true);

		// Add components to the Subcontainers
		loginInfoContainer.add(new JLabel("<html><b>Wikipedia: </html>"),
				"wrap");
		loginInfoContainer.add(new JLabel("Seite: "), "gapright 64");
		loginInfoContainer.add(rbWikiDE);
		loginInfoContainer.add(new JLabel("Deutschland"));
		loginInfoContainer.add(rbWikiEN);
		loginInfoContainer.add(new JLabel("Global (Englisch)"), "wrap");
		loginInfoContainer.add(new JLabel("Login: "));
		loginInfoContainer.add(new JLabel("User: "));
		loginInfoContainer.add(userField);
		loginInfoContainer.add(new JLabel("Pass: "));
		loginInfoContainer.add(passwordField);
		loginInfoContainer.add(loginBt);

		preferencesContainer.add(
				new JLabel("<html><b>Einstellungen: </html>>"), "wrap");
		preferencesContainer.add(new JLabel("User: "));
		preferencesContainer.add(usernameField);
		preferencesContainer.add(new JLabel("Oder IP-Adresse: "));
		preferencesContainer.add(ipField, "wrap");
		preferencesContainer.add(new JLabel("Artikel: "));
		preferencesContainer.add(articleField, "span");
		preferencesContainer.add(new JLabel("Revision: "));
		preferencesContainer.add(revisionField);
		preferencesContainer.add(new JLabel("Kategorie: "), "gapright 30");
		preferencesContainer.add(categoryField, "wrap");
		preferencesContainer.add(new JLabel("Datum: "));
		preferencesContainer.add(dateField);
		preferencesContainer.add(new JLabel("Uhrzeit: "));
		preferencesContainer.add(timeField);

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span 3");
		resultsContainer.add(sendBt, "wrap");

		// Add Subcontainers to the Maincontainer
		panel.add(loginInfoContainer, "span");
		panel.add(preferencesContainer, "span");
		panel.add(resultsContainer, "span");

	}

	/**
	 * Return the panel.
	 */
	public JPanel getAPIPanel() {
		return this.panel;
	}

	// Getter
	public JTextField getUserField() {
		return this.userField;
	}

	public JPasswordField getPasswordField() {
		return this.passwordField;
	}

	public JTextField getUsernameField() {
		return this.usernameField;
	}

	public JTextField getIPField() {
		return this.ipField;
	}

	public JTextField getArticleField() {
		return this.articleField;
	}

	public JTextField getRevisionField() {
		return this.revisionField;
	}

	public JTextField getCategoryField() {
		return this.categoryField;
	}

	public JTextField getDateField() {
		return this.dateField;
	}

	public JTextField getTimeField() {
		return this.timeField;
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
	
	public JRadioButton getRbWikiDE() {
		return this.rbWikiDE;
	}

	public JRadioButton getRbWikiEN() {
		return this.rbWikiEN;
	}

}
