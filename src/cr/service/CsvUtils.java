package cr.service;

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
		
		// deck id hashcode
		return deckStr.hashCode() + sep + deckStr.toString() + cardStrength.toString();
		
		
	}
}
