package Vergleiche;

import java.util.ArrayList;
/**
 * A temporary article class. It is used for importing data from the Databank.
 * The class is not needed if you do not want to change the process of importing
 * and inserting data from the Databak to files
 * @author Shimal
 */
public class TempArticle {
	
	String title;
	String category;
	ArrayList<TempRevision> revisions;

	public TempArticle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String cat) {
		this.category = cat;
	}
	
	public ArrayList<TempRevision> getRevisions() {
		return this.revisions;
	}
	
	public void setRevisions(ArrayList<TempRevision> revisions) {
		this.revisions = revisions;
	}
		
	

}
