package Vergleiche;

import java.util.ArrayList;

public class User {

	String name;
	String status; // z.B. Experte fuer ...
	ArrayList<TypeOfChange> typeOfChange;

	public User(String name) {
		this.name = name;
		this.typeOfChange = new ArrayList<TypeOfChange>();
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
	
	public ArrayList<TypeOfChange> getTypeOfChange() {
		return this.typeOfChange;
	}
	
	public UserType getUserType() {
		int correcting = 0;
		int improving = 0;
		int newKnowledge = 0;
		
		for(int i=0; i<this.typeOfChange.size(); i++) {
			if(this.typeOfChange.get(i).equals(UserType.KORREKTUR))
				correcting++;
			if(this.typeOfChange.get(i).equals(UserType.NEUES_WISSEN))
				newKnowledge++;
			if(this.typeOfChange.get(i).equals(UserType.VERBESSERUNG))
				improving++;
		}
		
		if(correcting > improving && correcting > newKnowledge) {
			return UserType.KORREKTUR;
		} else if (improving > correcting && improving > newKnowledge) {
			return UserType.VERBESSERUNG;
		} else if(newKnowledge > correcting && newKnowledge > improving) {
			return UserType.NEUES_WISSEN;
		} else {
			return UserType.KEINE_KLASSIFIZIERUNG;
		}
	}
}
