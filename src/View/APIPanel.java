package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 * Panel mit dem Informionen vom Wiki gesammelt und in die Datenbank geschrieben
 * werden können.
 * 
 * @author Bernhard Hermes
 * 
 */
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
	// private JTextField articleField = new JTextField();
	private JComboBox<String> articlecb = new JComboBox<String>();
	// private JTextField revisionField = new JTextField();
	private JComboBox<String> categorycb = new JComboBox<String>();
	private JTextArea resultArea = new JTextArea();
	private JScrollPane resultField = new JScrollPane(resultArea);

	// private JComboBox<String> tbnamesComboBox = new JComboBox<String>();

	private JRadioButton rbWikiDE = new JRadioButton();
	private JRadioButton rbWikiEN = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();

	private JButton loginBt = new JButton("Login");
	private JButton execBt = new JButton("Ausführen");
	private JButton clearBt = new JButton("Text Löschen");

	/**
	 * Create the application.
	 */
	public APIPanel() {
		init();
	}

	// ActionListener der aufgerufen wird, wenn Ausführen gedrückt wird.
	private ActionListener execBTAL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cat = "" + categorycb.getSelectedItem();
			String values = "";
			ArrayList<String> category = new ArrayList<String>();
			if (!cat.equals("")) {
				wikiBot.setArticle(cat);
				int i = 0;
				for (String s : wikiBot.getCategories()) {
					resultArea.append(s + "\n");
					category.add(s);
					System.out.println(i + " " + s);
					i++;
				}
				for (int f = 0; f < category.size(); f++) {
					values = "'" + category.get(f).toString() + "','" + cat
							+ "'";
					SQLPanel.con.setInsertInto(values, "kategorie");

				}

			}
			if (!usernameField.getText().equals("")) {
				UserInfo ui = new UserInfo(wikiBot.getWikiBot(),
						usernameField.getText());
				resultArea.append(ui.getUsername());
			}

			String item = "" + articlecb.getSelectedItem();
			System.out.println("MySelected" + item);
			if (!item.equals("")) {
				wikiBot.setArticle(item);
				resultArea.append(wikiBot.getArticle().getTitle());
				// JComboBox<String> cb = initComboBox(tbnamesComboBox);
				// String myselectedTab = "" + cb.getSelectedItem();
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

					SQLPanel.con.setInsertInto(values, "revision");

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
		articlecb.setPreferredSize(new Dimension(150, userField.getHeight()));
		// revisionField
		// .setPreferredSize(new Dimension(150, userField.getHeight()));
		categorycb.setPreferredSize(new Dimension(150, userField.getHeight()));

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
		preferencesContainer.add(articlecb, "wrap");
		// preferencesContainer.add(new JLabel("Revision: "));
		// preferencesContainer.add(revisionField);
		preferencesContainer.add(new JLabel("Kategorie: "), "gapright 30");
		preferencesContainer.add(categorycb, "wrap");
		// preferencesContainer.add(new JLabel("Tables: "));
		// preferencesContainer.add(tbnamesComboBox);

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span");
		resultsContainer.add(execBt, "span 2");
		resultsContainer.add(clearBt, "wrap");
		resultsContainer.revalidate();

		// Add Subcontainers to the Maincontainer
		panel.add(loginInfoContainer, "wrap");
		panel.add(preferencesContainer, "wrap");
		panel.add(resultsContainer, "wrap");

		initData();
	}

	/**
	 * Initialize Data
	 */
	private void initData() {
		userField.setText("wissensmanagement");
		passwordField.setText("asdasd");
		starturl = "http://de.wikipedia.org/w/";
		loginBt.doClick();
		// for (String tab : pan.getTables()) {
		// tbnamesComboBox.addItem(tab);
		// }
		// tbnamesComboBox.setSelectedItem("revision");

		String[] artikel = { "","Homöopathie", "Christian_Lindner", "U2_(Band)",
				"PRISM_(Überwachungsprogramm)", "Datenschutz", "Deutschland",
				"Hacker", "Erfrischungsgetränk" };
		for (String art : artikel) {
			articlecb.addItem(art);
		}
		articlecb.setSelectedItem("");

		String[] kategorien = { "","Chaos Computer Club", "Datenschutz",
				"Deutschland", "Erfrischungsgetränk", "Hackerkultur",
				"Homöopathie", "Hydrotherapie", "Rockband", "The Dubliners",
				"U2", "Irische Band", "Unternehmensberater", "New Economy" };
		for (String kat : kategorien) {
			categorycb.addItem(kat);
		}
		categorycb.setSelectedItem("");

	}

	// public JComboBox<String> initComboBox(final JComboBox<String> combobox) {
	//
	// combobox.addItemListener(new ItemListener() {
	// @SuppressWarnings("unchecked")
	// public void itemStateChanged(ItemEvent e) {
	// JComboBox<String> selectedChoice = (JComboBox<String>) e
	// .getItemSelectable();
	// }
	// });
	// return combobox;
	//
	// }

	// Getter und Setter
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

	// public JTextField getRevisionField() {
	// return this.revisionField;
	// }
	
	// public JComboBox getTbnamesComboBox() {
	// return tbnamesComboBox;
	// }

	public JTextArea getResultField() {
		return this.resultArea;
	}

	public JButton getLoginBt() {
		return this.loginBt;
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

	public JComboBox<String> getArticlecb() {
		return articlecb;
	}

	public void setArticlecb(JComboBox<String> articlecb) {
		this.articlecb = articlecb;
	}

	public JComboBox<String> getCategorycb() {
		return categorycb;
	}

	public void setCategorycb(JComboBox<String> categorycb) {
		this.categorycb = categorycb;
	}

}
