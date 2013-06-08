package Vergleiche;

import java.io.File;

public class Revision {

	String id;
	User author;
	File file;
	
	public Revision(String id, User author, File file) {
		this.id = id;
		this.author = author;
		this.file = file;
	}
	
	public String getID() {
		return this.id;
	}
	
	public User getAuthor() {
		return this.author;
	}
	
	public File getFile() {
		return this.file;
	}
	

}
