package cr.extractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cr.extractor.model.PlayerId;
import cr.model.Battle;
import cr.model.Country;
import cr.model.GameMode;
import cr.model.Player;
import cr.service.ConfigManager;
import cr.service.CrServiceException;
import cr.service.QueryBuilder;


/**
 * Construct a battle dataset (only 1v1 ladder/ 
 *
 */
public class BattleVisitor {
	
	private final static String BASE_FILE = "d:\\temp\\cr_data_";
	
	private LinkedList<PlayerId> waitingPlayers = new LinkedList<>();
	private Map<String, PlayerId> donePlayers = new HashMap<>(); 
	
	//private FileWriter fileWriter;
	private BufferedWriter bw;
	
	public void init() throws CrServiceException {
		
		try {
			FileWriter fileWriter = new FileWriter(BASE_FILE + System.currentTimeMillis() + ".csv");
			bw = new BufferedWriter(fileWriter);
			
		} catch (IOException e) {
			throw new CrServiceException("Pbm in open file", e);
		}
		Player[] players = 
				QueryBuilder
					.selectTopPlayers(Country.FR)
					.execute();
		
		for ( Player player : players ) {
				
			//System.out.println("Adding " + player.getName());
			
			PlayerId playerId = new PlayerId(player.getTag(), player.getName());
			waitingPlayers.add(playerId);
			donePlayers.put(playerId.getTag(), playerId);
		}
	
	}
	
	/**
	 * visitTime in ms
	 * @param visitTime
	 */
	public void startVisit(long visitTime) {
		long start = System.currentTimeMillis();
		long currentWork = 0;
		
		while (currentWork < visitTime ) {
			

			// Get some new battles 4 player by 4 player (100 battles)
			Battle[] battles = getNewBattles(4);
			saveBattles(battles);
			
			currentWork = (System.currentTimeMillis()-start);
		}
		
	}
	
	/**
	 * Get new battle from the api
	 * @throws CrServiceException 
	 */
	private Battle[] getNewBattles(int playerCount){
		
		List<PlayerId> players = new ArrayList<>();
		
		String playerStr = "";
		
		// FIXME list  check size
		for ( int idx = 0; idx < playerCount; idx++) {
			PlayerId playerId = waitingPlayers.getLast();
			players.add(playerId);
			if ( idx != 0 ) {
				playerStr += ",";
			}
			playerStr +=  playerId.getTag();
		}
		
		
		// add player to done
		for ( PlayerId player : players) {
			donePlayers.put(player.getTag(), player);
		}
		
		try {
			Battle[] battles = QueryBuilder.selectPlayerBattles(playerStr ).execute();
			return battles;
		} 
		catch (CrServiceException e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Save data to file
	 */
	private void saveBattles(Battle[] battles) {
		// System.out.println("saving battles" + battles.length);
		
		for ( Battle battle : battles ) {
		
			// filter battles, only keep 
			GameMode mode = battle.getMode().getName();
			
			String type = null;
			if ( mode == GameMode.Ladder ||  mode == GameMode.Challenge ) {
				type = mode.name();
			}
			else if ( mode == GameMode.Showdown_Ladder && "clanWarWarDay".equals(battle.getType())) {
				type = "clanWarWarDay";
			}
			
			if (type != null ) {
				try {
					bw.write(battle.toCsv("^", type));
					bw.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			
			//System.out.println(mode + " " +battle.getType());
			
			
			// add new players..
			List<Player> players = battle.getOpponent();
			
			for ( Player player : players) {
				if (!donePlayers.containsKey(player.getTag())) {
					waitingPlayers.add(new PlayerId(player.getTag(), player.getName()));
				}
			}
			
			try {
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String args[]) throws CrServiceException {
		
		ConfigManager.init();
		
		BattleVisitor battleVisitor = new BattleVisitor();
		battleVisitor.init();
		battleVisitor.startVisit(30 * 1000); /*1mn*/
		battleVisitor.end();
	}

	private void end() {
		try {
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
