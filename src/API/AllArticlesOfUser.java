package API;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_18;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_19;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_20;

import java.util.ArrayList;
import java.util.Iterator;

import org.jdom.Element;

import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.actions.util.MWAction;
import net.sourceforge.jwbf.mediawiki.actions.util.SupportedBy;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

@SupportedBy({ MW1_18, MW1_19, MW1_20 })
public class AllArticlesOfUser extends MWAction {
	
	

	private Get msg = null;
	private MediaWikiBot bot;
	private Revision[] revision;
	
	
	public Revision[] getRevision() {
		return revision;
	}

	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AllArticlesOfUser(MediaWikiBot bot, String username)
	{
		super(bot.getVersion());
		this.setBot(bot);
		this.username = username;
		msg = new Get(MediaWiki.URL_API + "?action=query&titles=user:" +MediaWiki.encode(username) + "&prop=revisions&rvlimit=max&rvuser="+MediaWiki.encode(username) +"&rvprop=ids%7Ctimestamp%7Cuser%7Cuserid%7Cflags%7Csize&format=xml");
		
		try {
	        bot.performAction(this);
	      } catch (ProcessException e) {
	        System.out.println("Failed");
	      }
		
	}

	public String[] getArticlesNames()
	{
		ArrayList<String> rtn = new ArrayList<String>();
		
		for(Revision r : revision)
		{
			GetRevision gr = new GetRevision(bot, r.getRevid());
			rtn.add(gr.getRevision().getName());
		}
		
		return rtn.toArray(new String[rtn.size()]);
	}
	@Override
	public HttpAction getNextMessage() {
		return msg;
	}
	
	public Revision[] getRevisions()
	{
		
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

	public MediaWikiBot getBot() {
		return bot;
	}

	public void setBot(MediaWikiBot bot) {
		this.bot = bot;
	}


}
