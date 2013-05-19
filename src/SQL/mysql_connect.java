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

public class mysql_connect {

	// Objekte zur Verbindung erstellen
	static Connection connect = null;
	static Statement stmt = null;
	static ResultSet result = null;

	public mysql_connect(String dbhost, String dbname,String dbuser,String dbpass) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(
					"jdbc:mysql://"+dbhost+dbname, dbuser, dbpass);
			connect.setReadOnly(true);

		} catch (SQLException e) {
			System.out.println(e);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mysql_close() {
		try {
			result.close();
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			System.out.println("Fehler: ");
			e.printStackTrace();
		}
	}

	public void show_data(String sql) {
		try {
			stmt = connect.createStatement();
			result = stmt.executeQuery(sql);

			while (result.next()) {
				// System.out.println(result.getString(spalten[i]));
				System.out.println(result.getInt(1) + " Datensatz");
				System.out.println(result.getString(2));
				System.out.println(result.getString(3));
				System.out.println(result.getString(4));
				System.out.println(result.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

	}

}
