package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class ExpertenPanel {
	
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("Artikel");
	private JTextField article = new JTextField();
	private JButton button = new JButton("Go");

	private JTextArea textArea = new JTextArea();
	private JScrollPane sp = new JScrollPane(textArea);
	
	public ExpertenPanel()
	{
		init();
	}
	private void search() {
		String sql = "Select User , count(*) as count from revision where Artikel='"
				+ article.getText() + "' group by User order by count";

		String content = SQLPanel.con.getSelectStatement(sql);
		textArea.setText(content);
		
	}

	private void init() {
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);
		
		sp.setPreferredSize(new Dimension(500, 300));

		article.setText("Klothoide");
		article.setPreferredSize(new Dimension(150, article.getHeight()));
		button.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				search();				
			}
		});
		
		panel.add(label,"span 3");
		panel.add(article);
		panel.add(button,"wrap");
		panel.add(sp,"span");
		
	}

	public JPanel getExpertenPanel() {

		return panel;
	}

}
