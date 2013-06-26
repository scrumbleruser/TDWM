package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import Vergleiche.*;

import net.miginfocom.swing.MigLayout;

public class ComparisonPanel {

	// Main- and Subcontainer
	private JPanel panel = new JPanel();;
	private JPanel userAnalyzeContainer = new JPanel(new MigLayout());
	private JPanel loginInfoContainer = new JPanel(new MigLayout());
	private JPanel preferencesContainer = new JPanel(new MigLayout());
	private JPanel resultsContainer = new JPanel(new MigLayout());

	// Components
	private JTextArea resultUser1 = new JTextArea();
	private JTextArea resultUser2 = new JTextArea();
	private JTextArea resultUser3 = new JTextArea();
	private JTextArea resultField = new JTextArea();

	private JComboBox<String> userBoxcb = new JComboBox<String>();
	private JComboBox<String> articleBoxcb = new JComboBox<String>();
	private JComboBox<String> selectStatcb = new JComboBox<String>();
	private JComboBox<String> userField1 = new JComboBox<String>();
	private JComboBox<String> userField2 = new JComboBox<String>();
	private JComboBox<String> userField3 = new JComboBox<String>();
	private JComboBox<String> userField = new JComboBox<String>();
	private JComboBox<String> articlesBox = new JComboBox<String>();
	private JComboBox<String> categoryField = new JComboBox<String>();

	// Buttons
	private JButton analyzestartBt = new JButton("Start");
	private JButton delcontentBt = new JButton("leeren");
	private JButton userCompareBt = new JButton("Vergleichen");
	private JButton categoryCompareBt = new JButton("Vergleichen");

	private final ArrayList<Article> articles = new ArrayList<Article>();
	protected Object getAnalyzeField;
	private DB db = new DB();
	private boolean laden = true;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public ComparisonPanel() throws IOException {
		init();
	}

	/**
	 * Initialize the contents of the panel.
	 * 
	 * @throws IOException
	 */
	private void init() throws IOException {

		// if (laden == true) {
		ArrayList<TempArticle> articlesInDB = db.getArticlesInDB();
		final ArrayList<Article> articles = new ArrayList<Article>();

		for (int i = 0; i < articlesInDB.size(); i++) {
			Category cat = new Category(articlesInDB.get(i).getCategory());
			ArrayList<Revision> revisions = new ArrayList<Revision>();
			// For efficiency we reduce the size of revisions
			for (int j = 0; j < (articlesInDB.get(i).getRevisions().size() / 50); j++) {
				User user = new User(articlesInDB.get(i).getRevisions().get(j)
						.getAuthor());
				File file = new File("res/"
						+ articlesInDB.get(i).getTitle().replaceAll(" ", "")
						+ "_Revision_"
						+ articlesInDB.get(i).getRevisions().get(j).getID()
								.replaceAll(" ", "") + ".txt");
				revisions.add(new Revision(user, file));
				// System.out.println("User ist:" + user +
				// "\n und Artikel ist: " + file);
			}
			articles.add(new Article(articlesInDB.get(i).getTitle(), cat,
					revisions));
		}

		// Maincontainer
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);

		// Subcontainers
		userAnalyzeContainer.setPreferredSize(new Dimension(600,
				userAnalyzeContainer.getHeight()));
		loginInfoContainer.setPreferredSize(new Dimension(600,
				loginInfoContainer.getHeight()));
		preferencesContainer.setPreferredSize(new Dimension(600,
				preferencesContainer.getHeight()));
		resultsContainer.setPreferredSize(new Dimension(600, 300));

		// User result fields
		userField1.setPreferredSize(new Dimension(150, userField1.getHeight()));

		resultUser1.setColumns(20);
		resultUser1.setRows(20);
		resultUser1.setLineWrap(true);
		resultUser1.setWrapStyleWord(true);

