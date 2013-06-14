package API;

public class UserContribs {
private String user;
private String title;
private String timestamp;
private String comment;
private int userid;
private int revid;
private int pageid;
private int size;
private int sizediff;
private boolean minor;


public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getTimestamp() {
	return timestamp;
}
public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public int getRevid() {
	return revid;
}
public void setRevid(int revid) {
	this.revid = revid;
}
public int getPageid() {
	return pageid;
}
public void setPageid(int pageid) {
	this.pageid = pageid;
}
public int getSize() {
	return size;
}
public void setSize(int size) {
	this.size = size;
}
public int getSizediff() {
	return sizediff;
}
public void setSizediff(int sizediff) {
	this.sizediff = sizediff;
}
public boolean isMinor() {
	return minor;
}
public void setMinor(boolean minor) {
	this.minor = minor;
}
public void setMinorchange(String minor) {
	if(minor == null)
		this.minor = false;
	else
		this.minor = true;
}

public String toString()
{
	StringBuffer rtn = new StringBuffer();
	
	rtn.append("UserID: " + userid + "\n");
	rtn.append("User: " + user + "\n");
	rtn.append("PageID: " + pageid + "\n");
	rtn.append("RevID: " + revid + "\n");
	rtn.append("Title: " + title + "\n");
	rtn.append("Timestamp: " + timestamp + "\n");
	rtn.append("Minor: " + minor + "\n");
	rtn.append("Comment: " + comment + "\n");
	rtn.append("Size: " + size + "\n");
	rtn.append("SizeDiff: " + sizediff + "\n");
	
	
	return rtn.toString();
}


}
