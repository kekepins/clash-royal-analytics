package cr.model;

import java.util.List;

import cr.service.CsvUtils;

public class Player {
	private String tag;
	private String name;
	private AdvClan clan;
	
	private Stats stats;
	
	private String deckLink;
	
	private List<Card> currentDeck;
	
	private int startTrophies;
	private int crownsEarned;

	
	public int getStartTrophies() {
		return startTrophies;
	}

	public void setStartTrophies(int startTrophies) {
		this.startTrophies = startTrophies;
	}

	public int getCrownsEarned() {
		return crownsEarned;
	}

	public void setCrownsEarned(int crownsEarned) {
		this.crownsEarned = crownsEarned;
	}

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

	public AdvClan getClan() {
		return clan;
	}

	public void setClan(AdvClan clan) {
		this.clan = clan;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public String getDeckLink() {
		return deckLink;
	}

	public void setDeckLink(String deckLink) {
		this.deckLink = deckLink;
	}

	public List<Card> getCurrentDeck() {
		return currentDeck;
	}

	public void setCurrentDeck(List<Card> currentDeck) {
		this.currentDeck = currentDeck;
	}

	public List<Card> getDeck() {
		return currentDeck;
	}

	public void setDeck(List<Card> desck) {
		this.currentDeck = desck;
	}

	public String toCsv(String sep) {
		StringBuilder strBuilder = new StringBuilder();
		
		// name
		strBuilder.append(this.name.replace(sep, "") + sep);
		
		// tag
		strBuilder.append(this.tag + sep);
		
		// clan name
		if ( clan !=null && clan.getName() != null ) {
			strBuilder.append(clan.getName().replace(sep, "") + sep);
		}
		else {
			strBuilder.append( sep);
		}
		
		// trophies
		strBuilder.append(this.startTrophies + sep);
		
		// crown earned
		strBuilder.append(this.crownsEarned + sep);
		
		// deck
		strBuilder.append(CsvUtils.deckToCsv(this.currentDeck, sep) );
		
		return strBuilder.toString();
	}

	public Object toCsvHeader(String sep, String id) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("name" + id + sep);
		strBuilder.append("tag" + id + sep);
		strBuilder.append("clan" + id + sep);
		strBuilder.append("startTrophies" + id + sep);
		strBuilder.append("crownsEarned" + id + sep);
		strBuilder.append(CsvUtils.deckToCsvHeader(id, sep));

		return strBuilder.toString();
	}


}
