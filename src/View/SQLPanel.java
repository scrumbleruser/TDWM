import java.awt.Color;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;


public class SQLPanel {

	private JPanel panel;

	/**
	 * Create the application.
	 */
	public SQLPanel() {
		init();
	}

	/**
	 * Initialize the contents of the panel.
	 */
	private void init() {
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[]", "[]"));
		//------------------------------------------------
		
		panel.setBackground(Color.yellow);
		
		
	}
	
	public JPanel getSQLPanel() {
		return this.panel;
	}

}
