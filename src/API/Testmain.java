package API;

import java.util.ArrayList;
import java.util.HashSet;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

/**
 * Tests usw.
 * @author Bernhard Hermes
 * 
 */
public class Testmain {
	
   public static void main(String[] args) throws Exception {
/*      MediaWikiBot b = new MediaWikiBot("http://de.wikipedia.org/w/");
      b.login("wissensmanagement", "asdasd");
      Article article = b.getArticle("Schwere_Körperverletzung_(Deutschland)");
      ArrayList<String> info = new ArrayList<String>();
      info.add(article.getEditor());
      info.add(article.getEditSummary());
      info.add(article.getRevisionId());
      info.add(article.getTitle());
      info.add(article.getText());
      article.getSimpleArticle();
      info.add(article.getEditTimestamp().toString());
      
      for(String a : info)
      {
    	  System.out.println(a);
      }*/
	   WikiBot wb = new WikiBot("wissensmanagement", "asdasd");
//	   wb.setArticle("Klothoide");
//	   
//	   String[] tmo = wb.getCategories();
//	   
//	   for(String s : wb.getBacklinks())
//		   System.out.println(s);
//	   
//	   for(String s : tmo)
//		   System.out.println(s);
	   
//	   Links bl = new Links(wb.getWikiBot(), "Klothoide");
//	   System.out.println(bl.toString());
	   
//	   Categories c = new Categories(wb.getWikiBot(),"Klothoide");
//	   
//	   String[] tmo = c.getCategories();
//	   
//	   for(String s : tmo)
//		   System.out.println(s);
	   
	   AllRevisions ar = new AllRevisions(wb.getWikiBot(),"Christian_Lindner");
	   
	   GetUserContribs uc = new GetUserContribs(wb.getWikiBot(), "AHZ");
	   
	   HashSet<String> hs = new HashSet();
	   
	   for(UserContribs u : uc.getUserContribs())
	   {
		   hs.add(u.getTitle());
	   }
	   
	   
	   for(String s : hs)
		   System.out.println(s);
	   
//	   for(Revision r : ar.getRevisions())
//	   System.out.println(r.toString());
//	   
//	   UserInfo ui = new UserInfo(wb.getWikiBot(), "Reinhard_Dietrich");
//	   System.out.println(ui.toString());
//	   for(String r : ui.getArticels())
//		   System.out.println(r);
//	   
//	   AllArticlesOfUser aaou = new AllArticlesOfUser(wb.getWikiBot(), "Qafgbxvghnx");
//	   
//	   
//	   for(String r : ui.getRights())
//		   System.out.println(r);
//	   for(String r : aaou.getArticlesNames())
//	   System.out.println(r);
//	   GetRevision grr = new GetRevision(wb.getWikiBot(), 1986);
//	   
//	   System.out.println(grr.getRevision().toString());
//	   GetUserContribs uc = new GetUserContribs(wb.getWikiBot(), "Catrope");
//	   
//	  for (UserContribs u : uc.getUserContribs()) {
//		System.out.println(u.toString());
//	}

	   //UserContribs uc = new UserContribs()wb.getWikiBot()
      
	  
   }
}
