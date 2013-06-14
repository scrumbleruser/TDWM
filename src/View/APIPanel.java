package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import API.Revision;
import API.UserInfo;
import API.WikiBot;

import net.miginfocom.swing.MigLayout;

public class APIPanel {

	private WikiBot wikiBot;
	private String starturl;

	private JPanel panel = new JPanel();;
	private JPanel loginInfoContainer = new JPanel(new MigLayout());
	private JPanel preferencesContainer = new JPanel(new MigLayout());
	private JPanel resultsContainer = new JPanel(new MigLayout());

	private JTextField userField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JTextField usernameField = new JTextField();
	private JTextField articleField = new JTextField();
	private JTextField revisionField = new JTextField();
	private JTextField categoryField = new JTextField();
	private JTextArea resultArea = new JTextArea();
	private JScrollPane resultField = new JScrollPane(resultArea);

	private JComboBox<String> tbnamesComboBox = new JComboBox<String>();

	private JRadioButton rbWikiDE = new JRadioButton();
	private JRadioButton rbWikiEN = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();

	private JButton loginBt = new JButton("Login");
	private JButton sendBt = new JButton("Speichern");
	private JButton execBt = new JButton("Ausführen");
	private JButton clearBt = new JButton("Text Löschen");

	private static Statement stmt = null;

	/**
	 * Create the application.
	 */
	public APIPanel() {
		init();
	}

	private ActionListener execBTAL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cat = categoryField.getText();
			String values = "";
			ArrayList<String> catarray = new ArrayList<String>();
			if (!categoryField.getText().equals("")) {
				wikiBot.setArticle(cat);
				int i = 0;
				for (String s : wikiBot.getLinks()) {
					resultArea.append(s + "\n");
					catarray.add(s);
					System.out.println(i + " " + s);
					i++;
				}
				for (int f = 0; f < catarray.size(); f++) {
					values = "'" + cat + "','"
							+ catarray.get(f).toString() +"'" ;
					stmt = SQLPanel.con.setInsertInto(values, "kategorie");

				}
				
			}
			if (!usernameField.getText().equals("")) {
				UserInfo ui = new UserInfo(wikiBot.getWikiBot(),
						usernameField.getText());
				resultArea.append(ui.getUsername());
			}

			if (!articleField.getText().equals("")) {
				wikiBot.setArticle(articleField.getText());
				resultArea.append(wikiBot.getArticle().getTitle());
				int i = 0;
				JComboBox<String> cb = initComboBox(tbnamesComboBox);
				String myselectedTab = "" + cb.getSelectedItem();
				for (Revision r : wikiBot.getAllRevisions()) {
					resultArea.append(r.toString() + "\n");
					// stmt = mysql.setInsertInto(r.getName(), r.getRevid() +
					// "", r.getUserid() + "", r.getUser(), r.getTimestamp(),
					// r.getSize() +"" , r.isMinorchange()+"");

					// System.out.println("Meine Tab ist: " + myselectedTab);
						values = "'" + r.getName() + "'," + "'" + r.getRevid()
								+ "'," + "'" + r.getUserid() + "'," + "'"
								+ r.getUser() + "'," + "'" + r.getTimestamp()
								+ "'," + "'" + r.getSize() + "'," + "'"
								+ r.isMinorchange() + "'";
						stmt = SQLPanel.con
								.setInsertInto(values, myselectedTab);
					
					// SQLPanel.con.mysql_close();
				}

			}
		}
	};

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

		rbWikiDE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStarturl("http://de.wikipedia.org/w/");
			}
		});

		rbWikiEN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStarturl("http://en.wikipedia.org/w/");
			}
		});

		bg.add(rbWikiDE);
		bg.add(rbWikiEN);

		// Preferences
		usernameField
				.setPreferredSize(new Dimension(150, userField.getHeight()));
		articleField
				.setPreferredSize(new Dimension(150, userField.getHeight()));
		revisionField
				.setPreferredSize(new Dimension(150, userField.getHeight()));
		categoryField
				.setPreferredSize(new Dimension(150, userField.getHeight()));

		// Results
		resultField
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		resultField.setPreferredSize(new Dimension(500, 300));
		resultArea.setLineWrap(true);
		resultArea.setAutoscrolls(true);
		resultArea.setWrapStyleWord(true);

		DefaultCaret caret = (DefaultCaret) resultArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		clearBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				resultArea.setText("");

			}
		});

		// LoginButton
		loginBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				wikiBot = new WikiBot(userField.getText(), passwordField
						.getPassword().toString());
				wikiBot.setWiki(starturl);
				resultArea.append("Connected" + "\n");

			}
		});

		execBt.addActionListener(execBTAL);

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
		preferencesContainer.add(new JLabel("Artikel: "));
		preferencesContainer.add(articleField, "wrap");
		preferencesContainer.add(new JLabel("Revision: "));
		preferencesContainer.add(revisionField);
		preferencesContainer.add(new JLabel("Kategorie: "), "gapright 30");
		preferencesContainer.add(categoryField, "wrap");
		preferencesContainer.add(new JLabel("Tables: "));
		preferencesContainer.add(tbnamesComboBox);

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "wrap");
		resultsContainer.add(execBt, "span 2");
		resultsContainer.add(sendBt, "span");
		resultsContainer.add(clearBt, "wrap");
		resultsContainer.revalidate();

		// Add Subcontainers to the Maincontainer
		panel.add(loginInfoContainer, "wrap");
		panel.add(preferencesContainer, "wrap");
		panel.add(resultsContainer, "wrap");

		initData();
	}

	private void initData() {
		userField.setText("wissensmanagement");
		passwordField.setText("asdasd");
		starturl = "http://de.wikipedia.org/w/";
		categoryField.setText("Berlin");
		loginBt.doClick();
		SQLPanel pan = new SQLPanel();
		;
		for (String tab : pan.getTables()) {
			tbnamesComboBox.addItem(tab);
		}
		tbnamesComboBox.setSelectedItem("revision");
	}

	public JComboBox<String> initComboBox(final JComboBox<String> combobox) {

		combobox.addItemListener(new ItemListener() {
			@SuppressWarnings("unchecked")
			public void itemStateChanged(ItemEvent e) {
				JComboBox<String> selectedChoice = (JComboBox<String>) e
						.getItemSelectable();
			}
		});
		return combobox;

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

	public JTextField getArticleField() {
		return this.articleField;
	}

	public JTextField getRevisionField() {
		return this.revisionField;
	}

	public JTextField getCategoryField() {
		return this.categoryField;
	}

	public JComboBox getTbnamesComboBox() {
		return tbnamesComboBox;
	}

	public JTextArea getResultField() {
		return this.resultArea;
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

	public WikiBot getWikiBot() {
		return wikiBot;
	}

	public void setWikiBot(WikiBot wikiBot) {
		this.wikiBot = wikiBot;
	}

	public String getStarturl() {
		return starturl;
	}

	public void setStarturl(String starturl) {
		this.starturl = starturl;
	}

	public void setResultField(JScrollPane resultField) {
		this.resultField = resultField;
	}

}
