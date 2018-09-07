package cr.analyse.spark;

public class CardPlayStat implements Comparable<CardPlayStat> {
	private double playCount;
	private double winCount;
	private double looseCount;
	private double pctPlayed;
	private double pctWin;
	private String name;

	
	public CardPlayStat(String name, double playCount, double winCount, double looseCount, double pctPlayed, double pctWin ) {
		this.name 		= name;
		this.playCount 	= playCount;
		this.winCount 	= winCount;
		this.looseCount = looseCount;
		this.pctPlayed 	= pctPlayed;
		this.pctWin		= pctWin;

	}
	
	public String toString() {
		return name + ":played ["  + playCount + "(" + pctPlayed + ")] win [" + winCount +  "(" +  pctWin + ")] loose [" + looseCount  + "]";
	}
	

	@Override
	public int compareTo(CardPlayStat otherCardCount) {
		return Double.compare(otherCardCount.playCount, this.playCount);
	}
}
