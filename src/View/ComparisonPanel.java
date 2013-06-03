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

public class ComparisonPanel {

	private JPanel panel = new JPanel();;
	private JPanel loginInfoContainer = new JPanel(new MigLayout());
	private JPanel preferencesContainer = new JPanel(new MigLayout());
	private JPanel resultsContainer = new JPanel(new MigLayout());

	private JTextField userField1 = new JTextField();
	private JTextField userField2 = new JTextField();

	private JTextField categoryField = new JTextField();

	private JTextArea resultField = new JTextArea();

	private JButton userCompareBt = new JButton("Vergleichen");
	private JButton categoryCompareBt = new JButton("Vergleichen");

	/**
	 * Create the application.
	 */
	public ComparisonPanel() {
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
		userField1.setPreferredSize(new Dimension(150, userField1.getHeight()));

		// Preferences
		userField2.setPreferredSize(new Dimension(150, userField2.getHeight()));
		categoryField.setPreferredSize(new Dimension(355, categoryField
				.getHeight()));

		// Results
		resultField.setPreferredSize(new Dimension(600, 300));
		resultField.setLineWrap(true);
		resultField.setWrapStyleWord(true);

		// Add components to the Subcontainers
		loginInfoContainer.add(new JLabel("<html><b>User verlgeichen: </html>"), "span");
		loginInfoContainer.add(new JLabel("Users: "), "gapright 64");
		loginInfoContainer.add(new JLabel("User 1: "));
		loginInfoContainer.add(userField1);
		loginInfoContainer.add(new JLabel("User 2: "));
		loginInfoContainer.add(userField2);
		loginInfoContainer.add(userCompareBt);

		preferencesContainer.add(
				new JLabel("<html><b>Kategorien vergleichen: </html>>"), "wrap");


		preferencesContainer.add(new JLabel("Kategorie: "), "gapright 107");
		preferencesContainer.add(categoryField);
		preferencesContainer.add(categoryCompareBt, "wrap");

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span");

		// Add Subcontainers to the Maincontainer
		panel.add(loginInfoContainer, "span");
		panel.add(preferencesContainer, "span");
		panel.add(resultsContainer, "span");

	}

	/**
	 * Return the panel.
	 */
	public JPanel getComparisonPanel() {
		return this.panel;
	}

	// Getter
	public JTextField getUserField1() {
		return this.userField1;
	}

	public JTextField getUserField2() {
		return this.userField2;
	}

	public JTextField getCategoryField() {
		return this.categoryField;
	}

	public JTextArea getResultField() {
		return this.resultField;
	}

	public JButton getUserCompareBt() {
		return this.userCompareBt;
	}

	public JButton getCategoryCompareBt() {
		return this.categoryCompareBt;
	}

}
