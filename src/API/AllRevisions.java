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

/**
 * Sucht alle Revisionen eines Artikels
 * 
 * @author Bernhard Hermes
 * 
 */
@SupportedBy({ MW1_18, MW1_19, MW1_20 })
public class AllRevisions extends MWAction {
	
	

	private Get msg = null;
	private MediaWikiBot bot;
	private Revision[] revision;
	
	
	public Revision[] getRevision() {
		return revision;
	}

	private String title;
	
	public AllRevisions(MediaWikiBot bot, String title)
	{
		super(bot.getVersion());
		this.bot = bot;
		this.title = title;
		msg = new Get(MediaWiki.URL_API + "?action=query&titles=" +MediaWiki.encode(title) + "&prop=revisions&rvlimit=max&rvprop=ids%7Ctimestamp%7Cuser%7Cuserid%7Cflags%7Csize&format=xml");
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
	
	public Revision[] getRevisions()
	{
		try {
	        bot.performAction(this);
	      } catch (ProcessException e) {
	        System.out.println("Failed");
	      }
		
		
		return revision;
		
	}
	
	public Revision[] getRevisionsFromUser(String name)
	{
		if (revision == null)
			return null;
		ArrayList<Revision> rtn = new ArrayList<Revision>();
		
		for(Revision r : revision)
		{
			if(r.getUser().equalsIgnoreCase(name))
			{
				rtn.add(r);
			}
		}
		
		return rtn.toArray(new Revision[rtn.size()]);
	}
	
	public Revision[] getRevisionsFromUserID(int userid)
	{
		if (revision == null)
			return null;
		ArrayList<Revision> rtn = new ArrayList<Revision>();
		
		for(Revision r : revision)
		{
			if(r.getUserid()== userid)
			{
				rtn.add(r);
			}
		}
		
		return rtn.toArray(new Revision[rtn.size()]);
	}
	
	  
	@Override
	  public String processAllReturningText(String s) {
		Element root = getRootElementWithError(s);
		ArrayList<Revision> rtn = new ArrayList<Revision>();
		findContent(root,rtn);
		revision = rtn.toArray(new Revision[rtn.size()]);
	    return "";
	  }
	
	@SuppressWarnings("unchecked")
	private void findContent(Element root, ArrayList<Revision> rtn) {
		Iterator<Element> el = root.getChildren().iterator();
	    while (el.hasNext()) {
	      Element element = el.next();
	      if (element.getQualifiedName().equalsIgnoreCase("rev")) {
	    	Revision rev = new Revision();
	    	rev.setName(title);
	    	rev.setRevID(element.getAttributeValue("revid"));
	    	rev.setUser(element.getAttributeValue("user"));
	    	rev.setUserID(element.getAttributeValue("userid"));
	    	rev.setTimeStamp(element.getAttributeValue("timestamp"));
	    	rev.setMinorchange(element.getAttributeValue("minor"));
	    	rev.setSize(element.getAttributeValue("size"));
	        rtn.add(rev);

	      } else {
	        findContent(element,rtn);
	      }
	    }
		
	}


}
