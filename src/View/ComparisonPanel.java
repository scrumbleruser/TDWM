package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Vergleiche.AnalyzeArticle;
import Vergleiche.AnalyzeRevisionsOf;
import Vergleiche.Article;
import Vergleiche.Category;
import Vergleiche.DB;
import Vergleiche.Revision;
import Vergleiche.TempArticle;
import Vergleiche.User;

import net.miginfocom.swing.MigLayout;

public class ComparisonPanel {

	private JPanel panel = new JPanel();;
	private JPanel loginInfoContainer = new JPanel(new MigLayout());
	private JPanel preferencesContainer = new JPanel(new MigLayout());
	private JPanel resultsContainer = new JPanel(new MigLayout());

	private JComboBox userField1 = new JComboBox();
	private JComboBox userField2 = new JComboBox();
	private JTextArea resultUser1 = new JTextArea();
	private JTextArea resultUser2 = new JTextArea();
	
	private JComboBox userField3 = new JComboBox();
	private JTextArea resultUser3 = new JTextArea();

	private JComboBox userField = new JComboBox();

	private JComboBox articlesBox = new JComboBox();
	private JComboBox categoryField = new JComboBox();

	private JTextArea resultField = new JTextArea();

	private JButton userCompareBt = new JButton("Vergleichen");
	private JButton categoryCompareBt = new JButton("Vergleichen");
	
	private final ArrayList<Article> articles = new ArrayList<Article>();
	private DB db = new DB();

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

		ArrayList<TempArticle> articlesInDB = db.getArticlesInDB();
		ArrayList<Article> articles = new ArrayList<Article>();
		
		for(int i=0; i<articlesInDB.size(); i++) {
			Category cat = new Category(articlesInDB.get(i).getCategory());
			ArrayList<Revision> revisions = new ArrayList<Revision>();
			// For efficiency we reduce the size of revisions
			for(int j=0; j<(articlesInDB.get(i).getRevisions().size()/50); j++) {
				User user = new User(articlesInDB.get(i).getRevisions().get(j).getAuthor());
				File file = new File("res/" + articlesInDB.get(i).getTitle().replaceAll(" ", "") + "_Revision_" + articlesInDB.get(i).getRevisions().get(j).getID().replaceAll(" ", "") + ".txt");
				revisions.add(new Revision(user, file));				
			}
			articles.add(new Article(articlesInDB.get(i).getTitle(), cat, revisions));
		}

		// Maincontainer
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);

		// Subcontainers
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
		
		articlesBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					changesByUser(articlesBox.getSelectedIndex());
				} catch(Exception ex) {
					resultField.setText("An " + articlesBox.getSelectedItem() + " wurden keine Änderungen durchgeführt!");
				}
				
			}
		});
		
//		changesOnArticleByUser(articlesBox.getSelectedIndex(), userField3.getSelectedItem().toString());

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

	public JComboBox getUserField1() {
		return userField1;
	}

	public void setUserField1(JComboBox userField1) {
		this.userField1 = userField1;
	}

	public JComboBox getUserField2() {
		return userField2;
	}

	public void setUserField2(JComboBox userField2) {
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

	public JComboBox getUserField() {
		return userField;
	}

	public void setUserField(JComboBox userField) {
		this.userField = userField;
	}

	public JComboBox getArticlesBox() {
		return articlesBox;
	}

	public void setArticlesBox(JComboBox articlesBox) {
		this.articlesBox = articlesBox;
	}

	public JComboBox getCategoryField() {
		return categoryField;
	}

	public void setCategoryField(JComboBox categoryField) {
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
		final AnalyzeRevisionsOf change = new AnalyzeRevisionsOf((Article) articles.get(selectedArticle));
		change.execute();
		for (int i = 0; i < articles.size(); i++) {
			for(int j=0; j<articles.get(i).getRevisions().size(); i++) {
				userField1.addItem(articles.get(i).getRevisions().get(j).getAuthor().getName());
				userField2.addItem(articles.get(i).getRevisions().get(j).getAuthor().getName());
				userField3.addItem(articles.get(i).getRevisions().get(j).getAuthor().getName());
			}
			userField1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						for (int j = 0; j < change.getAllTypeOfChangesOf((String) userField1.getSelectedItem()).size(); j++) {
							resultUser1.setText(change.getAllTypeOfChangesOf(userField1.getSelectedItem().toString()).get(j).toString());
						}
					} catch(Exception ex) {
						resultUser1.setText(userField1.getSelectedItem().toString() + " hat keine Änderungen durchgeführt!");
					}
				}
			});
			userField2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						for (int j = 0; j < change.getAllTypeOfChangesOf(
								(String) userField2.getSelectedItem()).size(); j++) {
							resultUser2.setText(change.getAllTypeOfChangesOf(userField2.getSelectedItem().toString()).get(j).toString());
						}
					} catch(Exception ex) {
						resultUser2.setText(userField2.getSelectedItem().toString() + " hat keine Änderungen durchgeführt!");
					}
				}
			});
		}
	}
	
	private void changesOnArticleByUser(final int selectedArticle, final String user) throws IOException {
		final AnalyzeArticle aa = new AnalyzeArticle(articles);
		for (int i = 0; i < aa.getUserTypOfChangesForOneArticle(articles.get(selectedArticle), user).size(); i++) {	
			userField3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						for(int j=0; j<articles.get(selectedArticle).getRevisions().size(); j++) {
							resultUser3.setText(aa.getArticlesOfOneUser(user).get(j).getTitle());
						}
					} catch(Exception ex) {
						resultUser3.setText(userField3.getSelectedItem().toString() + " hat keine Änderungen durchgeführt!");
					}
				}
			});
		}
	}

}
