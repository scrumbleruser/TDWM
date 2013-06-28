package Vergleiche;

import java.util.ArrayList;
/**
 * Defines an article. During initialization the following information are needed: <br>
 * - Title of the article <br>
 * - Category of the article <br>
 * - A list of revisions that belongs to the article
 * @author Shimal
 *
 */
public class Article {

	String title;
	ArrayList<User> authors;
	String category;
	ArrayList<Revision> revisions;
	ArrayList<TypeOfChange> typeOfChange;
	/**
	 * Defines an article. During initialization the following information are needed: <br>
	 * - Title of the article <br>
	 * - Category of the article <br>
	 * - A list of revisions that belongs to the article
	 * @param title
	 * @param category
	 * @param revisions
	 */
	public Article(String title, String category, ArrayList<Revision> revisions) {
		this.title = title;
		this.authors = new ArrayList<User>();
		this.category = category;
		this.revisions = revisions;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public ArrayList<User> getAuthor() {
		return this.authors;
	}
	
	public void setAuthor(ArrayList<User> authors) {
		this.authors = authors;
	}
	
	public String getCategory() {
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
