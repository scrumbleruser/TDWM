/**
 * 
 */
package SQL;

public class mainSQL {

	public static void main(String[] args) {
		System.out.println("Hier kommt das SQL Statment");
		// MySQL-Login Zugangsdaten festlegen
		String dbuser = "root";
		String dbpass = "usbw";
		String dbhost = "localhost:3307/";
		String dbname = "wikiinfos";
//		String[] spalten = {"ID","IP","Benutzername"};
		mysql_connect con = new mysql_connect(dbhost,dbname,dbuser,dbpass);
		con.show_data("Select * from wikiinfos");
		con.mysql_close();

	}

}
