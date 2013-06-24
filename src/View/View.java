package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.io.IOException;


public class View {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialisierung der View
	 * @throws IOException 
	 */
	public View() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * add PanelTabs to GUI
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Technologien des Wissensmanagement");
		
		// Tabs
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Wiki API", new JScrollPane(new APIPanel().getAPIPanel()));
//		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.addTab("Datenbank", new JScrollPane(new SQLPanel().getSQLPanel()));
		tabbedPane.addTab("Vergleichen", new JScrollPane(new ComparisonPanel().getComparisonPanel()));
		tabbedPane.addTab("Expertensuche", new JScrollPane(new ExpertenPanel().getExpertenPanel()));
		tabbedPane.addTab("Graph", new JScrollPane(new Graph().getGraph()) );
			
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

	}

}