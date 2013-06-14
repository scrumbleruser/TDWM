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
public class UserInfo extends MWAction {

	private Get msg = null;
	private MediaWikiBot bot;
	private String username;
	private String[] articles;

	public String[] getArticels() {
		return articles;
	}

	private String title;
	private int userid;
	private String userrealname;
	private int editcount;
	private String registration;
	private String gender;
	private String[] groups;
	private String[] rights;

	public int getEditcount() {
		return editcount;
	}

	public void setEditcount(int editcount) {
		this.editcount = editcount;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUserrealname() {
		return userrealname;
	}

	public void setUserrealname(String userrealname) {
		this.userrealname = userrealname;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public UserInfo(MediaWikiBot bot, String username) {
		super(bot.getVersion());
		this.username = username;
		this.bot = bot;
		this.title = username;
		msg = new Get(
				MediaWiki.URL_API
						+ "?action=query&prop=links&titles=user:"
						+ MediaWiki.encode(username)
						+ "&pllimit=max&format=xml&list=users&ususers="
						+ MediaWiki.encode(username)
						+ "&usprop=blockinfo%7Cgroups%7Ceditcount%7Cregistration%7Cemailable%7Cgender%7Crights");

		try {
			bot.performAction(this);
		} catch (ProcessException e) {
			System.out.println("Failed");
		}

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

	public String[] getArticless() {
		try {
			bot.performAction(this);
		} catch (ProcessException e) {
			System.out.println("Failed");
		}

		return articles;

	}

	@Override
	public String processAllReturningText(String s) {
		Element root = getRootElementWithError(s);
		ArrayList<String> rtnt = new ArrayList<String>();
		ArrayList<String> rtng = new ArrayList<String>();
		ArrayList<String> rtnr = new ArrayList<String>();
		findContent(root, rtng, rtnr, rtnt);
		articles = rtnt.toArray(new String[rtnt.size()]);
		groups = rtng.toArray(new String[rtng.size()]);
		rights = rtnr.toArray(new String[rtnr.size()]);
		return "";
	}

	public String[] getGroups() {
		return groups;
	}

	public String[] getRights() {
		return rights;
	}

	@SuppressWarnings("unchecked")
	private void findContent(Element root, ArrayList<String> rtng,
			ArrayList<String> rtnr, ArrayList<String> rtnt) {
		Iterator<Element> el = root.getChildren().iterator();
		while (el.hasNext()) {
			Element element = el.next();
			if (element.getQualifiedName().equalsIgnoreCase("pl")) {
				rtnt.add(element.getAttributeValue("title"));
			} else {
				if (element.getQualifiedName().equalsIgnoreCase("g"))
					rtng.add(element.getText());
				if (element.getQualifiedName().equalsIgnoreCase("r"))
					rtnr.add(element.getText());

				if (element.getQualifiedName().equalsIgnoreCase("user")) {
					this.userid = Integer.parseInt(element
							.getAttributeValue("userid"));
					this.userrealname = element.getAttributeValue("name");
					this.editcount = Integer.parseInt(element
							.getAttributeValue("editcount"));
					this.registration = element
							.getAttributeValue("registration");
					this.gender = element.getAttributeValue("gender");
					findContent(element, rtng, rtnr, rtnt);
				} else {
					findContent(element, rtng, rtnr, rtnt);

				}
			}

		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		String rtn = "";
		rtn = "Username: " + username + " User Realname: " + userrealname
				+ " UserID: " + userid + " Gender: " + gender
				+ " Registration: " + registration + " EditCount: " + editcount;
		StringBuffer tmp = new StringBuffer(" Groups:");
		for (String s : getGroups())
			tmp.append(" "+s + " ,");
		tmp = new StringBuffer(tmp.substring(0, tmp.length()-2));
		tmp.append(" Rights:");
		
		for(String s: getRights())
			tmp.append(" "+s + " ,");
		
		tmp = new StringBuffer(tmp.substring(0, tmp.length()-2));
		
		return rtn + tmp.toString();
	}

}
