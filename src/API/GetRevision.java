package API;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_18;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_19;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_20;

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
public class GetRevision extends MWAction {

	private Get msg = null;
	private MediaWikiBot bot;
	private Revision revision = new Revision();;

	public Revision getRevision() {
		return revision;
	}

	private int id;

	public GetRevision(MediaWikiBot bot, int id) {
		super(bot.getVersion());
		this.setBot(bot);
		this.id = id;
		msg = new Get(
				MediaWiki.URL_API
						+ "?action=query&revids="
						+ id
						+ "&prop=revisions&rvprop=ids%7Ctimestamp%7Cuser%7Cuserid%7Cflags%7Csize&format=xml");
		try {
			bot.performAction(this);
		} catch (ProcessException e) {
			System.out.println("Failed");
		}

	}

	public int getTitle() {
		return id;
	}

	public void setTitle(int id) {
		this.id = id;
	}

	@Override
	public HttpAction getNextMessage() {
		return msg;
	}

	@Override
	public String processAllReturningText(String s) {
		Element root = getRootElementWithError(s);
		findContent(root);
		return "";
	}

	@SuppressWarnings("unchecked")
	private void findContent(Element root) {
		Iterator<Element> el = root.getChildren().iterator();
		while (el.hasNext()) {
			Element element = el.next();
			if (element.getQualifiedName().equalsIgnoreCase("page")) {
				revision.setName(element.getAttributeValue("title"));
			}
			if (element.getQualifiedName().equalsIgnoreCase("rev")) {
				revision.setRevID(element.getAttributeValue("revid"));
				revision.setUser(element.getAttributeValue("user"));
				revision.setUserID(element.getAttributeValue("userid"));
				revision.setTimeStamp(element.getAttributeValue("timestamp"));
				revision.setMinorchange(element.getAttributeValue("minor"));
				revision.setSize(element.getAttributeValue("size"));
				revision.setContent(element.getAttributeValue("content"));
			} else {
				findContent(element);
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
