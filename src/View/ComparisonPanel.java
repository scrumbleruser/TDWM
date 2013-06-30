package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Vergleiche.Article;
import Vergleiche.MainAnalyze;
import Vergleiche.TypeOfChange;
import Vergleiche.User;

import net.miginfocom.swing.MigLayout;
/**
 * Representation of the necessary GUI elements to show the results of the analyzation process.
 * @author Shimal
 */
public class ComparisonPanel {
	
	// Main panel
	private JPanel panel = new JPanel();
	
	// Container 1
	private JPanel container1 = new JPanel(new MigLayout());
	private JComboBox<String> c1ArticlesBox1 = new JComboBox<String>();
	private JTextArea c1ResultField1 = new JTextArea();
	private JScrollPane c1RF1Field1 = new JScrollPane(c1ResultField1);
	private JButton c1RemoveBt1 = new JButton("Reset");
	
	// Container 2
	private JPanel container2 = new JPanel(new MigLayout());
	private JComboBox<String> c2UserField1 = new JComboBox<String>();
	private JTextArea c2ResultField1 = new JTextArea();
	private JScrollPane c2RF1Field1 = new JScrollPane(c2ResultField1);
	private JTextArea c2ResultField2 = new JTextArea();
	private JScrollPane c2RF1Field2 = new JScrollPane(c2ResultField2);
	private JButton c2RemoveBt1 = new JButton("Reset");

	// Container 3
	private JPanel container3 = new JPanel(new MigLayout());
	private JComboBox<String> c3CategoryField1 = new JComboBox<String>();
	private JTextArea c3ResultField1 = new JTextArea();
	private JScrollPane c3RF1Field1 = new JScrollPane(c3ResultField1);
	private JComboBox<String> c3TypeOfChangesField = new JComboBox<String>();
	private JTextArea c3ResultField2 = new JTextArea();
	private JButton c3RemoveBt1 = new JButton("Reset");
	private JScrollPane c2RF2Field = new JScrollPane(c3ResultField2);
	
	private MainAnalyze test = new MainAnalyze();

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public ComparisonPanel() {
		init();
	}

	/**
	 * Initialize the contents of the panel.
	 * 
	 * @throws IOException
	 */
	private void init() {

		// Maincontainer
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);

		// Subcontainers
		container1.setPreferredSize(new Dimension(600,300));
		container2.setPreferredSize(new Dimension(600,400));
		container3.setPreferredSize(new Dimension(600,400));

		// Preferences
		c1ArticlesBox1.setPreferredSize(new Dimension(150, c1ArticlesBox1.getHeight()));	
		c2UserField1.setPreferredSize(new Dimension(150, c2UserField1.getHeight()));	
		c3CategoryField1.setPreferredSize(new Dimension(150, c3CategoryField1.getHeight()));	
		c3TypeOfChangesField.setPreferredSize(new Dimension(150, c3TypeOfChangesField.getHeight()));	
		
		// Results
		c1ResultField1.setPreferredSize(new Dimension(600, 300));
		c1ResultField1.setLineWrap(true);
		c1ResultField1.setWrapStyleWord(true);
		
		c2ResultField1.setPreferredSize(new Dimension(600, 300));
		c2ResultField1.setLineWrap(true);
		c2ResultField1.setWrapStyleWord(true);
		c2ResultField2.setPreferredSize(new Dimension(600, 300));
		c2ResultField2.setLineWrap(true);
		c2ResultField2.setWrapStyleWord(true);
		
		c3ResultField1.setPreferredSize(new Dimension(600, 300));
		c3ResultField1.setLineWrap(true);
		c3ResultField1.setWrapStyleWord(true);
		c3ResultField2.setPreferredSize(new Dimension(600, 300));
		c3ResultField2.setLineWrap(true);
		c3ResultField2.setWrapStyleWord(true);

		prepareGUI();
		
