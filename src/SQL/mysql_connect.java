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

public class mysql_connect {

	// Objekte zur Verbindung erstellen
	static Connection connect = null;
	static Statement stmt = null;
	
	public mysql_connect(String dbhost, String dbname,String dbuser,String dbpass) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance(); // Instanz der Treiberklasse laden
			
			Enumeration allDrivers = DriverManager.getDrivers();
			allDrivers.hasMoreElements();
//			System.out.println("Treiber kann geladen werden und lautet: " + allDrivers.nextElement());
			connect = DriverManager.getConnection(
					"jdbc:mysql://"+dbhost+dbname, dbuser, dbpass);
		} catch (SQLException e) {
			System.out.println("mysql_connect: MySQL-Db nicht erreichbar \n");
		} catch (InstantiationException e) {
			System.out.println("mysql_connect: Instanz nicht ausführbar \n");
		} catch (IllegalAccessException e) {
			System.out.println("mysql_connect: Zugriff zur DB nicht möglich \n");
		} catch (ClassNotFoundException e) {
			System.out.println("mysql_connect: Treiber nicht gefunden \n");
		}
	}

	public void mysql_close() {
		try {
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			System.out.println("mysql_close: Fehler: ");
//			e.printStackTrace();
		}
	}

	public Statement getStatement(String sql, int nr) {
		try {
			stmt = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
//		System.out.println("SQL ist: " + sql);
	}
}
