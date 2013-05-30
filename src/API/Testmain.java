package API;

import java.util.ArrayList;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

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
//	   String[] tmo = wb.getCategories();
//	   
//	   for(String s : tmo)
//		   System.out.println(s);
	   
	   
//	   Categories c = new Categories(wb.getWikiBot(),"Klothoide");
//	   
//	   String[] tmo = c.getCategories();
//	   
//	   for(String s : tmo)
//		   System.out.println(s);
	   
	   AllRevisions ar = new AllRevisions(wb.getWikiBot(),"Klothoide");
	   
	   for(Revision r : ar.getRevisions())
	   System.out.println(r.toString());
      
   }
}
