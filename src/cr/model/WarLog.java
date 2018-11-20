package cr.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WarLog {
	private List<Participant> participants;
	private int seasonNumber;
	public List<Participant> getParticipants() {
		return participants;
	}
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	public int getSeasonNumber() {
		return seasonNumber;
	}
	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	
	public TreeMap<String, Long> getScore() {
		
		TreeMap<String, Long> score = new TreeMap<>(); 
		for ( Participant participant : participants ) {
			score.put(participant.getTag() + "_" + participant.getName(), participant.getScore());
		}
		return score;
		
	}
}
