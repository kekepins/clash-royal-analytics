package cr;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import cr.model.WarLog;
import cr.service.ConfigManager;
import cr.service.CrServiceException;
import cr.service.QueryBuilder;

public class BestOfClanWar {
	private static String CLAN_VIK_ID = "";
	
	public static void main(String[] args) throws CrServiceException {
		
		
		ConfigManager.init();
		Map<String, Long> res = new TreeMap<>();
		WarLog[] warlogs = QueryBuilder.selectWarLog(CLAN_VIK_ID).execute();
		
		res = warlogs[0].getScore();
		
		for ( int idx = 1; idx < warlogs.length; idx++) {
			Map<String, Long> resInter = warlogs[idx].getScore();
			
			for (Map.Entry<String, Long> entry : resInter.entrySet()) {
				if ( !res.containsKey(entry.getKey() )) {
					res.put(entry.getKey(), entry.getValue());
				}
				else {
					res.put(entry.getKey(), entry.getValue() + res.get(entry.getKey()));
				}
			}
		}
		
		
		for (Map.Entry<String, Long> entry : entriesSortedByValues(res) ) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
	}
	
	static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>() {
                @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1; // Special fix to preserve items with equal values
                }
            }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
		
}
