package cr.model;

import java.util.HashMap;
import java.util.Map;

public class DataUtils {
	
	private static Map<CardEnum, String> frCards = new HashMap<>();
	static { 
		
		frCards.put(CardEnum.knight, "chevalier");
		frCards.put(CardEnum.archers, "archers");
		frCards.put(CardEnum.goblins, "goblins");
		frCards.put(CardEnum.giant, "geant");
		frCards.put(CardEnum.pekka, "pekka");
		frCards.put(CardEnum.minions, "gargouilles");
		
		frCards.put(CardEnum.balloon, "bollon");
		frCards.put(CardEnum.witch, "sorciere");
		frCards.put(CardEnum.barbarians, "barbares");
		frCards.put(CardEnum.golem, "golem");
		frCards.put(CardEnum.skeletons, "squellettes");
		frCards.put(CardEnum.valkyrie, "valkyrie");

		frCards.put(CardEnum.skeleton_army, "armee de squelettes");
		frCards.put(CardEnum.bomber, "bombardier");
		frCards.put(CardEnum.musketeer, "mousquetaire");
		frCards.put(CardEnum.baby_dragon, "bebe dragon");
		frCards.put(CardEnum.prince, "prince");
		frCards.put(CardEnum.wizard, "sorcier");

		frCards.put(CardEnum.mini_pekka, "mini pekka");
		frCards.put(CardEnum.spear_goblins, "gobelins a lances");
		frCards.put(CardEnum.giant_skeleton, "geant squelette");
		frCards.put(CardEnum.hog_rider, "chev cochon");
		frCards.put(CardEnum.minion_horde, "horde gargouilles");
		frCards.put(CardEnum.ice_wizard, "sorcier de glace");

		frCards.put(CardEnum.royal_giant, "geant royal");
		frCards.put(CardEnum.guards, "gardes");
		frCards.put(CardEnum.princess, "princesse");
		frCards.put(CardEnum.dark_prince, "prince tenebr");
		frCards.put(CardEnum.three_musketeers, "3 mousquetaires");
		frCards.put(CardEnum.lava_hound, "molosse de lave");

		frCards.put(CardEnum.ice_spirit, "esprit de glace");
		frCards.put(CardEnum.fire_spirits, "esprit de feu");
		frCards.put(CardEnum.miner, "mineur");
		frCards.put(CardEnum.sparky, "sparky");
		frCards.put(CardEnum.bowler, "bouliste");
		frCards.put(CardEnum.lumberjack, "bucheron");

	}
	
	
	/*

	battle_ram(4, CardType.Troop, Rarity.Rare),  //Belier de combat
	inferno_dragon(6, CardType.Troop, Rarity.Legendary),
	ice_golem(2, CardType.Troop, Rarity.Rare),
	mega_minion(3, CardType.Troop, Rarity.Rare),
	dart_goblin(3, CardType.Troop, Rarity.Rare),
	goblin_gang(3, CardType.Troop, Rarity.Common),
	electro_wizard(4, CardType.Troop, Rarity.Legendary),
	elite_barbarians(6, CardType.Troop, Rarity.Common),
	hunter(4, CardType.Troop, Rarity.Epic),
	executioner(5, CardType.Troop, Rarity.Epic),
	bandit(3, CardType.Troop, Rarity.Legendary),  // voleuse
	royal_recruits(8, CardType.Troop, Rarity.Common), 
	night_witch(4, CardType.Troop, Rarity.Legendary),
	bats(2, CardType.Troop, Rarity.Common),
	royal_ghost(3, CardType.Troop, Rarity.Legendary),
	zappies(4, CardType.Troop, Rarity.Rare),  //electrocuteurs
	rascals(5, CardType.Troop, Rarity.Rare),
	cannon_cart(5, CardType.Troop, Rarity.Epic),
	mega_knight(7, CardType.Troop, Rarity.Legendary),
	skeleton_barrel(3, CardType.Troop, Rarity.Common),
	flying_machine(4, CardType.Troop, Rarity.Rare),
	royal_hogs(5, CardType.Troop, Rarity.Rare), // cochons royaux
	goblin_giant(6, CardType.Troop, Rarity.Epic ),
	magic_archer(4, CardType.Troop, Rarity.Legendary),
	cannon(3, CardType.Building, Rarity.Common),
	goblin_hut(5, CardType.Building, Rarity.Rare),
	mortar(4, CardType.Building, Rarity.Common),
	inferno_tower(5, CardType.Building, Rarity.Rare),
	bomb_tower(4, CardType.Building, Rarity.Rare),
	barbarian_hut(7, CardType.Building, Rarity.Rare),
	tesla(4, CardType.Building, Rarity.Common),
	elixir_collector(6, CardType.Building, Rarity.Rare),
	x_bow(6, CardType.Building, Rarity.Epic),  // arc-x
	tombstone(3, CardType.Building, Rarity.Rare),
	furnace(4, CardType.Building, Rarity.Rare), // fournaise
	fireball(4, CardType.Spell, Rarity.Rare), 
	arrows(3, CardType.Spell, Rarity.Common), 
	rage(2, CardType.Spell, Rarity.Epic), 
	rocket(6, CardType.Spell, Rarity.Rare), 
	goblin_barrel(3, CardType.Spell, Rarity.Epic), 
	freeze(4, CardType.Spell, Rarity.Epic), 
	mirror(1, CardType.Spell, Rarity.Epic), 
	lightning(6, CardType.Spell, Rarity.Epic),  // foudre
	zap(2, CardType.Spell, Rarity.Common), 
	poison(4, CardType.Spell, Rarity.Epic), 
	graveyard(5, CardType.Spell, Rarity.Legendary), 
	the_log(2, CardType.Spell, Rarity.Legendary),   // buche
	tornado(3, CardType.Spell, Rarity.Epic), 
	clone(3, CardType.Spell, Rarity.Epic), 
	barbarian_barrel(3, CardType.Spell, Rarity.Epic), 
	heal(3, CardType.Spell, Rarity.Rare), 
	giant_snowball(2, CardType.Spell, Rarity.Common);*/
	public static String getTranslation(String deck) {
		return null;
		
		
	}
}
