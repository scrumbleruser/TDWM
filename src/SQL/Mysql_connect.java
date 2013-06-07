/**
 * Author: Daniel Fay
 * 
 * Java Klasse die folgende Funktionalit�ten besitzt
 * - MySQL-Verbindung er�ffnen
 * - diversen Daten auslesen
 * - Hinzuf�gen von neuen Daten in die DB-Tabelle
 * - L�schen von Datens�zen
 */

package SQL;

import java.sql.*;
import java.util.*;

import View.SQLPanel;

public class Mysql_connect {

	// Objekte zur Verbindung erstellen
	private static Connection connect = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private String error_messages = "";
	private String other_message = "";
	private String error = "SQL-Statement falsch\n" +
					"oder Button Other ist nicht angeklickt worden ";
	
	SQLPanel pan = new SQLPanel();
	
	// Zur Mysql-Db eine Verbindung aufbauen
	public Mysql_connect() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance(); // Instanz der Treiberklasse laden
			Enumeration allDrivers = DriverManager.getDrivers();
			allDrivers.hasMoreElements();
			other_message  += "Treiber kann geladen werden und lautet: \n" + allDrivers.nextElement() + "\n";
			connect = DriverManager.getConnection(
			"jdbc:mysql://" +pan.getHostField().getText()+pan.getNameField().getText(),""
							+pan.getUserField().getText(), pan.getPasswordField().getText());
		} catch (InstantiationException ine) {
			error_messages  += "mysql_connect: Instanz nicht ausführbar \n";
		} catch (IllegalAccessException iae) {
			error_messages  += "mysql_connect: Zugriff zur DB nicht möglich \n";
		} catch (ClassNotFoundException cnfe) {
			error_messages  += "mysql_connect: Treiber nicht gefunden \n";
		}catch (Exception e){
			error_messages += "mysql_connect: MySQL-Db nicht erreichbar \n";
		}
	}
	
	public Mysql_connect(String dbhost, String dbname, String dbuser,
			String dbpass) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance(); // Instanz der Treiberklasse laden
			Enumeration allDrivers = DriverManager.getDrivers();
			allDrivers.hasMoreElements();
			other_message  += "Treiber kann geladen werden und lautet: \n" + allDrivers.nextElement() + "\n";
			connect = DriverManager.getConnection(
			"jdbc:mysql://" +dbhost+dbname,dbuser, dbpass);
		} catch (InstantiationException ine) {
			error_messages  += "mysql_connect: Instanz nicht ausführbar \n";
		} catch (IllegalAccessException iae) {
			error_messages  += "mysql_connect: Zugriff zur DB nicht möglich \n";
		} catch (ClassNotFoundException cnfe) {
			error_messages  += "mysql_connect: Treiber nicht gefunden \n";
		}catch (Exception e){
			error_messages += "mysql_connect: MySQL-Db nicht erreichbar \n";
		}
	}
	
	public void getStateMysql(){
		boolean connected;
		try {
			connected = connect.isClosed();
			if (connected == true){
				pan.getStausField().setText("off");
			}else{
				pan.getStausField().setText("on");
			}
		} catch (SQLException e) {
			System.out.println("getStateMysql: SQL-Fehler");
			e.printStackTrace();
		}
	}

	public String getErrorMessages(){
		return this.error_messages;
	}
	
	public String getOtherMessage(){
		return this.other_message;
	}

	public void mysql_close() {
		try {
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			error_messages  += "mysql_close: Verbindung kann nicht geschlossen werden ";
//			e.printStackTrace();
		} catch (NullPointerException npe){
			error_messages += "mysql_close: Null Pointer. Bitte DB-Verbindung prüfen";
		}
	}
	
	public String getRS(ResultSet result){
		ArrayList<String> list = new ArrayList<String>();
		int i;
		String content = "";
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
				for(int e=1;e<getcolumnncount+1;e++){
					list.add(result.getString(e)+ "  ");
				}
				list.add("\n");
				while (result.next()) {
					for(int e=1;e<getcolumnncount+1;e++){
						list.add(result.getString(e)+ "  ");
					}
					list.add("\n");
				}
//				content += ((list.size()/7)) + " Datensätze vorhanden \n";
				for(i=0; i<list.size();i++){
					content += list.get(i).toString();
				}
			}else{
				error_messages  += "getRS: Keine Datensätze in der Tabelle vorhanden ";
			}
		} catch (SQLException sqle) {
			error_messages  += "getRS: " + error;
		}
		return content;
	}
	
	public String getSelectStatement(String sql){
		String myContent = "";
		try {
			stmt = connect.createStatement();
			result = stmt.executeQuery(sql);
			if(result != null){
//    			System.out.println(result);
		    	myContent = getRS(result);
		    	other_message += "SQL-Statement: erfolgreich";
    		}
		} catch (SQLException sqle) {
			error_messages  += error;
		}catch (Exception e){
			error_messages += "getSelectionStatement: MySQL-Db nicht erreichbar \n" +
					"oder SQL-Statement falsch"; 
		;
		}
		return myContent;
	}
	
	public ArrayList<String> getColumnName(String tablename){
		ArrayList<String> list = new ArrayList<String>();
		int i;
		String columnName = "";
		String count ="";
		try {
			stmt = connect.createStatement();
			result = stmt.executeQuery("Select * from " + tablename);
			result.last();
			if(result != null){
				int getcolumnncount = result.getMetaData().getColumnCount();
				count = Integer.toString(getcolumnncount);
				list.add(0, count);
				// Dynamische Erstellung von Spaltennamen
				for(i=2;i<getcolumnncount;i++){
					columnName += result.getMetaData().getColumnName(i);
					columnName += ",";
				}
					columnName += result.getMetaData().getColumnName(getcolumnncount);
					list.add(1,columnName);
			}else{
				error_messages  += "getRS: Keine Datensätze in der Tabelle vorhanden ";
			}
		} catch (SQLException sqle) {
			error_messages  += "getRS: " + error;
		}
		return list;
		
	}
	
	public Statement setInsertInto(String myvalues, String tabname)
	{
		ArrayList<String> list = new ArrayList<String>();
		list = getColumnName(tabname);
		String rows = list.get(1).toString();

		String sql = "Insert into "+tabname +
				" (" +rows+ ")" + "VALUES("+myvalues+")";
//		System.out.println(sql);
//		SELECT Artikel, Count(*) FROM `revision` group by Artikel
//		Delete from revision WHERE Artikel="Deutschland"
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
			
		} catch (SQLException sqle) {
			error_messages  += "getOtherStatement: MySQL-Db nicht erreichbar \n" +
					"oder SQL-Statement falsch";
		}
		return stmt;
	}
	
	public ArrayList<String> getUserName(){
		ArrayList<String> userlist = new ArrayList<String>();
		try {
			String sql = "Select DISTINCT TRIM(User) from revision ORDER BY User";
			stmt = connect.createStatement();
			result = stmt.executeQuery(sql);
			System.out.println(result);
			if(result != null){
				while (result.next()) {
					userlist.add(result.getString(1));
				}
			}
		} catch (SQLException sqle) {
			error_messages  += error;
		}catch (Exception e){
			error_messages += "getUserName: MySQL-Db nicht erreichbar \n" +
					"oder SQL-Statement falsch"; 
		;
		}
		return userlist;
	}
	
	public Statement getOtherStatement(String sql){		
		// Weitere DDL-Befehle - Upade,Create,Delete
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
			other_message += "SQL-Statement: erfolgreich";
//			getResult(con);
		} catch (SQLException sqle) {
			error_messages  += "getOtherStatement: MySQL-Db nicht erreichbar \n" +
					"oder SQL-Statement falsch";
			error_messages  += "getOtherStatement: " + sqle.getMessage();
			error_messages  += "getOtherStatement: " + sqle.getSQLState();
			error_messages  += "getOtherStatement: " + sqle.getErrorCode();
		} 
		return stmt;
	}
}