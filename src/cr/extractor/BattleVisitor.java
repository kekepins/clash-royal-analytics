package cr.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cr.extractor.model.PlayerId;
import cr.model.Battle;
import cr.model.Country;
import cr.model.Player;
import cr.service.ConfigManager;
import cr.service.CrServiceException;
import cr.service.QueryBuilder;


/**
 * Construct a battle dataset (only 1v1 ladder/ 
 *
 */
public class BattleVisitor {
	
	private LinkedList<PlayerId> waitingPlayers = new LinkedList<>();
	private Map<String, PlayerId> donePlayers = new HashMap<>(); 
	
	public void init() throws CrServiceException {
		Player[] players = 
				QueryBuilder
					.selectTopPlayers(Country.FR)
					.execute();
		
		for ( Player player : players ) {
				
			System.out.println("Adding " + player.getName());
			waitingPlayers.add(new PlayerId(player.getTag(), player.getName()));
		}
	
	}
	
	/**
	 * visitTime in ms
	 * @param visitTime
	 */
	public void startVisit(long visitTime) {
		long start = System.currentTimeMillis();
		long currentWork = 0;
		
		//while (currentWork < visitTime ) {
			

			// Get some new battles 4 player by 4 player (100 battles)
			Battle[] battles = getNewBattles(4);
			saveBattles(battles);
			
			currentWork = (start - System.currentTimeMillis());
		//}
		
	}
	
	/**
	 * Get new battle from the api
	 * @throws CrServiceException 
	 */
	private Battle[] getNewBattles(int playerCount){
		
		List<PlayerId> players = new ArrayList<>();
		
		String playerStr = "";
		// get N player
		
		// FIXME list  check size
		for ( int idx = 0; idx < playerCount; idx++) {
			PlayerId playerId = waitingPlayers.getLast();
			players.add(playerId);
			if ( idx != 0 ) {
				playerStr += ",";
			}
			playerStr +=  playerId.getTag();
		}
		
		
		
		try {
			//Battle[] battles = QueryBuilder.selectPlayerBattles(player1.getTag() + "," + player2.getTag() + "," + player3.getTag() ).execute();
			Battle[] battles = QueryBuilder.selectPlayerBattles(playerStr ).execute();
			
			// add player to done
			
			for ( PlayerId player : players) {
				donePlayers.put(player.getTag(), player);
			}
			
			return battles;
		} 
		catch (CrServiceException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Save data to file
	 */
	private void saveBattles(Battle[] battles) {
		
	}

	public static void main(String args[]) throws CrServiceException {
		
		ConfigManager.init();
		
		BattleVisitor battleVisitor = new BattleVisitor();
		battleVisitor.init();
		battleVisitor.startVisit(60 * 1000); /*1mn*/
	}
}
