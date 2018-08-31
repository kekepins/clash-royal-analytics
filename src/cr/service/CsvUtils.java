package cr.service;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cr.model.Card;
import cr.model.CardEnum;

public class CsvUtils {
	public static String deckToCsv(List<Card> deckCards, String sep) {
		
		Map<CardEnum, Card> cards = new HashMap<>();
		
		
		for ( Card card : deckCards) {
			cards.put(card.getCardEnum(), card);
		}
		
		StringBuilder deckStr = new StringBuilder();
		StringBuilder cardStrength = new StringBuilder();
		StringBuilder humanDeckStr =  new StringBuilder();
		BitSet cardBitset = new BitSet();
		int idx = 0;
		
		for ( CardEnum card : CardEnum.values()) {
			Card cardFound = cards.get(card);
			if (cardFound != null) {
				deckStr.append(1);
				cardStrength.append(cardFound.getLevel() + sep);
				cardBitset.set(idx, true);
				humanDeckStr.append(cardFound.getCardEnum().name() + " ");
			}
			else {
				deckStr.append(0);
				cardBitset.set(idx, false);
			}
			deckStr.append(sep);
			idx++;
		}
	
		
		// deck id 
		return bytesToHex(cardBitset.toByteArray()) + sep + humanDeckStr + sep + deckStr.toString() + cardStrength.toString();
		
		
	}
	
	public static String deckToString(List<Card> deckCards,  String sep) {
		Map<CardEnum, Card> cards = new HashMap<>();
		
		for ( Card card : deckCards) {
			cards.put(card.getCardEnum(), card);
		}
		
		StringBuilder deckStr = new StringBuilder();
		StringBuilder cardStrength = new StringBuilder();

		
		for ( CardEnum card : CardEnum.values()) {
			//System.out.println(card);
			Card cardFound = cards.get(card);
			if (cardFound != null) {
				deckStr.append(1);
				cardStrength.append(cardFound.getLevel() + sep);
			}
			else {
				deckStr.append(0);
			}
			deckStr.append(sep);
		}
		
		return deckStr.toString();
	}

	public static String deckToCsvHeader(String id, String sep) {
		StringBuilder str = new StringBuilder();
		str.append("deckid" + id  + sep);
		str.append("human_deck" + id  + sep);
		for ( CardEnum card : CardEnum.values()) {
			str.append(card.name() + id  + sep);
		}
		
		for ( int i = 0; i < 8; i++) {
			str.append("strenght" + id + "_" + i + sep);
		}
		
		return str.toString();
	}
	
	 public static String bitset2bitstring(final BitSet bitset, final int length) {
	    final StringBuilder result = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	      result.append(bitset.get(i) ? "1" : "0");
	    }
	    return result.toString();
	}


	public static String bytesToHex(byte[] in) {
	    final StringBuilder builder = new StringBuilder();
	    for(byte b : in) {
	        builder.append(String.format("%02x", b));
	    }
	    return builder.toString();
	}


}
