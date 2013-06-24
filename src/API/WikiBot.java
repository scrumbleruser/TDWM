package API;

import java.util.HashSet;
import java.util.Set;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

/**
 * Wrapper Klasse der einige Funktionen zur einfacheren Verwändung vereint. 
 * @author Bernhard Hermes
 * 
 */
public class WikiBot {
	
	MediaWikiBot wikiBot = new MediaWikiBot("http://de.wikipedia.org/w/");
	public MediaWikiBot getWikiBot() {
		return wikiBot;
	}
	public void setWikiBot(MediaWikiBot wikiBot) {
		this.wikiBot = wikiBot;
	}

	Article article;
	
public Revision[] getAllRevisions()
{
	AllRevisions ar = new AllRevisions(wikiBot, article.getTitle());
	
	return ar.getRevisions();
}

public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
public WikiBot(String login, String password)
{
	wikiBot.login("wissensmanagement", "asdasd");
}
public void setWiki(String wikiURL)
{
	wikiBot = new MediaWikiBot(wikiURL);
}
public void setArticle(String article)
{
	this.article = wikiBot.getArticle(article);
}
public String getCurrentRevisionID()
{
	String rtn = "";
	if(article != null)
	rtn = article.getRevisionId();
	return rtn;
}

public String[] getCategories()
{
	if(article == null)
		return null;

	String text =article.getText();
	

//	Pattern p = Pattern.compile("[[Kategorie:");
//	Matcher m = p.matcher(text);
//	while(m.find())
//	{
//		rtn.add(text.substring(m.start(), m.end()));
//	}
	return getAllItemsOnce(text, "[[Kategorie:", "]");
}

private String[] getAllItemsOnce(String text, String anfangstring,
		String endestring) {
	
	Set<String> rtn = new HashSet<String>();
	int anfang = text.indexOf(anfangstring);
	int ende = 0;
	while(anfang != -1)
	{
		ende = text.indexOf(endestring, anfang + anfangstring.length());
		if (ende == -1)
			break;
		rtn.add(text.substring(anfang+anfangstring.length(), ende));
		anfang = text.indexOf(anfangstring,ende+endestring.length());
		
	}
	return rtn.toArray(new String[rtn.size()]);
}
public String[] getLinks() {
	Links l = new Links(wikiBot, article.getTitle());
	
	return l.getLinks();
	
}

}
