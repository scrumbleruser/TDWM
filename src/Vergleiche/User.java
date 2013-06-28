package Vergleiche;
/**
 * Defines a User. The constructor needs the follwing information: <br>
 * - The name of the Author of revision <br>
 * - The name of the article <br>
 * - The ID of the revision
 * @author Shimal
 */
public class User {

	String name;
	String article;
	String revision;
	String typeOfChange;

	public User(String name, String article, String revision) {
		this.name = name;
		this.article = article;
		this.revision = revision;
		this.typeOfChange = null;
	}

	public String getName() {
		return this.name;
	}

	public String getArticle() {
		return article;
	}
	
	// Getter & Setter
	public void setArticle(String article) {
		this.article = article;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}
	
	public String getTypeOfChange() {
		return this.typeOfChange;
	}
	
	public void setTypeOfChange(TypeOfChange type) {
		this.typeOfChange = type.toString();
	}
}
