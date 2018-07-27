package cr.model;

public class Member {
	private String name;
	private String tag;
	private int rank;
	private int previousRank;
	private String role;
	private int expLevel;
	private int trophies;
	private int donations;
	private int donationsReceived;
	private int donationsDelta;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getPreviousRank() {
		return previousRank;
	}

	public void setPreviousRank(int previousRank) {
		this.previousRank = previousRank;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getExpLevel() {
		return expLevel;
	}

	public void setExpLevel(int expLevel) {
		this.expLevel = expLevel;
	}

	public int getTrophies() {
		return trophies;
	}

	public void setTrophies(int trophies) {
		this.trophies = trophies;
	}

	public int getDonations() {
		return donations;
	}

	public void setDonations(int donations) {
		this.donations = donations;
	}

	public int getDonationsReceived() {
		return donationsReceived;
	}

	public void setDonationsReceived(int donationsReceived) {
		this.donationsReceived = donationsReceived;
	}

	public int getDonationsDelta() {
		return donationsDelta;
	}

	public void setDonationsDelta(int donationsDelta) {
		this.donationsDelta = donationsDelta;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	private Arena arena;
}
