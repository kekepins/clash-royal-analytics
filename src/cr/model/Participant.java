package cr.model;

public class Participant {
	private String tag;
	private String name;
	private int wins;
	private int cardsEarned;
	private int collectionDayBattlesPlayed;

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWins() {
		return wins;
	}
	public void setWin(int wins) {
		this.wins = wins;
	}
	public int getCardsEarned() {
		return cardsEarned;
	}
	public void setCardsEarned(int cardsEarned) {
		this.cardsEarned = cardsEarned;
	}
	public int getCollectionDayBattlesPlayed() {
		return collectionDayBattlesPlayed;
	}
	public void setCollectionDayBattlesPlayed(int collectionDayBattlesPlayed) {
		this.collectionDayBattlesPlayed = collectionDayBattlesPlayed;
	}
	public Long getScore() {
		long score = wins * 5000 + cardsEarned;
		
		// penality collection day
		if ( collectionDayBattlesPlayed == 2 ) {
			score -= 1000;
		}
		
		if ( collectionDayBattlesPlayed == 1 ) {
			score -= 2000;
		}
		
		return score;
		 
	}
}
