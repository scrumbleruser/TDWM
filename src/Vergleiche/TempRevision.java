package Vergleiche;

import java.io.File;
import java.util.ArrayList;

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
