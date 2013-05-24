import java.awt.Color;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;


public class ComparisonPanel {

	private JPanel panel;

	/**
	 * Create the application.
	 */
	public ComparisonPanel() {
		init();
	}

	/**
	 * Initialize the contents of the panel.
	 */
	private void init() {
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[]", "[]"));
		//------------------------------------------------
		
		panel.setBackground(Color.red);
		
	}
	
	public JPanel getComparisonPanel() {
		return this.panel;
	}

}
