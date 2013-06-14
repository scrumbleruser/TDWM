package Vergleiche;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ArrayList<Revision> revisions = new ArrayList<Revision>();
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User("Shimal"));											// Revision
		users.add(new User("Ibrahim"));											// Revision

		revisions.add(new Revision("R1", new User("Shimal"), new File("res/berlin1")));
		revisions.add(new Revision("R1", new User("Ibrahim"), new File("res/berlin2")));
		
		Article article = new Article("Berlin 1", users, new Category("Kat 1"), revisions);
		Changes change = new Changes(article);
		
		change.execute();
		
		
		

	}

}
