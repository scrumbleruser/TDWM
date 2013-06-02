package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SQL.mysql_connect;

import net.miginfocom.swing.MigLayout;

public class SQLPanel {

	private JPanel panel;
	private String sql ="Select * from wikiinfos";
	JTextField dbhostField = new JTextField();
	JPasswordField dbnameField = new JPasswordField();
	JTextField dbUserField = new JTextField();
	JPasswordField dbPasswordField = new JPasswordField();
	JTextArea sqlStatementField = new JTextArea();
	JTextArea sqlResultField = new JTextArea();
	JTextField messageField = new JTextField();
	static Statement stmt = null;
	static ResultSet result = null;
	
	JRadioButton rbSelect = new JRadioButton();
	JRadioButton rbOtherSQL = new JRadioButton();
	ButtonGroup bgsql = new ButtonGroup();

	/**
	 * Create the application.
	 */
	public SQLPanel() {
		init();	
	}
	
	public void getRS(ResultSet result, mysql_connect con){
		ArrayList<String> list = new ArrayList<String>();
		int i;
		String content = "";
		messageField.setText("Your SQL-Statment was: ");
		String column ="";
		try {
			if (result.first() == true){  // nur wenn Datensätze vorhanden sind ausführen
				int getcolumnncount = result.getMetaData().getColumnCount();
				for(i=1;i<getcolumnncount+1;i++){
					column += result.getMetaData().getColumnName(i);
					column += "  ";
				}
				list.add(column);
				list.add("\n");
				for(int e=1;e<getcolumnncount;e++){
					list.add(result.getString(e)+ "  ");
				}
				list.add("\n");
				while (result.next()) {
					for(int e=1;e<getcolumnncount;e++){
						list.add(result.getString(e)+ "  ");
					}
					list.add("\n");
				}
//				content += ((list.size()/7)) + " Datensätze vorhanden \n";
				for(i=0; i<list.size();i++){
					content += list.get(i).toString();
				}
				sqlResultField.setText(content);
				messageField.setText("complete");
//					con.mysql_close();
//				Update wikiinfos set IP="192.168.8.83" WHERE ID="6"
			}else{
				messageField.setText("Keine Datensätze in der Tabelle vorhanden ");
				sqlResultField.setText("");
				con.mysql_close();
			}
		} catch (SQLException sqle) {
			messageField.setText("SQL-Statement falsch oder MySQL-Db nicht erreichbar");
			sqlResultField.setText("");
			con.mysql_close();
		}
	}
	
	private ActionListener al = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
//	    	String dbhost = dbUserField.getText();
	    	mysql_connect con = new mysql_connect(dbhostField.getText(),dbnameField.getText(),dbUserField.getText(),dbPasswordField.getText());
	        
	    	if (rbSelect.isSelected() == true){
	    		// Parameter: SQL-Statement, Wert 1 nur für Select
				// Select - Ausgabe
				stmt = con.getStatement(sqlStatementField.getText(),1);
				try {
					result = stmt.executeQuery(sqlStatementField.getText());
				} catch (SQLException sqle) {
					messageField.setText("SQL-Statement falsch oder MySQL-Db nicht erreichbar");
					sqlResultField.setText("");
					con.mysql_close();
				}
	    		if(result != null){
//	    			System.out.println(result);
			    	getRS(result,con);
			    	sqlStatementField.setText(sqlStatementField.getText());
			    	con.mysql_close();
	    		}
	    	}
	    	if (rbOtherSQL.isSelected() == true){
		    		stmt = con.getStatement(sqlStatementField.getText(),2);
		    		// Weitere DDL-Befehle - Upade,Create,Delete
					try {
						stmt.executeUpdate(sqlStatementField.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // Ausführen des angegeben SQL-Statements
//					System.out.println("SQL-Statement erfolgreich ausgeführt \n");
		    		sqlStatementField.setText(sqlStatementField.getText());
		    		con.mysql_close();
	    	}
	    	sqlStatementField.setText(sqlStatementField.getText());
	    }
	  };

	/**
	 * Initialize the contents of the panel.
	 */
	private void init() {
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);
		// ------------------------------------------------

