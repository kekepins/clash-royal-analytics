package cr.model;

import java.util.List;

public class StatPlayer extends Player{
	private int trophies;
	private Arena arena;
	private LeagueStatistics leagueStatistics;
	private Games games;
	
	private List<Card> cards;

	public int getTrophies() {
		return trophies;
	}

	public void setTrophies(int trophies) {
		this.trophies = trophies;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public LeagueStatistics getLeagueStatistics() {
		return leagueStatistics;
	}

	public void setLeagueStatistics(LeagueStatistics leagueStatistics) {
		this.leagueStatistics = leagueStatistics;
	}

	public Games getGames() {
		return games;
	}

	public void setGames(Games games) {
		this.games = games;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
}
