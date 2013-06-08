package Vergleiche;

public class User {

	String name;
	String status; // z.B. Experte fuer ...

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setStatus(Status status) {
		if (status.equals(Status.BEGINNER))
			this.status = "Beginner";
		if (status.equals(Status.INTERMEDIATE))
			this.status = "Fortgeschritten";
		this.status = "Experte";
	}
	
	public String getStatus() {
		return this.status;
	}
}
