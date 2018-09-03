package cr.analyse.spark;

public class CardCount implements Comparable<CardCount> {
	private double count;
	private double pct;
	private String name;
	
	public CardCount(String name, double count, double pct ) {
		this.name = name;
		this.count = count;
		this.pct = pct;
	}
	
	public String toString() {
		return name + ":" + count + " -----> " + pct;
	}

	@Override
	public int compareTo(CardCount otherCardCount) {
		return Double.compare(otherCardCount.count, this.count);
	}
}
