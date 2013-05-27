/**
 * Author: Daniel Fay
 * 
 * Java Klasse die folgende Funktionalitäten besitzt
 * - MySQL-Verbindung eröffnen
 * - diversen Daten auslesen
 * - Hinzufügen von neuen Daten in die DB-Tabelle
 * - Löschen von Datensäzen
 */

package SQL;

import java.sql.*;
import java.util.*;

public class mysql_connect {

	// Objekte zur Verbindung erstellen
	static Connection connect = null;
	static Statement stmt = null;
	static ResultSet result = null;

	public mysql_connect(String dbhost, String dbname,String dbuser,String dbpass) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance(); // Instanz der Treiberklasse laden
			
			Enumeration allDrivers = DriverManager.getDrivers();
			allDrivers.hasMoreElements();
			System.out.println("Treiber kann geladen werden und lautet: " + allDrivers.nextElement());
			connect = DriverManager.getConnection(
					"jdbc:mysql://"+dbhost+dbname, dbuser, dbpass);

		} catch (SQLException e) {
			System.out.println("mysql_connect: SQL-Statment falsch oder MySQL-Db nicht erreichbar \n");
		} catch (InstantiationException e) {
			System.out.println("mysql_connect: Instanz nicht ausführbar \n");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("mysql_connect: Zugriff zur DB nicht möglich \n");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("mysql_connect: Treiber nicht gefunden \n");
			e.printStackTrace();
		}
	}

	public void mysql_close() {
		try {
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			System.out.println("mysql_close: Fehler: ");
			e.printStackTrace();
		}
	}
	
	public void setResult(ResultSet result){
		try {
			System.out.println(result.getInt(1) + " Datensatz");
			System.out.println(result.getString(2));
			System.out.println(result.getString(3));
			System.out.println(result.getString(4));
			System.out.println(result.getString(5));
			System.out.println(result.getString(6));
			System.out.println(result.getString(7));
			System.out.println(result.getString(8));
		} catch (SQLException e) {
			System.out.println("setResult: SQL-Fehler");
			e.printStackTrace();
		}
		
	}

	public void getSQL(String sql, int nr) {
		try {
			stmt = connect.createStatement();
			System.out.println("SQL ist: " + sql);
			if(nr == 1){
				connect.setReadOnly(true); // nur Lesezugriff möglich
				// Select - Ausgabe
				result = stmt.executeQuery(sql);
				if (result.first() == true){  // nur wenn Datensätze vorhanden sind ausführen
					setResult(result);
					while (result.next()) {
						// System.out.println(result.getString(spalten[i]));
						setResult(result);
					}
						result.close();
				}else{
						System.out.println("Keine Datensätze in der Tabelle vorhanden");
				}
			}else{
					connect.setReadOnly(false);
					// Weitere DDL-Befehle - Upade,Create,Delete
					stmt.executeUpdate(sql); // Ausführen des angegeben SQL-Statements
					System.out.println("SQL-Statement erfolgreich ausgeführt \n");
				}
		} catch (SQLException e) {
			System.out.println("getSQL: SQL-Statment falsch oder MySQL-Db nicht erreichbar \n");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

	}

}
