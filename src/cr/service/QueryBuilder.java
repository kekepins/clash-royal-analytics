package cr.service;


import cr.model.AdvClan;
import cr.model.Battle;
import cr.model.Clan;
import cr.model.Country;
import cr.model.Player;
import cr.model.Tracking;

public class QueryBuilder {
	
	private final static String API_BASE = "https://api.royaleapi.com/";
	private final static String CLAN_URI = "clan/";
	private final static String PLAYER_URI = "player/";
	private final static String BATTLE_URI = "/battle/";
	private final static String TRACKING_URI = "/tracking";
	private final static String TRACK_URI = "/track";
	private final static String TOP_PLAYERS_URI = "top/players/";
	private final static String TOP_CLANS_URI = "top/clans/";
	
	
	public static Query<Player> selectPlayer(String playerId) {
		Query<Player> query = new Query<Player>(API_BASE +  PLAYER_URI + "%s", Player.class); 
		query = query.withParam(playerId);
		
		return query;
	}
	
	
	public static Query<Tracking> selectClanTracking(String clanId) {
		
		Query<Tracking> query = new Query<Tracking>( API_BASE +  CLAN_URI + "%s" + TRACKING_URI, Tracking.class); 
		query = query.withParam(clanId);
		
		return query;
		
	}
	
	public static Query<AdvClan> selectClan(String clanId) {
		
		Query<AdvClan> query = new Query<AdvClan>( API_BASE +  CLAN_URI + "%s", AdvClan.class); 
		query = query.withParam(clanId);
		
		return query;
		
	}
	
	public static Query<Player[]> selectTopPlayers(Country country) {
		
		
		String queryStr = API_BASE +  TOP_PLAYERS_URI + (country != null ? "%s" : "");
		//Query<Player[]> query = new Query<Player[]>( API_BASE +  TOP_PLAYERS_URI + "%s", Player[].class); 
		Query<Player[]> query = new Query<Player[]>( queryStr, Player[].class); 
		
		if ( country != null ) {
			query = query.withParam(country.name());
		}
		
		return query;
		
	}
	
	public static Query<Clan[]> selectTopClans(Country country) {
		
		Query<Clan[]> query = new Query<Clan[]>( API_BASE +  TOP_CLANS_URI + "%s", Clan[].class); 
		query = query.withParam(country.name());
		
		return query;
		
	}
	
	public static Query<Battle[]> selectPlayerBattles(String playerId) {
		Query<Battle[]> query = new Query<Battle[]>( API_BASE +  PLAYER_URI + "%s" + BATTLE_URI, Battle[].class);
		
		query = query.withParam(playerId);
		return query;
	}
	

	
}