		// ActionListeners
		this.c1ArticlesBox1.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				typeOfChangesOnArticle();
			}
		});
		
		c1RemoveBt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c1ResultField1.setText("");
			}
		});
		
		c2RemoveBt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c2ResultField1.setText("");
				c2ResultField2.setText("");
			}
		});
		
		c3RemoveBt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c3ResultField1.setText("");
				c3ResultField2.setText("");
			}
		});
		
		this.c2UserField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				articleOfUser(c2UserField1.getSelectedItem().toString());
			}
		});
		
		this.c3CategoryField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if((c3CategoryField1.getSelectedItem() != null) &&
						(c3CategoryField1.getSelectedItem() != null))
				similarUsersByCategory(c3CategoryField1.getSelectedItem().toString());
			}
		});
		
		c3TypeOfChangesField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				similarUsersByTypeOfChange(c3TypeOfChangesField.getSelectedItem().toString());
				
			}
		});
		
		
		// Add components to the Subcontainers
		container1.add(
				new JLabel("<html>1. Welche Arten von Änderungen gibt es?<br>" +
						"2. Welche Muster aus Änderungen gibt es?<br></html>"), "span");
		container1.add(new JLabel("<html><b>Artikel: </html>"), "gapright 64");
		container1.add(c1ArticlesBox1, "wrap");
		container1.add(c1RF1Field1, "span");
		container1.add(c1RemoveBt1, "wrap");
		container2.add(
				new JLabel("<html>1. Welche Artikel ändert eine Person<br>" +
						"2. Wie gehören diese zusammen?<br>" +
						"3. Welche Arten von Änderungen führt sie durch?<br>" +
						"4. Kann man Typen von Nutzern erkennen?<br></html>"), "span");
		container2.add(new JLabel("<html><b>Autor: </html>"), "gapright 64");
		container2.add(c2UserField1, "wrap");
		container2.add(c2RF1Field1, "span");
		container2.add(new JLabel("<html><b>Änderungsart (Autortyp): </html>"), "span");
		container2.add(c2RF1Field2, "span");
		container2.add(c2RemoveBt1, "wrap");
		container3.add(
				new JLabel("<html>Welche Nutzer sind sich ähnlich?<br></html>"), "span");
		container3.add(new JLabel("<html><b>Kategorie: </html>"), "gapright 64");
		container3.add(c3CategoryField1, "wrap");
		container3.add(c3RF1Field1, "span");
		container3.add(new JLabel("<html><b>Änderungsart: </html>"));
		container3.add(c3TypeOfChangesField, "wrap");
		container3.add(c2RF2Field, "span");
		container3.add(c3RemoveBt1, "wrap");
		
		// Add Subcontainers to the Maincontainer
		panel.add(container1, "span");
		panel.add(container2, "span");
		panel.add(container3, "span");
		
	}
	
	/**
	 * Preparing the fields of the GUI through adding the specific data
	 */
	private void prepareGUI() {
		ArrayList<Article> articles = test.getAllArticles();
		for(int i=0; i<articles.size(); i++)
			this.c1ArticlesBox1.addItem(articles.get(i).getTitle());
			
		ArrayList<User> authors = test.getAllAuthorsOfAllArtciles();
		for(int i=0; i<authors.size(); i++)
			this.c2UserField1.addItem(authors.get(i).getName());
			
		for(int i=0; i<articles.size(); i++)
			this.c3CategoryField1.addItem(articles.get(i).getCategory());
			
		ArrayList<TypeOfChange> change = test.getAllChangesOfAllArticles();
		for(int i=0; i<change.size(); i++)
			if(change.get(i) != null)
				this.c3TypeOfChangesField.addItem(change.get(i).name());
		
		this.c1ArticlesBox1.setSelectedItem(null);
		this.c2UserField1.setSelectedItem(null);
		this.c3CategoryField1.setSelectedItem(null);
		this.c3TypeOfChangesField.setSelectedItem(null);
	}
	
	/**
	 * Showing the types of changes on an specific article
	 */
	private void typeOfChangesOnArticle() {
		try {
			ArrayList<TypeOfChange> changes = test.getAllChangesOfArticle(getC1ArticlesBox1().getSelectedItem().toString());
			this.c1ResultField1.append(getC1ArticlesBox1().getSelectedItem().toString() + "\n");
			for(int i=0; i<changes.size(); i++)
				if(changes.get(i) != null)
					this.c1ResultField1.append("-> " + changes.get(i).toString() + "\n");
		} catch(Exception ex) {
			System.err.println("Fehler!!!");
		}
	}
	
	/**
	 * Get articles of an author
	 * @param user
	 */
	private void articleOfUser(String user) {
		try {
			ArrayList<Article> article = test.getAllChangesOfArticleByUser(user);
			this.c2ResultField1.append(user + "\n");
			for(int i=0; i<article.size(); i++) {
				this.c2ResultField1.append("-> " + article.get(i).getTitle() + "\n");
				this.c2ResultField2.append(article.get(i).getTitle() + "\n");
				for(int j=0; j<article.get(i).getRevisions().size(); j++) 
					if(article.get(i).getRevisions().get(j).getAuthor().getName().equals(user))
						this.c2ResultField2.append("-> " + article.get(i).getRevisions().get(j).getTypeOfChange() + "\n\n");
			}
		} catch(Exception ex) {
			System.err.println("Fehler!!!");
		}
	}
	
	/**
	 * Get similar users by category
	 * @param category
	 */
	private void similarUsersByCategory(String category) {
		ArrayList<Article> articles = test.getAllArticles();
		this.c3ResultField1.append(category + "\n");
		for(int i=0; i<articles.size(); i++) 
			for(int j=0; j<articles.get(i).getRevisions().size(); j++) 
				if(articles.get(i).getCategory().equals(category))	
					this.c3ResultField1.append("->" + articles.get(i).getRevisions().get(j).getAuthor().getName() + "\n");
	}
	
	/**
	 * Get similar users by type of change
	 * @param category
	 */
	private void similarUsersByTypeOfChange(String typeOfChange) {
		ArrayList<Article> articles = test.getAllArticles();
		this.c3ResultField2.append(typeOfChange + "\n");
		for(int i=0; i<articles.size(); i++) 
			for(int j=0; j<articles.get(i).getRevisions().size(); j++) 
				if(articles.get(i).getRevisions().get(j).getTypeOfChange() != null)
					if(articles.get(i).getRevisions().get(j).getTypeOfChange().toString().equals(typeOfChange))	
						this.c3ResultField2.append("->" + articles.get(i).getRevisions().get(j).getAuthor().getName() + "\n");
	}
	
	/**
	 * Returns the main panel
	 */
	public JPanel getComparisonPanel() {
		return this.panel;
	}

	// Getter & Setter
	public JPanel getPanel() {
		return panel;
	}
	
	public JComboBox<String> getC1ArticlesBox1() {
		return c1ArticlesBox1;
	}

	public JTextArea getC1ResultField1() {
		return c1ResultField1;
	}


	public JComboBox<String> getC2UserField1() {
		return c2UserField1;
	}

	public JTextArea getC2ResultField1() {
		return c2ResultField1;
	}

	public JTextArea getC2ResultField2() {
		return c2ResultField2;
	}
	
	public JComboBox<String> getC3CategoryField() {
		return c3CategoryField1;
	}

	public JTextArea getC3ResultField1() {
		return c3ResultField1;
	}

	public JTextArea getC3ResultField2() {
		return c3ResultField2;
	}

	public JComboBox<String> getC3TypeOfChangesField() {
		return c3TypeOfChangesField;
	}

	
}
