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

/**
 * 
 * @author Bernhard Hermes
 * Sucht alle Beiträge die ein Nutzer 
 */
@SupportedBy({ MW1_18, MW1_19, MW1_20 })
public class GetUserContribs extends MWAction {

	private Get msg = null;
	private MediaWikiBot bot;
	private UserContribs[] usercontribs;

	public UserContribs[] getUserContribs() {
		return usercontribs;
	}

	private String user;

	public GetUserContribs(MediaWikiBot bot, String user) {
		super(bot.getVersion());
		this.setBot(bot);
		this.user = user;
		msg = new Get(
				MediaWiki.URL_API
						+ "?action=query&list=usercontribs&ucuser="+user+"&uclimit=max&ucdir=newer&ucprop=ids%7Ctitle%7Ctimestamp%7Ccomment%7Csize%7Csizediff%7Cflags&format=xml");
		try {
			bot.performAction(this);
		} catch (ProcessException e) {
			System.out.println("Failed");
		}

	}

	public String getTitle() {
		return user;
	}

	public void setTitle(String user) {
		this.user = user;
	}

	@Override
	public HttpAction getNextMessage() {
		return msg;
	}

	@Override
	public String processAllReturningText(String s) {
		Element root = getRootElementWithError(s);
		ArrayList<UserContribs> rtn = new ArrayList<UserContribs>();
		findContent(root,rtn);
		usercontribs = rtn.toArray(new UserContribs[rtn.size()]);
		return "";
	}

	@SuppressWarnings("unchecked")
	private void findContent(Element root, ArrayList<UserContribs> rtn) {
		Iterator<Element> el = root.getChildren().iterator();
		while (el.hasNext()) {
			Element element = el.next();
			if (element.getQualifiedName().equalsIgnoreCase("item")) {

				UserContribs usercontrib = new UserContribs();
				usercontrib .setUserid(Integer.parseInt(element.getAttributeValue("userid")));
				usercontrib.setUser(element.getAttributeValue("user"));
				usercontrib.setPageid(Integer.parseInt(element.getAttributeValue("pageid")));
				usercontrib.setRevid(Integer.parseInt(element.getAttributeValue("revid")));
				usercontrib.setTitle(element.getAttributeValue("title"));
				usercontrib.setTimestamp(element.getAttributeValue("timestamp"));
				usercontrib.setMinorchange(element.getAttributeValue("minor"));
				usercontrib.setComment(element.getAttributeValue("comment"));
				usercontrib.setSize(Integer.parseInt(element.getAttributeValue("size")));
				usercontrib.setSizediff(Integer.parseInt(element.getAttributeValue("sizediff")));	

				rtn.add(usercontrib);
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
