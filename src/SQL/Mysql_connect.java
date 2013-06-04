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
	static Connection connect = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private String error_messages = "";
	private String other_message = "";
	private String error = "SQL-Statement falsch\n" +
					"oder Button Other ist nicht angeklickt worden ";
	
	SQLPanel sqpPanel = new SQLPanel();
	
	// Zur Mysql-Db eine Verbindung aufbauen
	public Mysql_connect(String dbhost, String dbname,String dbuser,String dbpass) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance(); // Instanz der Treiberklasse laden
			Enumeration allDrivers = DriverManager.getDrivers();
			allDrivers.hasMoreElements();
			other_message  += "Treiber kann geladen werden und lautet: \n" + allDrivers.nextElement() + "\n";
			connect = DriverManager.getConnection(
					"jdbc:mysql://"+dbhost+dbname, dbuser, dbpass);
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
//				messages  += "SQL-Statement: erfolgreich";
//				Update wikiinfos set IP="192.168.8.83" WHERE ID="6"
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
	
	public Statement setInsertInto(String valRevID, String valUserID, String valUser, String valDatum, String valGroesse, String valkAenderung){
		try {
			stmt = connect.createStatement();
			String sql = "Insert into wikiinfos " +
						"(Artikel, RevisionID, UserID,User,DatumUhrzeit,Groesse,Rechte)" +
						"VALUES(" + valRevID + "," + valUserID +
						"," + valUser + "," + valDatum + "," + valGroesse +
						"," + valkAenderung + ")";
			stmt.executeUpdate(sql);
			
		} catch (SQLException sqle) {
			error_messages  += "getOtherStatement: MySQL-Db nicht erreichbar \n" +
					"oder SQL-Statement falsch";
		}
		return stmt;
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
		} // Ausführen des angegeben SQL-Statements
//		Update wikiinfos set IP="192.168.8.72" WHERE ID="13"
		return stmt;
	}
}