# clash-royal-analytics
## first step construct dataset with the help of cr-api (https://github.com/RoyaleAPI)

### construct utility classes to access REST cr-api:


Example:
Get top player FR
```java
			Player[] players = 
					QueryBuilder
						.selectTopPlayers(Country.FR)
						.withKeys(Arrays.asList(new String[] {"name", "tag"}))
						.withMax(5)
						.execute();
			
```

or get Player Battles

```java
			Battle[] battles = QueryBuilder
					.selectPlayerBattles(HOULALA_ID)
					.execute();

```

 ### then iterate threw battles to generate a csv file:
 
 ```java
 
 	public static void main(String args[]) throws CrServiceException {
		
		ConfigManager.init();
		
		BattleVisitor battleVisitor = new BattleVisitor();
		battleVisitor.init();
		battleVisitor.startVisit(10 * 1000); /*x * 1mn*/
		battleVisitor.end();
	}
```

Battle are initialized from top ladder players

```java
	
		Player[] players = 
				QueryBuilder
					.selectTopPlayers(Country.FR)
					.execute();
```

Then getting player last matches, and keeping opponents to get other results.

 ### I defined csv format like this
|type|winner|utctime|name_P1|tag_P1|clan_P1|startTrophies_P1|crownsEarned_P1|deckid_P1|knight_P1|archers_P1|goblins_P1|giant_P1|pekka_P1|minions_P1|balloon_P1|witch_P1|barbarians_P1|golem_P1|skeletons_P1|valkyrie_P1|skeleton_army_P1|bomber_P1|musketeer_P1|baby_dragon_P1|prince_P1|wizard_P1|mini_pekka_P1|spear_goblins_P1|giant_skeleton_P1|hog_rider_P1|minion_horde_P1|ice_wizard_P1|royal_giant_P1|guards_P1|princess_P1|dark_prince_P1|three_musketeers_P1|lava_hound_P1|ice_spirit_P1|fire_spirits_P1|miner_P1|sparky_P1|bowler_P1|lumberjack_P1|battle_ram_P1|inferno_dragon_P1|ice_golem_P1|mega_minion_P1|dart_goblin_P1|goblin_gang_P1|electro_wizard_P1|elite_barbarians_P1|hunter_P1|executioner_P1|bandit_P1|royal_recruits_P1|night_witch_P1|bats_P1|royal_ghost_P1|zappies_P1|rascals_P1|cannon_cart_P1|mega_knight_P1|skeleton_barrel_P1|flying_machine_P1|royal_hogs_P1|magic_archer_P1|cannon_P1|goblin_hut_P1|mortar_P1|inferno_tower_P1|bomb_tower_P1|barbarian_hut_P1|tesla_P1|elixir_collector_P1|x_bow_P1|tombstone_P1|furnace_P1|fireball_P1|arrows_P1|rage_P1|rocket_P1|goblin_barrel_P1|freeze_P1|mirror_P1|lightning_P1|zap_P1|poison_P1|graveyard_P1|the_log_P1|tornado_P1|clone_P1|barbarian_barrel_P1|heal_P1|giant_snowball_P1|strenght_P1_0|strenght_P1_1|strenght_P1_2|strenght_P1_3|strenght_P1_4|strenght_P1_5|strenght_P1_6|strenght_P1_7|name_P2|tag_P2|clan_P2|startTrophies_P2|crownsEarned_P2|deckid_P2|knight_P2|archers_P2|goblins_P2|giant_P2|pekka_P2|minions_P2|balloon_P2|witch_P2|barbarians_P2|golem_P2|skeletons_P2|valkyrie_P2|skeleton_army_P2|bomber_P2|musketeer_P2|baby_dragon_P2|prince_P2|wizard_P2|mini_pekka_P2|spear_goblins_P2|giant_skeleton_P2|hog_rider_P2|minion_horde_P2|ice_wizard_P2|royal_giant_P2|guards_P2|princess_P2|dark_prince_P2|three_musketeers_P2|lava_hound_P2|ice_spirit_P2|fire_spirits_P2|miner_P2|sparky_P2|bowler_P2|lumberjack_P2|battle_ram_P2|inferno_dragon_P2|ice_golem_P2|mega_minion_P2|dart_goblin_P2|goblin_gang_P2|electro_wizard_P2|elite_barbarians_P2|hunter_P2|executioner_P2|bandit_P2|royal_recruits_P2|night_witch_P2|bats_P2|royal_ghost_P2|zappies_P2|rascals_P2|cannon_cart_P2|mega_knight_P2|skeleton_barrel_P2|flying_machine_P2|royal_hogs_P2|magic_archer_P2|cannon_P2|goblin_hut_P2|mortar_P2|inferno_tower_P2|bomb_tower_P2|barbarian_hut_P2|tesla_P2|elixir_collector_P2|x_bow_P2|tombstone_P2|furnace_P2|fireball_P2|arrows_P2|rage_P2|rocket_P2|goblin_barrel_P2|freeze_P2|mirror_P2|lightning_P2|zap_P2|poison_P2|graveyard_P2|the_log_P2|tornado_P2|clone_P2|barbarian_barrel_P2|heal_P2|giant_snowball_P2|strenght_P2_0|strenght_P2_1|strenght_P2_2|strenght_P2_3|strenght_P2_4|strenght_P2_5|strenght_P2_6|strenght_P2_7|
| :-------- |:--| :---------|:---------|:---------|:---------|:---------|:---------|:---------|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--| :---------|:---------|:---------|:---------|:---------|:---------|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|
|Ladder|0|1534421142|_Pourquoi_Que|Y28LVQG2|_Amnæs?a Un?ted|5267|1|0008004400020000020602|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|1|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|1|1|0|0|0|0|0|0|1|0|0|0|0|0|11|4|13|13|13|11|8|4|_??|22Q9U9VYL|_????|5238|0|08004010100202000440|0|0|0|1|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|1|0|0|0|0|0|0|0|1|0|0|0|0|1|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|0|11|13|11|11|13|13|11|13|
|Challenge|2|1534420740|_Pourquoi_Que|Y28LVQG2|_Amnæs?a Un?ted|5267|0|0000000061040600008002|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|1|1|0|0|0|1|0|0|0|0|0|0|1|1|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|0|1|0|0|0|0|0|1|1|7|1|9|1|4|1|_???|2LGCUCUCL|_AKARU??|4436|1|00000000780004044040|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|1|1|1|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|0|1|7|1|7|1|1|7|9|


## Dataset

A first dataset is constructed, 363152 battles and can be accessed [here](https://github.com/kekepins/clash-royal-analytics/blob/master/dataset/cr_data_1535999786739.zip)

