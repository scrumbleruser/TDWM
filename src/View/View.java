package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;


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
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Technologien des Wissensmanagement");
		
		// Tabs
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Wiki API", new JScrollPane(new APIPanel().getAPIPanel()));
//		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.addTab("Datenbank", new JScrollPane(new SQLPanel().getSQLPanel()));
		tabbedPane.addTab("Vergleichen", new JScrollPane(new ComparisonPanel().getComparisonPanel()));
		tabbedPane.addTab("Expertensuche", new JScrollPane( /** hier soll ein Panel rein*/ ));
		tabbedPane.addTab("Graph", new Graph().getGraph() /** hier soll ein Panel rein*/ );
		
		
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

	}

}
