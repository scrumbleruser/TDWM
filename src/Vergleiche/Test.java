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
		
		ArrayList<Revision> revisionsArticle_1 = new ArrayList<Revision>();
		ArrayList<Revision> revisionsArticle_2 = new ArrayList<Revision>();
		
		revisionsArticle_1.add(new Revision("R1", new User("Shimal"), new File("res/berlin1")));
		revisionsArticle_1.add(new Revision("R2", new User("Ibrahim"), new File("res/berlin2")));
		revisionsArticle_1.add(new Revision("R3", new User("Daniel"), new File("res/berlin3")));
		revisionsArticle_1.add(new Revision("R4", new User("Bernd"), new File("res/berlin4")));

		revisionsArticle_2.add(new Revision("R5", new User("Shimal"), new File("res/berlin5")));
		revisionsArticle_2.add(new Revision("R6", new User("Ibrahim"), new File("res/berlin6")));
		revisionsArticle_2.add(new Revision("R7", new User("Daniel"), new File("res/berlin7")));
		revisionsArticle_2.add(new Revision("R8", new User("Hermes"), new File("res/berlin8")));
		revisionsArticle_2.add(new Revision("R9", new User("Bernd"), new File("res/berlin9")));
		
		Article article_1 = new Article("Berlin 1", new Category("Kat 1"), revisionsArticle_1);
		Article article_2 = new Article("Berlin 2", new Category("Kat 2"), revisionsArticle_2);
		
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.add(article_1);
		articles.add(article_2);
		
		
		for(int i=0; i<articles.size(); i++) {
			AnalyzeRevisionsOf change = new AnalyzeRevisionsOf(articles.get(i));
			change.execute();
			
			for(int j = 0; j<change.getAllTypeOfChangesOf("Ibrahim").size(); j++) {
				System.out.println(change.getAllTypeOfChangesOf("Ibrahim").get(j));
			}
		}
		
		for(int i=0; i<articles.size(); i++) {
			
		}

		
		// AnalyzeArticle
		AnalyzeArticle aa = new AnalyzeArticle(articles);
		

		System.out.println("Bernd getUserType " + aa.getUserType("Bernd"));
		System.out.println("Hermes getUserTypOfChangesForOneArticle " + aa.getUserTypOfChangesForOneArticle(articles.get(1), "Hermes"));
		System.out.println("Hermes getTypOfChanges " + aa.getTypOfChanges("Hermes"));
		System.out.println("Daniel getArticlesOfOneUser " + aa.getArticlesOfOneUser("Daniel").get(1).getTitle());
		System.out.println("Daniel getArticlesOfOneUser " + aa.getArticlesOfOneUser("Daniel").get(0).getTitle());
		System.out.println("Daniel getArticlesOfOneUser " + aa.getArticlesOfOneUser("Daniel").get(1).getTitle());
		
		
		

	}
	
	

}
