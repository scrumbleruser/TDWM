package API;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_15;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_16;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_17;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_18;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_19;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_20;

import java.util.ArrayList;
import java.util.Iterator;

import org.jdom.Element;

import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.actions.util.MWAction;
import net.sourceforge.jwbf.mediawiki.actions.util.SupportedBy;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

@SupportedBy({ MW1_15, MW1_16, MW1_17, MW1_18, MW1_19, MW1_20 })
public class Categories extends MWAction {
	
	

	private Get msg = null;
	private MediaWikiBot bot;
	private String[] categories;
	private String title;
	
	public Categories(MediaWikiBot bot, String title)
	{
		super(bot.getVersion());
		this.bot = bot;
		this.title = title;
		msg = new Get(MediaWiki.URL_API + "?action=query&titles=" +MediaWiki.encode(title) + "&prop=categories&format=xml");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public HttpAction getNextMessage() {
		return msg;
	}
	
	public String[] getCategories()
	{
		try {
	        bot.performAction(this);
	      } catch (ProcessException e) {
	        System.out.println("Failed");
	      }
		
		
		return categories;
		
	}
	
	  
	@Override
	  public String processAllReturningText(String s) {
		Element root = getRootElementWithError(s);
		ArrayList<String> rtn = new ArrayList<String>();
		findContent(root,rtn);
	    categories = rtn.toArray(new String[rtn.size()]);
	    return "";
	  }
	
	@SuppressWarnings("unchecked")
	private void findContent(Element root, ArrayList<String> rtn) {
		Iterator<Element> el = root.getChildren().iterator();
	    while (el.hasNext()) {
	      Element element = el.next();
	      if (element.getQualifiedName().equalsIgnoreCase("cl")) {
	        rtn.add(element.getAttributeValue("title"));

	      } else {
	        findContent(element,rtn);
	      }
	    }
		
	}


}
