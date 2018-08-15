package cr.model;

import java.util.List;

public class Battle {
	private String type;
	private String challengeType;
	private BattleMode mode;
	
	private long utcTime;
	private String deckType;
	private String teamSize;
	private int winner;
	private short teamCrown;
	private short opponentCrowns;
	
	private List<Player> team;
	private List<Player> opponent;
	
	private Arena arena;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChallengeType() {
		return challengeType;
	}

	public void setChallengeType(String challengeType) {
		this.challengeType = challengeType;
	}

	public BattleMode getMode() {
		return mode;
	}

	public void setMode(BattleMode mode) {
		this.mode = mode;
	}

	public long getUtcTime() {
		return utcTime;
	}

	public void setUtcTime(long utcTime) {
		this.utcTime = utcTime;
	}

	public String getDeckType() {
		return deckType;
	}

	public void setDeckType(String deckType) {
		this.deckType = deckType;
	}

	public String getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(String teamSize) {
		this.teamSize = teamSize;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public short getTeamCrown() {
		return teamCrown;
	}

	public void setTeamCrown(short teamCrown) {
		this.teamCrown = teamCrown;
	}

	public short getOpponentCrowns() {
		return opponentCrowns;
	}

	public void setOpponentCrowns(short opponentCrowns) {
		this.opponentCrowns = opponentCrowns;
	}

	public List<Player> getTeam() {
		return team;
	}

	public void setTeam(List<Player> team) {
		this.team = team;
	}

	public List<Player> getOpponent() {
		return opponent;
	}

	public void setOpponent(List<Player> opponent) {
		this.opponent = opponent;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}
	
	public String toCsv(String sep, String type) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(type +sep);
		
		
		// winner
		strBuilder.append("" + getWinnerId() + sep);
		
		// time
		strBuilder.append("" + utcTime + sep);
		
		// P1 
		strBuilder.append(getTeam().get(0).toCsv(sep));
		
		// P2
		strBuilder.append(getOpponent().get(0).toCsv(sep));
		
		
		return strBuilder.toString();
	}
	
	// 0 : draw, 1 : P1, 2  : P2
	private int getWinnerId() {
		if ( teamCrown == opponentCrowns ) {
			return 0;
		}
		if ( teamCrown > opponentCrowns ) {
			return 1;
		}
		return 2;
		
	}


}
