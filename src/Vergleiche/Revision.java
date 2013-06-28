	package Vergleiche;

import java.io.File;
import java.util.ArrayList;
/**
 * Defines a revision. During initialization the following information are needed: <br>
 * - User (the author of the revision) <br>
 * - A file with content of the revision 
 * @author Shimal
 *
 */
public class Revision {

	private String id;
	private User author;
	private File file;
	private ArrayList<String> lines;
	private ArrayList<String[]> words;
	private ArrayList<String> uniqueWords;
	private ArrayList<String> chars;
	private int maxLines;
	private int maxWords;
	private TypeOfChange typeOfChange;
	
	public Revision(User author, File file) {
		this.author = author;
		this.file = file;
		this.lines = new ArrayList<String>();
		this.words = new ArrayList<String[]>();
		this.uniqueWords = new ArrayList<String>();
		this.chars = new ArrayList<String>();
		this.maxLines = 0;
		this.maxWords = 0;
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
	
	public ArrayList<String> getLines() {
		return this.lines;
	}
	
	public ArrayList<String[]> getWords() {
		return this.words;
	}
	
	public void setWords(ArrayList<String[]> words) {
		this.words = words;
	}
	
	public ArrayList<String> getUniqueWords() {
		return this.uniqueWords;
	}
	
	public ArrayList<String> getChars() {
		return this.chars;
	}
	
	public Integer getMaxLines() {
		return this.maxLines;
	}
	
	public Integer getMaxWords() {
		return this.maxWords;
	}
	
	public TypeOfChange getTypeOfChange() {
		return this.typeOfChange;
	}
	
	public void setTypeOfChange(TypeOfChange type) {
		this.typeOfChange = type;
	}
	
	public void addLine(String line) {
		this.lines.add(line);
	}
	
	public void addWord(String word) {
		this.lines.add(word);
	}
	
	public void addChar(String c) {
		this.lines.add(c);
	}
	
	public void setUniqueWords(ArrayList<String> list) {
		this.uniqueWords = list;
	}
	
	public void setMaxLines(int line) {
		this.maxLines = line;
	}
	
	public void setMaxWords(int word) {
		this.maxWords = word;
	}

}
