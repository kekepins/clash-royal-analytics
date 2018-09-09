package cr.extractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
public class BattleVisitorThread {
	
	private final static String BASE_FILE = "d:\\temp\\cr_data_";
	private static final int BATTLE_PLAYER_COUNT = 4; 
	private static final int THREAD_COUNT = 8;
	
	private LinkedList<PlayerId> waitingPlayers = new LinkedList<>();
	private Map<String, PlayerId> donePlayers = new HashMap<>(); 
	
	private BufferedWriter bw;
	
	private boolean headerWritten = false;
	
	/**
	 * Init list with some players (Top fr for example) 
	 * 
	 * @throws CrServiceException
	 */
	public void init() throws CrServiceException {
		
		try {
			FileWriter fileWriter = new FileWriter(BASE_FILE + System.currentTimeMillis() + ".csv");
			bw = new BufferedWriter(fileWriter);
			
		} catch (IOException e) {
			throw new CrServiceException("Pbm in open file", e);
		}
		Player[] players = 
				QueryBuilder
					.selectTopPlayers(null)
					//.selectTopPlayers(Country.FR)
					//.selectTopPlayers(Country._INT)
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
		ExecutorService executor = Executors.newFixedThreadPool( THREAD_COUNT );

		while (currentWork < visitTime ) {

			
			// Create task list
			List <Callable<Battle[]>> callableTasks = new ArrayList<Callable<Battle[]>>();
			
			for ( int i = 0; i < THREAD_COUNT; i++ ) {
				callableTasks.add(new BattleResultTask(getNewBattlesId(BATTLE_PLAYER_COUNT)));
			}
			
			// Start threads ...
			
			try {
				 List<Future<Battle[]>> futures = executor.invokeAll(callableTasks);
				 for (Future<Battle[]> future : futures) {
					 Battle[] battles = future.get();
					 saveBattles(battles);
				 }

			} 
			catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			
			currentWork = (System.currentTimeMillis()-start);
			System.out.println("work for " + currentWork);
			
		}
		
		System.out.println("shutdown executor now " + currentWork);
		executor.shutdown();
	}
	
	
	private synchronized String getNewBattlesId(int playerCount){
		String playerStr = "";
		//List<PlayerId> players = new ArrayList<>();
		
		for ( int idx = 0; idx < playerCount; idx++) {
			PlayerId playerId = waitingPlayers.removeLast();
			//players.add(playerId);
			if ( idx != 0 ) {
				playerStr += ",";
			}
			playerStr +=  playerId.getTag();
		}
		
		return playerStr;
	}
	

	/**
	 * Save data to file
	 */
	private synchronized  void saveBattles(Battle[] battles) {
		// System.out.println("saving battles" + battles.length);
		
		for ( Battle battle : battles ) {
		
			// filter battles, only keep 
			GameMode mode = battle.getMode().getName();
			
			String type = null;
			if ( mode == GameMode.Ladder ||  mode == GameMode.Challenge ) {
				type = mode.name();
			}
			/*else if ( mode == GameMode.Showdown_Ladder && "clanWarWarDay".equals(battle.getType())) {
				type = "clanWarWarDay";
			}*/
			
			if (type != null ) {
				try {
					
					if ( !headerWritten) {
						bw.write(battle.toCsvHeader("^"));
						bw.newLine();
						headerWritten = true;
					}
					
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
				if (!donePlayers.containsKey(player.getTag()) ) {
					PlayerId playerId = new PlayerId(player.getTag(), player.getName());
					waitingPlayers.add(playerId);
					donePlayers.put(player.getTag(), playerId);
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
		
		BattleVisitorThread battleVisitor = new BattleVisitorThread();
		battleVisitor.init();
		battleVisitor.startVisit(10 * 60 * 60 * 1000); /*x * 1mn*/
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
