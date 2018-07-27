package cr.model;

import java.util.List;

public class AdvClan extends Clan{
	
	private String description;
	private String type;
	private int requiredScore;
	private int donations;
	private List<Member> members;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRequiredScore() {
		return requiredScore;
	}
	public void setRequiredScore(int requiredScore) {
		this.requiredScore = requiredScore;
	}
	public int getDonations() {
		return donations;
	}
	public void setDonations(int donations) {
		this.donations = donations;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}

}
