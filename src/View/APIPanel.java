package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import SQL.mysql_connect;
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
	private JTextField dateField = new JTextField();
	private JTextField timeField = new JTextField();
	private JTextArea resultField = new JTextArea();
	private JComboBox<String> tbnamesComboBox = new JComboBox<String>();

	private JRadioButton rbWikiDE = new JRadioButton();
	private JRadioButton rbWikiEN = new JRadioButton();
	private ButtonGroup bg = new ButtonGroup();

	private JButton loginBt = new JButton("Login");
	private JButton sendBt = new JButton("Speichern");
	private JButton execBt = new JButton("Ausführen");
	
	private static Statement stmt = null;

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
		dateField.setPreferredSize(new Dimension(150, userField.getHeight()));
		timeField.setPreferredSize(new Dimension(150, userField.getHeight()));

		// Results
		resultField.setPreferredSize(new Dimension(500, 300));
		resultField.setLineWrap(true);
		resultField.setWrapStyleWord(true);

		// LoginButton
		loginBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				wikiBot = new WikiBot(userField.getText(), passwordField
						.getPassword().toString());
				wikiBot.setWiki(starturl);
				resultField.append("Connected" + "\n");
				
			}
		});
		
		execBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!usernameField.getText().equals(""))	
				{
					UserInfo ui = new UserInfo(wikiBot.getWikiBot(), usernameField.getText());
					resultField.append(ui.getUsername());
				}

				if(!articleField.getText().equals(""))	
				{
					wikiBot.setArticle(articleField.getText());
					resultField.append(wikiBot.getArticle().getTitle());
					mysql_connect mysql = new mysql_connect();
//					int i=0;
					JComboBox<String> cb = initComboBox(tbnamesComboBox);
					String myselectedTab = ""+cb.getSelectedItem();
					for(Revision r : wikiBot.getAllRevisions())
					{
//						i++;
//						if(i == 15) break;
						resultField.append(r.toString() + "\n");
	//					stmt = mysql.setInsertInto(r.getName(), r.getRevid() + "", r.getUserid() + "", r.getUser(), r.getTimestamp(), r.getSize() +"" , r.isMinorchange()+"");
						String values = "";
//						System.out.println("Meine Tab ist: " + myselectedTab);
						switch(myselectedTab){
							case "revision":
								values +=
									"'" + r.getName() + "'," +
									"'" + r.getRevid() + "'," +
									"'" + r.getUserid() + "'," +
									"'" + r.getUser() + "'," +
									"'" + r.getTimestamp() + "'," +
									"'" + r.getSize() + "'," +
									"'" + r.isMinorchange() + "'";
									stmt = mysql.setInsertInto(values,myselectedTab);
									break;
							case "rechte":
								values +=
								"'" + r.getUserid() + "'," +
								"'" + r.isMinorchange() + "'";
								stmt = mysql.setInsertInto(values,myselectedTab);
								break;
							case "weitereTabellen":
								break;
							default:
								resultField.append("Tabelle nicht gefunden" + "\n");		
						}
					}
				}
			}
		});

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
		preferencesContainer.add(new JLabel("Datum: "));
		preferencesContainer.add(dateField);
		preferencesContainer.add(new JLabel("Uhrzeit: "));
		preferencesContainer.add(timeField, "wrap");
		preferencesContainer.add(new JLabel("Tables: "));
		preferencesContainer.add(tbnamesComboBox);

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span 3");
		resultsContainer.add(execBt, "wrap");
		resultsContainer.add(sendBt, "wrap");
		

		// Add Subcontainers to the Maincontainer
		panel.add(loginInfoContainer, "span");
		panel.add(preferencesContainer, "span");
		panel.add(resultsContainer, "span");

		initData();
	}

	private void initData() {
		userField.setText("wissensmanagement");
		passwordField.setText("asdasd");
		starturl = "http://de.wikipedia.org/w/";
		articleField.setText("Berlin");
		loginBt.doClick();
		SQLPanel pan = new SQLPanel();;
		for (String tab : pan.getTables()){
			tbnamesComboBox.addItem(tab);
		}
		tbnamesComboBox.setSelectedItem("rechte");
	}
	
	public JComboBox<String> initComboBox(final JComboBox<String> combobox){
		
		combobox.addItemListener(new ItemListener(){
			@SuppressWarnings("unchecked")
			public void itemStateChanged( ItemEvent e ) {
				JComboBox<String> selectedChoice = (JComboBox<String>)e.getItemSelectable();
		        }
		     } 
		);
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

	public JTextField getDateField() {
		return this.dateField;
	}

	public JTextField getTimeField() {
		return this.timeField;
	}
	
	public JComboBox getTbnamesComboBox() {
		return tbnamesComboBox;
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

}
