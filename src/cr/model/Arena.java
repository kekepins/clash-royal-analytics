package cr.model;

public class Arena {
	private String name;
	private String arena;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArena() {
		return arena;
	}
	public void setArena(String arena) {
		this.arena = arena;
	}
	public int getArenaID() {
		return arenaID;
	}
	public void setArenaID(int arenaID) {
		this.arenaID = arenaID;
	}
	public int getTrophyLimit() {
		return trophyLimit;
	}
	public void setTrophyLimit(int trophyLimit) {
		this.trophyLimit = trophyLimit;
	}
	private int arenaID;
	private int trophyLimit;
	
}
