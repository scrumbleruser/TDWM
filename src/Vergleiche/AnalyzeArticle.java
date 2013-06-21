package Vergleiche;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AnalyzeArticle {


	
	ArrayList<Article> articles;
//	AnalyzeRevisionsOf ar;
	
	public AnalyzeArticle(ArrayList<Article> articles) throws IOException {
		this.articles = articles;
		
//		for(int i=0; i<articles.size(); i++) {
//			ar = new AnalyzeRevisionsOf(articles.get(i));
//			ar.execute();
//		}
	}
	
	/**
	 * Ermittelt den ALLGEMEINEN UserType eines Autors
	 * @param user
	 * @return User type
	 * @throws IOException
	 */
	public UserType getUserType(String user) throws IOException {
		int correcting = 0;
		int improving = 0;
		int newKnowledge = 0;
		
		for(int i=0; i<articles.size(); i++) {
			AnalyzeRevisionsOf change = new AnalyzeRevisionsOf(articles.get(i));
			change.execute();
			if(change.getTypeOf(user).equals(UserType.KORREKTUR))
				correcting++;
			if(change.getTypeOf(user).equals(UserType.VERBESSERUNG))
				improving++;
			if(change.getTypeOf(user).equals(UserType.NEUES_WISSEN))
				newKnowledge++;
		}
		if(correcting > improving && correcting > newKnowledge) {
			return UserType.KORREKTUR;
		} else if (improving > correcting && improving > newKnowledge) {
			return UserType.VERBESSERUNG;
		} else if(newKnowledge > correcting && newKnowledge > improving) {
			return UserType.NEUES_WISSEN;
		} else {
			return UserType.KEINE_KLASSIFIZIERUNG;
		}
	}
	
	/**
	 * Welche Arten von Änderungen ein User im ALLGEMEINEN durchführt, wird mit diese Methode beantwortet.
	 * @param user
	 * @return generalTypesOfChanges
	 * @throws IOException
	 */
	public ArrayList<TypeOfChange> getTypOfChanges(String user) throws IOException {
		ArrayList<TypeOfChange> generalTypesOfChanges = new ArrayList<TypeOfChange>();
		ArrayList<TypeOfChange> temp1 = new ArrayList<TypeOfChange>();
		for(int i=0; i<articles.size(); i++) {
			AnalyzeRevisionsOf change = new AnalyzeRevisionsOf(articles.get(i));
			temp1 = change.getAllTypeOfChangesOf(user);
			for(int j=0; j<temp1.size(); j++) {
				generalTypesOfChanges.add(temp1.get(j));
			}
		}
		return generalTypesOfChanges;
	}
	
	/**
	 * Welche Arten von Änderungen macht ein User in EINEM Artikel?
	 * @throws IOException 
	 */
	public ArrayList<TypeOfChange> getUserTypOfChangesForOneArticle(Article article, String user) throws IOException {
		int articlePosition = 0;
		ArrayList<TypeOfChange> changes = new ArrayList<TypeOfChange>();
		for(int i=0; i<this.articles.size(); i++) {
			if(this.articles.get(i).equals(article))
				articlePosition = i;
		}
		Article temp = this.articles.get(articlePosition);
		for(int i=0; i<temp.getRevisions().size(); i++) {
			if(temp.getRevisions().get(i).getAuthor().getName().equals(user)) {
				changes.add(temp.getRevisions().get(i).getTypeOfChange());
			}
		}
		return changes;	
	}
	
	/**
	 * Welche Artikeln ändert EINE Person?
	 */
	public ArrayList<Article> getArticlesOfOneUser(String username) {
		ArrayList<Article> articlesOfUser = new ArrayList<Article>();
		for(int i=0; i<this.articles.size(); i++) {
			for(int j=0; j<this.articles.get(i).getRevisions().size(); j++)
				if(this.articles.get(i).getRevisions().get(j).getAuthor().getName().equals(username))
					articlesOfUser.add(this.articles.get(i));
		}
		return articlesOfUser;
	}
	
	/**
	 * Welche User sind sich ähnlich?
	 * Hier Vergleich über gleiche UserType
	 * @return
	 */
	public ArrayList<User> getSimilarUsersByUserType() {
		ArrayList<User> similarUsers = new ArrayList<User>();
		for(int i=0; i<this.articles.size(); i++) {
			AnalyzeRevisionsOf rev = new AnalyzeRevisionsOf(this.articles.get(i));
			ArrayList<User> allUserOfArtikel = rev.getAllAuthorsOfAllRevisions();
			// UserType
			for(int j=0; j<allUserOfArtikel.size(); i++) {
				if(this.articles.get(i).getRevisions().get(j).getAuthor().getUserType().equals(allUserOfArtikel.get(j).getUserType())
						&& (!this.articles.get(i).getRevisions().get(j).getAuthor().equals(allUserOfArtikel.get(j)))) {
					similarUsers.add(allUserOfArtikel.get(j));
					similarUsers.add(this.articles.get(i).getRevisions().get(j).getAuthor());
				}
			}
		}
		return similarUsers;
	}
	
	// Welche User machen etwas in welche Kategorie?
	/**
	 * Welche User sind sich ähnlich?
	 * Hier Vergleich über gleiche Kategorie
	 * @return similarUsers
	 */
	public ArrayList<User> getSimilarUsersByCategory() {
		ArrayList<User> similarUsers = new ArrayList<User>();
		ArrayList<Article> similarArticleByCategory = new ArrayList<Article>();
		for(int i=1; i<this.articles.size(); i++) {
			AnalyzeRevisionsOf rev = new AnalyzeRevisionsOf(this.articles.get(i));
			ArrayList<User> allUserOfArtikel = rev.getAllAuthorsOfAllRevisions();
			for(int j=0; j<this.articles.get(i).getRevisions().size(); i++) {
				for(int k=0; k<allUserOfArtikel.size(); k++) {
					if(this.articles.get(i-1).getCategory().equals(this.articles.get(i).getCategory())) {
						similarArticleByCategory.add(this.articles.get(i-1));
						similarArticleByCategory.add(this.articles.get(i));
					}
				}
			}
			
			for(int j=0; j<allUserOfArtikel.size(); j++) {
				for(int k=0; k<similarArticleByCategory.size(); k++) {
					if(allUserOfArtikel.get(k).equals(similarArticleByCategory.get(j).getRevisions().get(k).getAuthor())) {
						similarUsers.add(allUserOfArtikel.get(k));
						similarUsers.add(similarArticleByCategory.get(j).getRevisions().get(k).getAuthor());
					}
				}
			}
		}
		return similarUsers;
	}
	
	/**
	 * Welche User sind sich ähnlich?
	 * Hier Vergleich über gleiche Typ der Änderung
	 * @return similarUsers
	 */
//	public ArrayList<User> getSimilarUsersByTypeOfChange() {
//		ArrayList<User> similarUsers = new ArrayList<User>();
//		ArrayList<Article> similarArticleTypeOfChange = new ArrayList<Article>();
//		for(int i=0; i<this.articles.size(); i++) {
//			AnalyzeRevisionsOf rev = new AnalyzeRevisionsOf(this.articles.get(i));
//			ArrayList<User> authors = rev.getAllAuthorsOfAllRevisions();
//			for(int j=0; j<this.articles.get(i).getRevisions().size(); j++) {
//				for(int k=0; k<rev.getAllTypeOfChangesOf(this.articles.get(i).getRevisions().get(j).getAuthor().getName()).size(); k++)
//					for(int l=0; l<similarArticleTypeOfChange.size(); l++) {
//						if(rev.getAllTypeOfChangesOf(this.articles.get(i).getRevisions().get(j).getAuthor().getName()).get(k).
//								equals(similarArticleTypeOfChange.get(l))) {
//							similarUsers.add(this.articles.get(i).getRevisions().get(j).getAuthor());
//							similarUsers.add(similarArticleTypeOfChange.get(l).getRevisions());
//						}
//					
//				}
////				for(int k=0; k<rev.)
//			}
//		}
//		
//		
//		return similarUsers;
//	}
	

}
