package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * Mit diesem Panel lassen sich die Experten eines Artikels suchen. Dafür werden
 * die Anzahlen der Veränderungen eines Nutzers gezählt und sortiert.
 * 
 * @author Bernhard Hermes
 */
public class ExpertenPanel {

	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("Artikel");
	private JComboBox<String> article = new JComboBox<String>(getArticles());
	private JComboBox<String> article2 = new JComboBox<String>(getArticles());
	private JButton button = new JButton("Go");

	private JTextArea textArea = new JTextArea();
	private JScrollPane sp = new JScrollPane(textArea);

	public ExpertenPanel() {
		init();
	}

	private void search() {
		
		//SQL Daten abrufen
		String sql = "Select User , count(*) as count, sum(if(Artikel='"
				+ article.getSelectedItem() + "',1,0)) as '"
				+ article.getSelectedItem() + "' , sum(if(Artikel='"
				+ article2.getSelectedItem() + "',1,0)) as '"
				+ article2.getSelectedItem()
				+ "' from revision where Artikel='" + article.getSelectedItem()
				+ "' or Artikel='" + article2.getSelectedItem()
				+ "' group by User order by count";

		String content = SQLPanel.con.getSelectStatement(sql);
		StringBuilder print = new StringBuilder();
		
		//Nutzer die nur einen Artikel bearbeitet haben werden gefiltert
		int coun = 0;
		for(String s : content.split("\n"))
		{
			coun++;
			if(coun>2)
			{
			String[] t = s.split("  ");
			if(!t[2].equals("0") && !t[3].equals("0"))
				print.append(s+ "\n");
			}
			else
			{
				print.append(s+ "\n");
			}
		}
			
		textArea.setText(print.toString());
	}

	private String[] getArticles() {
		String sql = "Select Artikel from revision group by Artikel";

		String content = SQLPanel.con.getSelectStatement(sql);

		ArrayList<String> rtn = new ArrayList<String>();
		
		if (!content.equals("")) {
			for (String s : content.split("\n")) {
				rtn.add(s.trim());
			}

			rtn.remove(0);
			rtn.remove(0);

		}
		return rtn.toArray(new String[rtn.size()]);
	}

	private void init() {
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);

		sp.setPreferredSize(new Dimension(500, 300));

		article.setPreferredSize(new Dimension(150, article.getHeight()));
		article2.setPreferredSize(new Dimension(150, article2.getHeight()));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
		});

		panel.add(label, "span 4");
		panel.add(article);
		panel.add(article2);
		panel.add(button, "wrap");
		panel.add(sp, "span");

	}

	public JPanel getExpertenPanel() {

		return panel;
	}

}