		JPanel dbInfoPanel = new JPanel(new MigLayout());
		dbInfoPanel.setBackground(Color.LIGHT_GRAY);
		dbInfoPanel.setPreferredSize(new Dimension(800,500));
		
		JPanel dbResultPanel = new JPanel(new MigLayout());
		dbResultPanel.setBackground(Color.LIGHT_GRAY);
		dbResultPanel.setPreferredSize(new Dimension(800,400));

		JLabel dbUser = new JLabel("User: ");
		JLabel loginlbl = new JLabel("Database: ");
		dbUserField.setPreferredSize(new Dimension(150, dbUserField.getHeight()));
		dbUserField.setText("root");

		JLabel dbPassword = new JLabel("Pass: ");
		dbPasswordField.setPreferredSize(new Dimension(150, dbPasswordField.getHeight()));
		dbPasswordField.setText("usbw");
		
		JLabel dbhostlbl = new JLabel("DB Host: ");
		dbhostField.setPreferredSize(new Dimension(345, dbhostField.getHeight()));
		dbhostField.setText("localhost:3307/");

		JLabel dbnamelbl = new JLabel("DB Name: ");
		dbnameField.setPreferredSize(new Dimension(345, dbnameField
				.getHeight()));
		dbnameField.setText("wikiinfos");
		
		JLabel selectlbl = new JLabel("Select");
		rbSelect.setActionCommand("1");
		rbSelect.setSelected(true);
		JLabel otherSQLlbl = new JLabel("Other Statement");
		rbOtherSQL.setActionCommand("2");
		bgsql.add(rbSelect);
		bgsql.add(rbOtherSQL);
//		rbSelect.addActionListener(al);
//		rbOtherSQL.addActionListener(al);
		
		JLabel sqlStatementlbl = new JLabel("SQL Statement: ");
		sqlStatementField.setPreferredSize(new Dimension(540,300));
		sqlStatementField.setLineWrap(true);
		sqlStatementField.setWrapStyleWord(true);
		sqlStatementField.setText(sql);

		JButton loginBt = new JButton("Ausführen");
		JLabel messagelbl = new JLabel("Message");
		messageField.setPreferredSize(new Dimension(340,200));
		messageField.setText("Your SQL-Statment was: ");
		loginBt.addActionListener(al);
		
		JLabel sqlResultlbl = new JLabel("Ergebnis: ");
		sqlResultField.setPreferredSize(new Dimension(540,300));
		sqlResultField.setLineWrap(true);
		sqlResultField.setWrapStyleWord(true);
		sqlResultField.setText("Ergebnis");
		
		JButton sendBt = new JButton("Senden");

		dbInfoPanel.add(loginlbl, "wrap");
		dbInfoPanel.add(dbUser);
		dbInfoPanel.add(dbUserField); // Wrap to next row
		dbInfoPanel.add(dbPassword);
		dbInfoPanel.add(dbPasswordField, "wrap");
		dbInfoPanel.add(dbhostlbl);
		dbInfoPanel.add(dbhostField, "span");
		dbInfoPanel.add(dbnamelbl);
		dbInfoPanel.add(dbnameField, "span");
		dbInfoPanel.add(selectlbl, "gapright 30");
		dbInfoPanel.add(rbSelect, "wrap");
		dbInfoPanel.add(otherSQLlbl, "gapright 30");
		dbInfoPanel.add(rbOtherSQL, "wrap");
		dbInfoPanel.add(sqlStatementlbl);
		dbInfoPanel.add(sqlStatementField, "span 3");
		dbInfoPanel.add(loginBt, "wrap");
		dbInfoPanel.add(messagelbl,"gapright 30");
		dbInfoPanel.add(messageField, "span 3");
		
		dbResultPanel.add(sqlResultlbl, "gapright 38");
		dbResultPanel.add(sqlResultField, "span 3");
		dbResultPanel.add(sendBt, "wrap");

		panel.add(dbInfoPanel, "span");
		panel.add(dbResultPanel, "span");		
	}

	public JPanel getSQLPanel() {
		return this.panel;
	}

}
