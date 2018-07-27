package cr.model;

public class LeagueStatistics {
	private SeasonStat currentSeason;
	private SeasonStat previousSeason;
	private SeasonStat bestSeason;
	public SeasonStat getCurrentSeason() {
		return currentSeason;
	}
	public void setCurrentSeason(SeasonStat currentSeason) {
		this.currentSeason = currentSeason;
	}
	public SeasonStat getPreviousSeason() {
		return previousSeason;
	}
	public void setPreviousSeason(SeasonStat previousSeason) {
		this.previousSeason = previousSeason;
	}
	public SeasonStat getBestSeason() {
		return bestSeason;
	}
	public void setBestSeason(SeasonStat bestSeason) {
		this.bestSeason = bestSeason;
	}
}
