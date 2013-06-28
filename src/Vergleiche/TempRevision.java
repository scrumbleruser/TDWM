package Vergleiche;

/**
 * A temporary revision class. It is used for importing data from the Databank.
 * The class is not needed if you do not want to change the process of importing
 * and inserting data from the Databak to files
 * @author Shimal
 */
public class TempRevision {

	private String author;
	private String id;

	public TempRevision(String author, String id) {
		this.author = author;
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

}
