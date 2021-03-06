package API;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Strucktur einer Revision
 * 
 * @author Bernhard Hermes
 * 
 */
public class Revision {

	private int revid;
	private String user;
	private String name;
	private String content;
	private String timestamp;
	private int userid;
	boolean minorchange;
	private long size;

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public boolean isMinorchange() {
		return minorchange;
	}

	public void setMinorchange(boolean minorchange) {
		this.minorchange = minorchange;
	}

	public int getRevid() {
		return revid;
	}

	public String getUser() {
		return user;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public int getUserid() {
		return userid;
	}

	public void setRevID(String revid) {
		this.revid = Integer.parseInt(revid);

	}

	public void setUser(String user) {
		this.user = user;

	}

	public void setUserID(String userid) {
		this.userid = Integer.parseInt(userid);

	}

	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;

	}

	public String toString() {
		return "Article : " + name + " RevisionID: " + revid + " UserID:" + userid + " User: " + user
				+ " TimeSpamp: " + timestamp +  " Size: " +size+ " Minor change: " + minorchange + " content: " + content;
	}
	
	public String getDateString() {
		Date d = null;
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    try {
	      d = sdf.parse(timestamp);
	    } catch (ParseException e) {
	      
	    }
	    DateFormat out = new SimpleDateFormat("yyyy-MM-dd");
	   return out.format(d);
	  }
	
	public String getTimeString() {
		Date d = null;
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    try {
	      d = sdf.parse(timestamp);
	    } catch (ParseException e) {
	      
	    }
	    DateFormat out = new SimpleDateFormat("HH:mm:ss");
	   return out.format(d);
	  }

	public void setMinorchange(String minorchange) {
		if(minorchange == null)
			this.minorchange = false;
		else
			this.minorchange = true;
		
	}

	public void setSize(String size) {
		setSize(Long.parseLong(size));		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