		resultUser2.setColumns(20);
		resultUser2.setRows(20);
		resultUser2.setLineWrap(true);
		resultUser2.setWrapStyleWord(true);

		resultUser3.setColumns(20);
		resultUser3.setRows(20);
		resultUser3.setLineWrap(true);
		resultUser3.setWrapStyleWord(true);

		for (int i = 0; i < articles.size(); i++) {
			String article = articles.get(i).getTitle();
			articlesBox.setSelectedItem(null);
			articlesBox.addItem(article);
		}

		userFieldal();

		articlesBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int artID = articlesBox.getSelectedIndex();
					for (int i = 0; i < articles.get(artID).getRevisions()
							.size(); i++) {
						String aa = articles.get(artID).getRevisions().get(i)
								.getAuthor().getName();
						userField1.addItem(aa);
					}
					changesByUser(articlesBox.getSelectedIndex());
				} catch (Exception ex) {
					resultField.setText("An " + articlesBox.getSelectedItem()
							+ " wurden keine Änderungen durchgeführt!");
				}

			}
		});

		// changesOnArticleByUser(articlesBox.getSelectedIndex(),
		// userField3.getSelectedItem().toString());

		// Preferences
		userField2.setPreferredSize(new Dimension(150, userField2.getHeight()));
		categoryField.setPreferredSize(new Dimension(355, categoryField
				.getHeight()));

		// Results
		resultField.setPreferredSize(new Dimension(600, 300));
		resultField.setLineWrap(true);
		resultField.setWrapStyleWord(true);

		// Add components to the Subcontainers
		// User Analysen sind hier definiert
		userAnalyzeContainer.add(new JLabel(
				"<html><b><H3>User Analysen: </h3></html>"), "span");
		userAnalyzeContainer.add(new JLabel(
				"<html><b>User - Artikel - Analysen </html>"), "span");
		userAnalyzeContainer.add(userBoxcb, "span 2");
		userAnalyzeContainer.add(articleBoxcb, "span 2");
		userAnalyzeContainer.add(selectStatcb, "wrap");
		userAnalyzeContainer.add(analyzestartBt, "span 2");
		userAnalyzeContainer.add(delcontentBt, "wrap");

		loginInfoContainer.add(
				new JLabel("<html><b>User verlgeichen: </html>"), "span");
		loginInfoContainer.add(new JLabel("Artikel: "), "gapright 64");
		loginInfoContainer.add(articlesBox, "wrap");
		loginInfoContainer.add(new JLabel("Autoren: "), "gapright 64");
		loginInfoContainer.add(new JLabel("Autor 1: "));
		loginInfoContainer.add(userField1);
		loginInfoContainer.add(new JLabel("Autor 2: "));
		loginInfoContainer.add(userField2);
		loginInfoContainer.add(userCompareBt, "wrap");
		loginInfoContainer.add(resultUser1, "span 3");
		loginInfoContainer.add(resultUser2, "span");
		loginInfoContainer.add(userField3, "wrap");
		loginInfoContainer.add(resultUser3);

		preferencesContainer.add(new JLabel(
				"<html><b>Kategorien vergleichen: </html>>"), "wrap");

		preferencesContainer.add(new JLabel("Kategorie: "), "gapright 107");
		preferencesContainer.add(categoryField);
		preferencesContainer.add(categoryCompareBt, "wrap");

		resultsContainer.add(new JLabel("<html><b>Ergebnis: </html>"), "wrap");
		resultsContainer.add(resultField, "span");

		// Add Subcontainers to the Maincontainer
		panel.add(userAnalyzeContainer, "span");
		panel.add(loginInfoContainer, "span");
		panel.add(preferencesContainer, "span");
		panel.add(resultsContainer, "span");

		analyzestartBt.addActionListener(analyzeal);
		delcontentBt.addActionListener(delcontental);
		setcbDefaultValues();

	}

	public void loadDBandCreateFiles() {
		try {
			ArrayList<TempArticle> articlesInDB = db.getArticlesInDB();
			final ArrayList<Article> articles = new ArrayList<Article>();

			for (int i = 0; i < articlesInDB.size(); i++) {
				Category cat = new Category(articlesInDB.get(i).getCategory());
				ArrayList<Revision> revisions = new ArrayList<Revision>();
				// For efficiency we reduce the size of revisions
				for (int j = 0; j < (articlesInDB.get(i).getRevisions().size() / 50); j++) {
					User user = new User(articlesInDB.get(i).getRevisions()
							.get(j).getAuthor());
					File file = new File("res/"
							+ articlesInDB.get(i).getTitle()
									.replaceAll(" ", "")
							+ "_Revision_"
							+ articlesInDB.get(i).getRevisions().get(j).getID()
									.replaceAll(" ", "") + ".txt");
					revisions.add(new Revision(user, file));
					// System.out.println("User ist:" + user +
					// "\n und Artikel ist: " + file);
				}
				articles.add(new Article(articlesInDB.get(i).getTitle(), cat,
						revisions));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// die vordefinierte User und Analysen sind hier enthalten
	public void setcbDefaultValues() {
		String[] user = new String[] { "Lear 21", "Exil", "Phoenix1983" };
		for (String username : user) {
			userBoxcb.addItem(username);
		}
		userBoxcb.setSelectedItem("Lear 21");

		String[] analysen = new String[] { "Analyse1", "Analyse2", "Analyse3" };
		for (String sql : analysen) {
			selectStatcb.addItem(sql);
		}
		selectStatcb.setSelectedItem("Analyse1");

		String[] artikel = new String[] { "Kaffee", "Berlin", "Wurzel_2",
				"Klothoide" };
		for (String art : artikel) {
			articleBoxcb.addItem(art);
		}
		articleBoxcb.setSelectedItem("Wurzel_2");
	}

	// Funktionalitäten
	public void artBoxal() {
		articlesBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int artID = articlesBox.getSelectedIndex();
					System.out.println("ArtID: " + artID);
					for (int i = 0; i < articles.get(artID).getRevisions()
							.size(); i++) {
						String aa = articles.get(artID).getRevisions().get(i)
								.getAuthor().getName();
						userField1.addItem(aa);
					}
					changesByUser(articlesBox.getSelectedIndex());
				} catch (Exception ex) {
					resultField.setText("An " + articlesBox.getSelectedItem()
							+ " wurden keine Änderungen durchgeführt!");
				}

			}
		});
	}

	public void userFieldal() {
		userField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					final AnalyzeArticle analyze = new AnalyzeArticle(articles);
					int artID = articlesBox.getSelectedIndex();
					String user = userField1.getSelectedItem().toString();
					for (int j = 0; j < articles.get(artID).getRevisions()
							.size(); j++) {
						resultUser3.setText(analyze.getArticlesOfOneUser(user)
								.get(j).getTitle());
					}

				} catch (Exception io) {

				}

			}
		});
	}

	// Das SQL-Ergebnis wird hier formatiert, indem die ersten beiden Zeilen
	// entfernt werden und nur der Rest zurück geliefert wird.
	private String formatContent(String content) {
		String[] line = content.split("\n");
		String formatcontent = "";
		for (int i = 2; i < line.length; i++) {
			// System.out.println(line[i]);
			formatcontent += line[i];
			formatcontent += "\n";
		}
		// formatcontent += "\n";
		return formatcontent;
	}

	// ActionListener für die User-Analysen und delete Textbox
	private ActionListener analyzeal = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String user = "" + userBoxcb.getSelectedItem();
			int analysenr = selectStatcb.getSelectedIndex();
			String artikel = "" + articleBoxcb.getSelectedItem();

			switch (analysenr) {
			case 0:
				String sql1 = "Select Artikel from revision where User='"
						+ user + "'group by Artikel";
				String content = SQLPanel.con.getSelectStatement(sql1);
				String myContent,
				mynewContent;
				myContent = formatContent(content);
				myContent = "Artikel des Users: " + user + "\n" + myContent;
				mynewContent = "";
				mynewContent += resultField.getText();
				// System.out.println(mynewContent);
				resultField.setText(mynewContent + myContent);
				// String[] splitt = myContent.split("\n");
				// for(String article : splitt){
				// article = article.trim();
				// articleBoxcb.addItem(article);
				// }
				break;
			case 1:
				String sql2 = "Select Kategorie from kategorie where Artikel='"
						+ artikel + "'";
				String article_content = SQLPanel.con.getSelectStatement(sql2);
				article_content = formatContent(article_content);
				article_content = "Kategorien zu Artikel: " + artikel
						+ "  vom User: " + user + "\n" + article_content;
				mynewContent = "";
				mynewContent += resultField.getText();
				resultField.setText(mynewContent + article_content);
				switch (user) {
				case "Lear 21":
					resultField
							.setText(resultField.getText()
									+ "Keine Zusammengehörigkeit bei den Kategorien \n");
					break;
				case "Exil":
					resultField
							.setText(resultField.getText()
									+ "Keine Zusammengehörigkeit bei den Kategorien \n");
					break;
				case "Phoenix1983":
					String text = "Gemeinsamkeiten vorhanden. \n"
							+ "Klothoide und Wurzel_2 gehören der Kategorie Mathematik an,\n"
							+ "Berlin und Kaffee sind in der Kategorie Europa vorhanden.\n";
					resultField.setText(resultField.getText() + "\n" + text);
					break;
				}
				break;

			case 2:
				String sql3 = "Select klAenderung from revision where Artikel='"
						+ artikel + "'" + "and User='" + user + "'";
				String changes_content = SQLPanel.con.getSelectStatement(sql3);
				changes_content = formatContent(changes_content);
				changes_content = "\nUser: " + user + " hat bei den Artikel: "
						+ artikel + " kleine Änderungen verfasst?" + "\n"
						+ changes_content;
				mynewContent = "";
				mynewContent += resultField.getText();
				resultField.setText(mynewContent + changes_content);
				break;

			default:
				resultField.setText("Analyse nicht gefunden");

			}
		}
	};

	private ActionListener delcontental = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			resultField.setText("");

		}
	};

	/**
	 * Return the panel.
	 */
	public JPanel getComparisonPanel() {
		return this.panel;
	}

	// Getter & Setter
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JPanel getLoginInfoContainer() {
		return loginInfoContainer;
	}

	public void setLoginInfoContainer(JPanel loginInfoContainer) {
		this.loginInfoContainer = loginInfoContainer;
	}

	public JPanel getPreferencesContainer() {
		return preferencesContainer;
	}

	public void setPreferencesContainer(JPanel preferencesContainer) {
		this.preferencesContainer = preferencesContainer;
	}

	public JPanel getResultsContainer() {
		return resultsContainer;
	}

	public void setResultsContainer(JPanel resultsContainer) {
		this.resultsContainer = resultsContainer;
	}

	public JComboBox<String> getUserField1() {
		return userField1;
	}

	public void setUserField1(JComboBox<String> userField1) {
		this.userField1 = userField1;
	}

	public JComboBox<String> getUserField2() {
		return userField2;
	}

	public void setUserField2(JComboBox<String> userField2) {
		this.userField2 = userField2;
	}

	public JTextArea getResultUser1() {
		return resultUser1;
	}

	public void setResultUser1(JTextArea resultUser1) {
		this.resultUser1 = resultUser1;
	}

	public JTextArea getResultUser2() {
		return resultUser2;
	}

	public void setResultUser2(JTextArea resultUser2) {
		this.resultUser2 = resultUser2;
	}

	public JComboBox<String> getUserField() {
		return userField;
	}

	public void setUserField(JComboBox<String> userField) {
		this.userField = userField;
	}

	public JComboBox<String> getArticlesBox() {
		return articlesBox;
	}

	public void setArticlesBox(JComboBox<String> articlesBox) {
		this.articlesBox = articlesBox;
	}

	public JComboBox<String> getCategoryField() {
		return categoryField;
	}

	public void setCategoryField(JComboBox<String> categoryField) {
		this.categoryField = categoryField;
	}

	public JTextArea getResultField() {
		return resultField;
	}

	public void setResultField(JTextArea resultField) {
		this.resultField = resultField;
	}

	public JButton getUserCompareBt() {
		return userCompareBt;
	}

	public void setUserCompareBt(JButton userCompareBt) {
		this.userCompareBt = userCompareBt;
	}

	public JButton getCategoryCompareBt() {
		return categoryCompareBt;
	}

	public void setCategoryCompareBt(JButton categoryCompareBt) {
		this.categoryCompareBt = categoryCompareBt;
	}

	private void changesByUser(int selectedArticle) throws IOException {
		final AnalyzeRevisionsOf change = new AnalyzeRevisionsOf(
				(Article) articles.get(selectedArticle));
		change.execute();
		for (int i = 0; i < articles.size(); i++) {
			for (int j = 0; j < articles.get(i).getRevisions().size(); i++) {

				System.out.println();
				resultField.setText(articles.get(i).getRevisions().get(j)
						.getAuthor().getName());
				// userField1.addItem(articles.get(i).getRevisions().get(j).getAuthor().getName());
				// userField2.addItem(articles.get(i).getRevisions().get(j).getAuthor().getName());
				// userField3.addItem(articles.get(i).getRevisions().get(j).getAuthor().getName());
			}
			userField1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						for (int j = 0; j < change.getAllTypeOfChangesOf(
								(String) userField1.getSelectedItem()).size(); j++) {
							resultUser1.setText(change
									.getAllTypeOfChangesOf(
											userField1.getSelectedItem()
													.toString()).get(j)
									.toString());
						}
					} catch (Exception ex) {
						resultUser1.setText(userField1.getSelectedItem()
								.toString()
								+ " hat keine Änderungen durchgeführt!");
					}
				}
			});
			userField2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						for (int j = 0; j < change.getAllTypeOfChangesOf(
								(String) userField2.getSelectedItem()).size(); j++) {
							resultUser2.setText(change
									.getAllTypeOfChangesOf(
											userField2.getSelectedItem()
													.toString()).get(j)
									.toString());
						}
					} catch (Exception ex) {
						resultUser2.setText(userField2.getSelectedItem()
								.toString()
								+ " hat keine Änderungen durchgeführt!");
					}
				}
			});
		}
	}

	private void changesOnArticleByUser(final int selectedArticle,
			final String user) throws IOException {
		final AnalyzeArticle aa = new AnalyzeArticle(articles);
		for (int i = 0; i < aa.getUserTypOfChangesForOneArticle(
				articles.get(selectedArticle), user).size(); i++) {
			userField3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						for (int j = 0; j < articles.get(selectedArticle)
								.getRevisions().size(); j++) {
							resultUser3.setText(aa.getArticlesOfOneUser(user)
									.get(j).getTitle());
						}
					} catch (Exception ex) {
						resultUser3.setText(userField3.getSelectedItem()
								.toString()
								+ " hat keine Änderungen durchgeführt!");
					}
				}
			});
		}
	}

	public JButton getDelcontentBt() {
		return delcontentBt;
	}

	public void setDelcontentBt(JButton delcontentBt) {
		this.delcontentBt = delcontentBt;
	}

	public JComboBox<String> getArticleBoxcb() {
		return articleBoxcb;
	}

	public void setArticleBoxcb(JComboBox<String> articleBoxcb) {
		this.articleBoxcb = articleBoxcb;
	}
}
