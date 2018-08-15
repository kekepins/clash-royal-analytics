package cr.model;

public class BattleMode {
	private GameMode name;
	private String deck;
	private String cardLevels;
	private int overtimeSeconds;
	private String players;
	private boolean sameDeck;
	public GameMode getName() {
		return name;
	}
	public void setName(GameMode name) {
		this.name = name;
	}
	public String getDeck() {
		return deck;
	}
	public void setDeck(String deck) {
		this.deck = deck;
	}
	public String getCardLevels() {
		return cardLevels;
	}
	public void setCardLevels(String cardLevels) {
		this.cardLevels = cardLevels;
	}
	public int getOvertimeSeconds() {
		return overtimeSeconds;
	}
	public void setOvertimeSeconds(int overtimeSeconds) {
		this.overtimeSeconds = overtimeSeconds;
	}
	public String getPlayers() {
		return players;
	}
	public void setPlayers(String players) {
		this.players = players;
	}
	public boolean isSameDeck() {
		return sameDeck;
	}
	public void setSameDeck(boolean sameDeck) {
		this.sameDeck = sameDeck;
	}
}
