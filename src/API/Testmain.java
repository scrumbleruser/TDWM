package API;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class Testmain {
	
   public static void main(String[] args) throws Exception {
      MediaWikiBot b = new MediaWikiBot("http://de.wikipedia.org/w/");
      b.login("wissensmanagement", "asdasd");
      Article article = b.getArticle("Schwere_Körperverletzung_(Deutschland)");
      ArrayList<String> info = new ArrayList<String>();
      info.add(article.getEditor());
      info.add(article.getEditSummary());
      info.add(article.getRevisionId());
      info.add(article.getTitle());
      info.add(article.getText());
      info.add(article.getEditTimestamp().toString());
      
      for(String a : info)
      {
    	  System.out.println(a);
      }
      
   }
}
