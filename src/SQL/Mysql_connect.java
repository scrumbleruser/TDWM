package SQL;

import java.sql.*;
import java.util.*;

public class Mysql_connect {

	// Objekte zur Verbindung erstellen
	private static Connection connect = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	
	// Messages
	private String mysql_message = "";
	private String error_messages = "";
	private String other_message = "";
	
	// Verbindung mit MySQL-DB aufnehmen / Treiber laden
	public Mysql_connect(String dbhost, String dbname, String dbuser,
			String dbpass) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // Instanz der Treiberklasse laden
			Enumeration<Driver> allDrivers = DriverManager.getDrivers();
			allDrivers.hasMoreElements();
			mysql_message  += "Treiber kann geladen werden und lautet: \n" + allDrivers.nextElement() + "\n";
			connect = DriverManager.getConnection(
			"jdbc:mysql://" +dbhost+dbname,dbuser, dbpass);
		} catch (InstantiationException ine) {
			mysql_message  += "mysql_connect: Instanz nicht ausführbar \n";
		} catch (IllegalAccessException iae) {
			mysql_message  += "mysql_connect: Zugriff zur DB nicht möglich \n";
		} catch (ClassNotFoundException cnfe) {
			mysql_message  += "mysql_connect: Treiber nicht gefunden \n";
		}catch (Exception e){
			mysql_message += "mysql_connect: MySQL-Db nicht erreichbar \n";
		}
	}
	
	// Status der MySQL-Verbindung übergeben
	public String getStateMysql(){
		boolean connected;
		String state = "";
		try {
			connected = connect.isClosed();
			if (connected == true){
				state = "off";
			}else{
				state = "on";
			}
		} catch (SQLException e) {
			error_messages  += "getStateMysql: " + e.getMessage() + "\n";
			error_messages  += "getStateMysql: " + e.getErrorCode() + "\n";
		}
		return state;
	}
	
	// Bei Bedarf die DB-Verbindung schließen
	public void mysql_close() {
		try {
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			error_messages  += "mysql_close: " + e.getMessage() + "\n";
			error_messages  += "mysql_close: " + e.getErrorCode() + "\n";
		} catch (NullPointerException npe){
			error_messages += "mysql_close: Null Pointer. Bitte DB-Verbindung prüfen\n";
		}
	}
	
	// Ergebnis des SQL-Befehl übergeben +  / Tabellenspalten auslesen und abspeichern / 
	// Anzahl der Datensätze zum SQL-Befehl abspeichern 
	public String getRS(ResultSet result){
		ArrayList<String> list = new ArrayList<String>();
		int i;
		String rows ="";
		String content = "";
		String column ="";
		try {
			result.last();
			if(result != null){
				rows += "" + result.getRow() + " Datensätze vorhanden";
			}
			list.add(rows);
			list.add("\n");
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
//				content += ((list.size()/7)) + " DatensÃ¤tze vorhanden \n";
				for(i=0; i<list.size();i++){
					content += list.get(i).toString();
				}
			}else{
				error_messages  += "getRS: Keine Datensätze in der Tabelle vorhanden \n";
			}
		} catch (SQLException sqle) {
			error_messages  += "getRS: " + sqle.getMessage() + "\n";
			error_messages  += "getRS: " + sqle.getErrorCode() + "\n";
		}
		return content;
	}
	
	// Select Befehl an die DB stellen und Ergebnis weitergeben
	public String getSelectStatement(String sql){
		String myContent = "";
		try {
			stmt = connect.createStatement();
			result = stmt.executeQuery(sql);
			if(result != null){
//    			System.out.println(result);
		    	myContent = getRS(result);
		    	other_message += "getSelectStatement: SQL-Statement: erfolgreich\n";
    		}
		} catch (SQLException sqle) {
			error_messages  += "getSelectStatement: " + sqle.getMessage() + "\n";
			error_messages  += "getSelectStatement: " + sqle.getErrorCode() + "\n";
		}
		return myContent;
	}
	
	// Liefert alle Benutzernamen zurück
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
//			error_messages  += error;
			error_messages  += "getUserName: " + sqle.getMessage() + "\n";
			error_messages  += "getUserName: " + sqle.getErrorCode() + "\n";
		}
		return userlist;
	}
	
	// Gibt alle Tabellenspalten einer DB-Tabelle zurück
	public ArrayList<String> getColumnName(String tablename){
		ArrayList<String> list = new ArrayList<String>();
		int i;
		String columnName = "";
		String count ="";
		try {
			stmt = connect.createStatement();
			result = stmt.executeQuery("Select * from " + tablename);
			if(result != null){ // Tabelle enthält Datensätze
				int getcolumnncount = result.getMetaData().getColumnCount();
				count = Integer.toString(getcolumnncount);
				list.add(0, count);
				for(i=2;i<getcolumnncount;i++){
					columnName += result.getMetaData().getColumnName(i);
					columnName += ",";
				}
					columnName += result.getMetaData().getColumnName(getcolumnncount);
					list.add(1,columnName);
			}else{
				error_messages  += "getColumnName: Keine Datensätze in der Tabelle vorhanden \n";
			}
		} catch (SQLException sqle) {
			error_messages  += "getColumnName: " + sqle.getMessage() + "\n";
			error_messages  += "getColumnName: " + sqle.getErrorCode() + "\n";
		}
		return list;
	}
	
	// Neue Datensätzen in einer DB-Tabelle einfügen
	public Statement setInsertInto(String myvalues, String tabname)
	{
		ArrayList<String> list = new ArrayList<String>();
		list = getColumnName(tabname);
		String rows = list.get(1).toString();
//		System.out.println(myvalues);
		String sql = "Insert into "+tabname +
				" (" +rows+ ")" + "VALUES("+myvalues+")";
		System.out.println(sql);
//		SELECT Artikel, Count(*) FROM `revision` group by Artikel
//		Delete from revision WHERE Artikel="Deutschland"
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
			
		} catch (SQLException sqle) {
			error_messages  += "setInsertInto: " + sqle.getMessage() + "\n";
			error_messages  += "setInsertInto: " + sqle.getErrorCode() + "\n";
		}
		return stmt;
	}
	
	// andere SQL-Befehle an die DB stellen
	public Statement getOtherStatement(String sql){		
		// Weitere DDL-Befehle - Update,Create,Delete
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
			other_message += "getOtherStatement: SQL-Statement: erfolgreich\n";
		} catch (SQLException sqle) {
			error_messages  += "getOtherStatement: " + sqle.getMessage() + "\n";
			error_messages  += "getOtherStatement: " + sqle.getErrorCode() + "\n";
		} 
		return stmt;
	}
	
	// ab hier nur noch getter/setter
	public String getErrorMessages(){
		return this.error_messages;
	}
	
	public String getOtherMessage(){
		return this.other_message;
	}
	
	public String getmysqlMesage(){
		return this.mysql_message;
	}
	
	public void setmysql_messages(String mysql_message) {
		this.mysql_message = mysql_message;
	}
	
	public void setError_messages(String error_messages) {
		this.error_messages = error_messages;
	}
	
	public void setOther_message(String other_message) {
		this.other_message = other_message;
	}
	
	public void deleteMessages(){
		this.other_message = "";
		this.error_messages = "";
	}
}