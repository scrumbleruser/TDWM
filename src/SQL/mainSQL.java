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
		// Parameter: SQL-Statement, Wert 1 nur für Select
		con.getSQL("Select * from wiki2",1);
//		con.getSQL("DROP TABLE wiki;",2);
//		con.getSQL("CREATE TABLE IF NOT EXISTS wiki(ID mediumint(8) unsigned NOT NULL AUTO_INCREMENT," +
//				"DatumUhrzeit varchar(255),IP varchar(40),Benutzername varchar(255)," +
//				"Benutzergruppe varchar(50),Artikel varchar(255)," +
//				"Revision mediumint(10),Kategorie varchar(255)," +
//				"PRIMARY KEY (ID)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=5;",2);
		con.mysql_close();
	}
}
