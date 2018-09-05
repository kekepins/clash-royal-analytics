package cr.analyse.spark;

public class CardCount implements Comparable<CardCount> {
	private double winCount;
	private double looseCount;
	//private double pct;
	private String name;
	
	public CardCount(String name, double winCount ) {
		this.name = name;
		this.winCount = winCount;
	}
	
	public String toString(double totalGames) {
		double pct =  (looseCount + winCount) / totalGames;
		double pctWin =  winCount / (looseCount + winCount);
		double pctLoose =  looseCount / (looseCount + winCount);
		return name + ":played ["  + (looseCount + winCount)  + "(" + pct + ")] win [" + winCount +  "(" +  pctWin + ")] loose [" + looseCount + "(" + pctLoose+ ")]";
	}
	
	public void setLooseCount(double looseCount) {
		this.looseCount = looseCount;
	}

	@Override
	public int compareTo(CardCount otherCardCount) {
		return Double.compare(otherCardCount.looseCount + otherCardCount.looseCount, this.winCount + this.looseCount);
	}
}
