package cr.model;

import java.util.List;

public class Player {
	private String tag;
	private String name;
	private AdvClan clan;
	
	private Stats stats;
	
	private String deckLink;
	
	private List<Card> currentDeck;

	
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


}
