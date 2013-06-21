package Vergleiche;

import java.util.ArrayList;

public class Article {

	String title;
//	ArrayList<User> authors;
	Category category;
	ArrayList<Revision> revisions;
	ArrayList<TypeOfChange> typeOfChange;
	
	public Article(String title, Category category, ArrayList<Revision> revisions) {
		this.title = title;
//		this.authors = authors;
		this.category = category;
		this.revisions = revisions;
	}
	
	public String getTitle() {
		return this.title;
	}
	
//	public ArrayList<User> getAuthor() {
//		return this.authors;
//	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public ArrayList<TypeOfChange> getTypeOfChange() {
		return this.typeOfChange;
	}
	
	public Integer getNumberOfChanges() {
		return this.revisions.size();
	}
	
	public void setTypeOfChange(TypeOfChange typeOfChange) {
		this.typeOfChange.add(typeOfChange);
	}
	
	public ArrayList<Revision> getRevisions() {
		return this.revisions;
	}
	
	public void addRevision(Revision revision) {
		this.revisions.add(revision);
	}
 
}
