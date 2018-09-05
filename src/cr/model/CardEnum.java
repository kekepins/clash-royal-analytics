package cr.model;

public enum CardEnum {
	knight(3, CardType.Troop, Rarity.Common),
	archers(3, CardType.Troop, Rarity.Common),
	goblins(2, CardType.Troop, Rarity.Common),
	giant(5, CardType.Troop, Rarity.Rare),
	pekka(7, CardType.Troop, Rarity.Epic),
	minions(3, CardType.Troop, Rarity.Common),
	balloon(5, CardType.Troop, Rarity.Epic),
	witch(5, CardType.Troop, Rarity.Epic),
	barbarians(5, CardType.Troop, Rarity.Common),
	golem(8, CardType.Troop, Rarity.Epic),
	skeletons(1, CardType.Troop, Rarity.Common),
	valkyrie(4, CardType.Troop, Rarity.Rare),
	skeleton_army(3, CardType.Troop, Rarity.Epic),
	bomber(3, CardType.Troop, Rarity.Common),
	musketeer(4, CardType.Troop, Rarity.Rare),
	baby_dragon(4, CardType.Troop, Rarity.Epic),
	prince(5, CardType.Troop, Rarity.Epic),
	wizard(5, CardType.Troop, Rarity.Rare),
	mini_pekka(4, CardType.Troop, Rarity.Rare),
	spear_goblins(2, CardType.Troop, Rarity.Common),
	giant_skeleton(6, CardType.Troop, Rarity.Epic),
	hog_rider(4, CardType.Troop, Rarity.Rare),
	minion_horde(5, CardType.Troop, Rarity.Common),
	ice_wizard(3, CardType.Troop, Rarity.Legendary),
	royal_giant(6, CardType.Troop, Rarity.Common),
	guards(3, CardType.Troop, Rarity.Epic),
	princess(3, CardType.Troop, Rarity.Legendary),
	dark_prince(4, CardType.Troop, Rarity.Epic),
	three_musketeers(9, CardType.Troop, Rarity.Rare),
	lava_hound(7, CardType.Troop, Rarity.Legendary), // Molosse de lave
	ice_spirit(1, CardType.Troop, Rarity.Common),
	fire_spirits(1, CardType.Troop, Rarity.Common),
	miner(3, CardType.Troop, Rarity.Legendary),
	sparky(6, CardType.Troop, Rarity.Legendary),
	bowler(5, CardType.Troop, Rarity.Epic),
	lumberjack(4, CardType.Troop, Rarity.Legendary),
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
	zappies(4, CardType.Troop, Rarity.Rare),
	rascals(5, CardType.Troop, Rarity.Rare),
	cannon_cart(5, CardType.Troop, Rarity.Epic),
	mega_knight(7, CardType.Troop, Rarity.Legendary),
	skeleton_barrel(3, CardType.Troop, Rarity.Common),
	flying_machine(4, CardType.Troop, Rarity.Rare),
	royal_hogs(5, CardType.Troop, Rarity.Rare), // cochons royaux
	magic_archer(4, CardType.Troop, Rarity.Legendary),
	cannon(3, CardType.Building, Rarity.Common),
	goblin_hut(5, CardType.Building, Rarity.Rare),
	mortar(4, CardType.Building, Rarity.Common),
	inferno_tower(5, CardType.Building, Rarity.Rare),
	bomb_tower(4, CardType.Building, Rarity.Rare),
	barbarian_hut(7, CardType.Building, Rarity.Rare),
	tesla(4, CardType.Building, Rarity.Common),
	elixir_collector(6, CardType.Building, Rarity.Rare),
	x_bow(6, CardType.Building, Rarity.Epic),
	tombstone(3, CardType.Building, Rarity.Rare),
	furnace(4, CardType.Building, Rarity.Rare), // fournaise
	fireball(4, CardType.Spell, Rarity.Rare), 
	arrows(3, CardType.Spell, Rarity.Common), 
	rage(2, CardType.Spell, Rarity.Epic), 
	rocket(6, CardType.Spell, Rarity.Rare), 
	goblin_barrel(3, CardType.Spell, Rarity.Epic), 
	freeze(4, CardType.Spell, Rarity.Epic), 
	mirror(1, CardType.Spell, Rarity.Epic), 
	lightning(6, CardType.Spell, Rarity.Epic), 
	zap(2, CardType.Spell, Rarity.Common), 
	poison(4, CardType.Spell, Rarity.Epic), 
	graveyard(5, CardType.Spell, Rarity.Legendary), 
	the_log(2, CardType.Spell, Rarity.Legendary),   // buche
	tornado(3, CardType.Spell, Rarity.Epic), 
	clone(3, CardType.Spell, Rarity.Epic), 
	barbarian_barrel(3, CardType.Spell, Rarity.Epic), 
	heal(3, CardType.Spell, Rarity.Rare), 
	giant_snowball(2, CardType.Spell, Rarity.Common);
	
	
	private int elixir;
	public int getElixir() {
		return elixir;
	}

	public CardType getCardType() {
		return cardType;
	}

	public Rarity getRarity() {
		return rarity;
	}

	private CardType cardType;
	private Rarity rarity;
	
	private CardEnum(int elixir, CardType type, Rarity rarity) {
		this.elixir = elixir;
		this.cardType= type;
		this.rarity = rarity;
	}
	
	public static CardEnum fromKey(String key) {
		
		if (key != null ) {
			String toSearch = key.replace('-', '_');
			
			for (CardEnum cardEnum : CardEnum.values()) {
				if ( cardEnum.name().equals(toSearch)) {
					return cardEnum;
				}
			}
		}
		return null;
		
		
	}
	
	
}
