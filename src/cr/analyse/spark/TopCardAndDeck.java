package cr.analyse.spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import cr.model.CardEnum;

import static org.apache.spark.sql.functions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopCardAndDeck {
	
	private static final String CR_DATA_FILE = "C:\\temp\\cr_data_1535999786739\\cr_data_1535999786739.csv";
	
	
	private void doWork2() {
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
	
		SparkSession spark = initSparkSesion();
		
		// load dataset as csv file
		Dataset<Row> ds = loadDataset(spark);
		
		// count data :
		System.out.println("Total " + ds.count() );
		
		// dataset with winners deck, elimate draws
		ds = ds.filter(col("winner").notEqual(lit(0)));
		
		
		System.out.println("Total without draw " + ds.count() );
		
		// refactor dataset
		// Winners union Loosers union draw
		Column[] colWinners = new Column[CardEnum.values().length + 3];
		Column[] colLoosers = new Column[CardEnum.values().length + 3];
		int idx = 0;
		for (CardEnum card : CardEnum.values() ) {
			colWinners[idx] = when(col("winner").equalTo(lit(1)), col(card.name() +  "_P1")).otherwise(col(card.name() + "_P2")) .as(card.name());
			colLoosers[idx] = when(col("winner").equalTo(lit(2)), col(card.name() +  "_P1")).otherwise(col(card.name() + "_P2")) .as(card.name());
			idx++;
		}
		
		colWinners[idx] = when(col("winner").equalTo(lit(1)), col("deckid_P1")).otherwise(col("deckid_P2")) .as("deckid");
		colLoosers[idx] = when(col("winner").equalTo(lit(2)), col("deckid_P1")).otherwise(col("deckid_P2")) .as("deckid");
		idx++;
		colWinners[idx] = when(col("winner").equalTo(lit(1)), col("human_deck_P1")).otherwise(col("human_deck_P2")) .as("human_deck");
		colLoosers[idx] = when(col("winner").equalTo(lit(2)), col("human_deck_P1")).otherwise(col("human_deck_P2")) .as("human_deck");
		idx++;
		colWinners[idx] = lit(1).as("isWinner");
		colLoosers[idx] = lit(0).as("isLooser");
		
		
		ds = 
			ds.select(colWinners).union(ds.select(colLoosers));
		
		ds.show(false);
		
		long count = ds.count();
		
		
		System.out.println("Total with refactor " + ds.count() );

		// Top cards
		// Sum cards
		List<Column> cols = new ArrayList<>();
		idx = 0;
		for ( CardEnum card : CardEnum.values()) {
			//cols[idx++] = sum(col(card.name())).as(card.name());
			cols.add(sum(col(card.name())).as(card.name()));
			cols.add(sum(when(col("isWinner").equalTo(lit(1)), col(card.name()) )).as(card.name() + "win"));
			cols.add(sum(when(col("isWinner").equalTo(lit(0)), col(card.name()) )).as(card.name() + "lost"));
			cols.add( sum(col(card.name())).as(card.name()).divide(lit(count)).as(card.name() + "pct") );
			
			
		}
		
		ds = ds.select(cols.toArray(new Column[cols.size()]));
		
		for ( CardEnum card : CardEnum.values()) {
			ds = ds.withColumn(card.name() + "pctwin", col(card.name() + "win").divide(lit(count)));
		}
		
		ds.show();
		//return ds.select(cols);
		
		
		
	}
	private void doWork() {
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
	
		SparkSession spark = initSparkSesion();
		
		// load dataset as csv file
		Dataset<Row> ds = loadDataset(spark);
		
		ds.show();
		
		// count data :
		System.out.println(ds.count());
		
		// dataset with winners deck
		ds = ds.filter(col("winner").notEqual(lit(0)));
		long count = ds.count();
		System.out.println("count :" + count);
		
		
		/*Dataset<Row> dsWinners = ds
				.withColumn("deckid", when(col("winner").equalTo(lit(1)), col("deckid_P1")).otherwise(col("deckid_P2")) );
				*/ 
		
		// winners dataset infos ..
		Dataset<Row> dsWinners = ds.select(
				col("utctime"),
				when(col("winner").equalTo(lit(1)), col("deckid_P1")).otherwise(col("deckid_P2")) .as("deckid"),
				when(col("winner").equalTo(lit(1)), col("knight_P1")).otherwise(col("knight_P2")) .as("knight"),
				when(col("winner").equalTo(lit(1)), col("archers_P1")).otherwise(col("archers_P2")) .as("archers"),
				when(col("winner").equalTo(lit(1)), col("goblins_P1")).otherwise(col("goblins_P2")) .as("goblins"),
				when(col("winner").equalTo(lit(1)), col("giant_P1")).otherwise(col("giant_P2")) .as("giant"),
				when(col("winner").equalTo(lit(1)), col("pekka_P1")).otherwise(col("pekka_P2")) .as("pekka"),
				when(col("winner").equalTo(lit(1)), col("minions_P1")).otherwise(col("minions_P2")) .as("minions"),
				when(col("winner").equalTo(lit(1)), col("balloon_P1")).otherwise(col("balloon_P2")) .as("balloon"),
				when(col("winner").equalTo(lit(1)), col("witch_P1")).otherwise(col("witch_P2")) .as("witch"),
				when(col("winner").equalTo(lit(1)), col("barbarians_P1")).otherwise(col("barbarians_P2")) .as("barbarians"),
				when(col("winner").equalTo(lit(1)), col("golem_P1")).otherwise(col("golem_P2")) .as("golem"),
				when(col("winner").equalTo(lit(1)), col("skeletons_P1")).otherwise(col("skeletons_P2")) .as("skeletons"),
				when(col("winner").equalTo(lit(1)), col("valkyrie_P1")).otherwise(col("valkyrie_P2")) .as("valkyrie"),
				when(col("winner").equalTo(lit(1)), col("skeleton_army_P1")).otherwise(col("skeleton_army_P2")) .as("skeleton_army"),
				when(col("winner").equalTo(lit(1)), col("bomber_P1")).otherwise(col("bomber_P2")) .as("bomber"),
				when(col("winner").equalTo(lit(1)), col("musketeer_P1")).otherwise(col("musketeer_P2")) .as("musketeer"),
				when(col("winner").equalTo(lit(1)), col("baby_dragon_P1")).otherwise(col("baby_dragon_P2")) .as("baby_dragon"),
				when(col("winner").equalTo(lit(1)), col("prince_P1")).otherwise(col("prince_P2")) .as("prince"),
				when(col("winner").equalTo(lit(1)), col("wizard_P1")).otherwise(col("wizard_P2")) .as("wizard"),
				when(col("winner").equalTo(lit(1)), col("mini_pekka_P1")).otherwise(col("mini_pekka_P2")) .as("mini_pekka"),
				when(col("winner").equalTo(lit(1)), col("spear_goblins_P1")).otherwise(col("spear_goblins_P2")) .as("spear_goblins"),
				when(col("winner").equalTo(lit(1)), col("giant_skeleton_P1")).otherwise(col("giant_skeleton_P2")) .as("giant_skeleton"),
				when(col("winner").equalTo(lit(1)), col("hog_rider_P1")).otherwise(col("hog_rider_P2")) .as("hog_rider"),
				when(col("winner").equalTo(lit(1)), col("minion_horde_P1")).otherwise(col("minion_horde_P2")) .as("minion_horde"),
				when(col("winner").equalTo(lit(1)), col("ice_wizard_P1")).otherwise(col("ice_wizard_P2")) .as("ice_wizard"),
				when(col("winner").equalTo(lit(1)), col("royal_giant_P1")).otherwise(col("royal_giant_P2")) .as("royal_giant"),
				when(col("winner").equalTo(lit(1)), col("guards_P1")).otherwise(col("guards_P2")) .as("guards"),
				when(col("winner").equalTo(lit(1)), col("princess_P1")).otherwise(col("princess_P2")) .as("princess"),
				when(col("winner").equalTo(lit(1)), col("dark_prince_P1")).otherwise(col("dark_prince_P2")) .as("dark_prince"),
				when(col("winner").equalTo(lit(1)), col("three_musketeers_P1")).otherwise(col("three_musketeers_P2")) .as("three_musketeers"),
				when(col("winner").equalTo(lit(1)), col("lava_hound_P1")).otherwise(col("lava_hound_P2")) .as("lava_hound"),
				when(col("winner").equalTo(lit(1)), col("ice_spirit_P1")).otherwise(col("ice_spirit_P2")) .as("ice_spirit"),
				when(col("winner").equalTo(lit(1)), col("fire_spirits_P1")).otherwise(col("fire_spirits_P2")) .as("fire_spirits"),
				when(col("winner").equalTo(lit(1)), col("miner_P1")).otherwise(col("miner_P2")) .as("miner"),
				when(col("winner").equalTo(lit(1)), col("sparky_P1")).otherwise(col("sparky_P2")) .as("sparky"),
				when(col("winner").equalTo(lit(1)), col("bowler_P1")).otherwise(col("bowler_P2")) .as("bowler"),
				when(col("winner").equalTo(lit(1)), col("lumberjack_P1")).otherwise(col("lumberjack_P2")) .as("lumberjack"),
				when(col("winner").equalTo(lit(1)), col("battle_ram_P1")).otherwise(col("battle_ram_P2")) .as("battle_ram"),
				when(col("winner").equalTo(lit(1)), col("inferno_dragon_P1")).otherwise(col("inferno_dragon_P2")) .as("inferno_dragon"),
				when(col("winner").equalTo(lit(1)), col("ice_golem_P1")).otherwise(col("ice_golem_P2")) .as("ice_golem"),
				when(col("winner").equalTo(lit(1)), col("mega_minion_P1")).otherwise(col("mega_minion_P2")) .as("mega_minion"),
				when(col("winner").equalTo(lit(1)), col("dart_goblin_P1")).otherwise(col("dart_goblin_P2")) .as("dart_goblin"),
				when(col("winner").equalTo(lit(1)), col("goblin_gang_P1")).otherwise(col("goblin_gang_P2")) .as("goblin_gang"),
				when(col("winner").equalTo(lit(1)), col("electro_wizard_P1")).otherwise(col("electro_wizard_P2")) .as("electro_wizard"),
				when(col("winner").equalTo(lit(1)), col("elite_barbarians_P1")).otherwise(col("elite_barbarians_P2")) .as("elite_barbarians"),
				when(col("winner").equalTo(lit(1)), col("hunter_P1")).otherwise(col("hunter_P2")) .as("hunter"),
				when(col("winner").equalTo(lit(1)), col("executioner_P1")).otherwise(col("executioner_P2")) .as("executioner"),
				when(col("winner").equalTo(lit(1)), col("bandit_P1")).otherwise(col("bandit_P2")) .as("bandit"),
				when(col("winner").equalTo(lit(1)), col("royal_recruits_P1")).otherwise(col("royal_recruits_P2")) .as("royal_recruits"),
				when(col("winner").equalTo(lit(1)), col("night_witch_P1")).otherwise(col("night_witch_P2")) .as("night_witch"),
				when(col("winner").equalTo(lit(1)), col("bats_P1")).otherwise(col("bats_P2")) .as("bats"),
				when(col("winner").equalTo(lit(1)), col("royal_ghost_P1")).otherwise(col("royal_ghost_P2")) .as("royal_ghost"),
				when(col("winner").equalTo(lit(1)), col("zappies_P1")).otherwise(col("zappies_P2")) .as("zappies"),
				when(col("winner").equalTo(lit(1)), col("rascals_P1")).otherwise(col("rascals_P2")) .as("rascals"),
				when(col("winner").equalTo(lit(1)), col("cannon_cart_P1")).otherwise(col("cannon_cart_P2")) .as("cannon_cart"),
				when(col("winner").equalTo(lit(1)), col("mega_knight_P1")).otherwise(col("mega_knight_P2")) .as("mega_knight"),
				when(col("winner").equalTo(lit(1)), col("skeleton_barrel_P1")).otherwise(col("skeleton_barrel_P2")) .as("skeleton_barrel"),
				when(col("winner").equalTo(lit(1)), col("flying_machine_P1")).otherwise(col("flying_machine_P2")) .as("flying_machine"),
				when(col("winner").equalTo(lit(1)), col("royal_hogs_P1")).otherwise(col("royal_hogs_P2")) .as("royal_hogs"),
				when(col("winner").equalTo(lit(1)), col("magic_archer_P1")).otherwise(col("magic_archer_P2")) .as("magic_archer"),
				when(col("winner").equalTo(lit(1)), col("cannon_P1")).otherwise(col("cannon_P2")) .as("cannon"),
				when(col("winner").equalTo(lit(1)), col("goblin_hut_P1")).otherwise(col("goblin_hut_P2")) .as("goblin_hut"),
				when(col("winner").equalTo(lit(1)), col("mortar_P1")).otherwise(col("mortar_P2")) .as("mortar"),
				when(col("winner").equalTo(lit(1)), col("inferno_tower_P1")).otherwise(col("inferno_tower_P2")) .as("inferno_tower"),
				when(col("winner").equalTo(lit(1)), col("bomb_tower_P1")).otherwise(col("bomb_tower_P2")) .as("bomb_tower"),
				when(col("winner").equalTo(lit(1)), col("barbarian_hut_P1")).otherwise(col("barbarian_hut_P2")) .as("barbarian_hut"),
				when(col("winner").equalTo(lit(1)), col("tesla_P1")).otherwise(col("tesla_P2")) .as("tesla"),
				when(col("winner").equalTo(lit(1)), col("elixir_collector_P1")).otherwise(col("elixir_collector_P2")) .as("elixir_collector"),
				when(col("winner").equalTo(lit(1)), col("x_bow_P1")).otherwise(col("x_bow_P2")) .as("x_bow"),
				when(col("winner").equalTo(lit(1)), col("tombstone_P1")).otherwise(col("tombstone_P2")) .as("tombstone"),
				when(col("winner").equalTo(lit(1)), col("furnace_P1")).otherwise(col("furnace_P2")) .as("furnace"),
				when(col("winner").equalTo(lit(1)), col("fireball_P1")).otherwise(col("fireball_P2")) .as("fireball"),
				when(col("winner").equalTo(lit(1)), col("arrows_P1")).otherwise(col("arrows_P2")) .as("arrows"),
				when(col("winner").equalTo(lit(1)), col("rage_P1")).otherwise(col("rage_P2")) .as("rage"),
				when(col("winner").equalTo(lit(1)), col("rocket_P1")).otherwise(col("rocket_P2")) .as("rocket"),
				when(col("winner").equalTo(lit(1)), col("goblin_barrel_P1")).otherwise(col("goblin_barrel_P2")) .as("goblin_barrel"),
				when(col("winner").equalTo(lit(1)), col("freeze_P1")).otherwise(col("freeze_P2")) .as("freeze"),
				when(col("winner").equalTo(lit(1)), col("mirror_P1")).otherwise(col("mirror_P2")) .as("mirror"),
				when(col("winner").equalTo(lit(1)), col("lightning_P1")).otherwise(col("lightning_P2")) .as("lightning"),
				when(col("winner").equalTo(lit(1)), col("zap_P1")).otherwise(col("zap_P2")) .as("zap"),
				when(col("winner").equalTo(lit(1)), col("poison_P1")).otherwise(col("poison_P2")) .as("poison"),
				when(col("winner").equalTo(lit(1)), col("graveyard_P1")).otherwise(col("graveyard_P2")) .as("graveyard"),
				when(col("winner").equalTo(lit(1)), col("the_log_P1")).otherwise(col("the_log_P2")) .as("the_log"),
				when(col("winner").equalTo(lit(1)), col("tornado_P1")).otherwise(col("tornado_P2")) .as("tornado"),
				when(col("winner").equalTo(lit(1)), col("clone_P1")).otherwise(col("clone_P2")) .as("clone"),
				when(col("winner").equalTo(lit(1)), col("barbarian_barrel_P1")).otherwise(col("barbarian_barrel_P2")) .as("barbarian_barrel"),
				when(col("winner").equalTo(lit(1)), col("heal_P1")).otherwise(col("heal_P2")) .as("heal"),
				when(col("winner").equalTo(lit(1)), col("giant_snowball_P1")).otherwise(col("giant_snowball_P2")) .as("giant_snowball"),
				when(col("winner").equalTo(lit(1)), col("human_deck_P1")).otherwise(col("human_deck_P2")) .as("human_deck")
						 
				);
		
		// winners dataset infos ..
		Dataset<Row> dsLoosers = ds.select(
				col("utctime"),
				when(col("winner").equalTo(lit(0)), col("deckid_P1")).otherwise(col("deckid_P2")) .as("deckid"),
				when(col("winner").equalTo(lit(0)), col("knight_P1")).otherwise(col("knight_P2")) .as("knight"),
				when(col("winner").equalTo(lit(0)), col("archers_P1")).otherwise(col("archers_P2")) .as("archers"),
				when(col("winner").equalTo(lit(0)), col("goblins_P1")).otherwise(col("goblins_P2")) .as("goblins"),
				when(col("winner").equalTo(lit(0)), col("giant_P1")).otherwise(col("giant_P2")) .as("giant"),
				when(col("winner").equalTo(lit(0)), col("pekka_P1")).otherwise(col("pekka_P2")) .as("pekka"),
				when(col("winner").equalTo(lit(0)), col("minions_P1")).otherwise(col("minions_P2")) .as("minions"),
				when(col("winner").equalTo(lit(0)), col("balloon_P1")).otherwise(col("balloon_P2")) .as("balloon"),
				when(col("winner").equalTo(lit(0)), col("witch_P1")).otherwise(col("witch_P2")) .as("witch"),
				when(col("winner").equalTo(lit(0)), col("barbarians_P1")).otherwise(col("barbarians_P2")) .as("barbarians"),
				when(col("winner").equalTo(lit(0)), col("golem_P1")).otherwise(col("golem_P2")) .as("golem"),
				when(col("winner").equalTo(lit(0)), col("skeletons_P1")).otherwise(col("skeletons_P2")) .as("skeletons"),
				when(col("winner").equalTo(lit(0)), col("valkyrie_P1")).otherwise(col("valkyrie_P2")) .as("valkyrie"),
				when(col("winner").equalTo(lit(0)), col("skeleton_army_P1")).otherwise(col("skeleton_army_P2")) .as("skeleton_army"),
				when(col("winner").equalTo(lit(0)), col("bomber_P1")).otherwise(col("bomber_P2")) .as("bomber"),
				when(col("winner").equalTo(lit(0)), col("musketeer_P1")).otherwise(col("musketeer_P2")) .as("musketeer"),
				when(col("winner").equalTo(lit(0)), col("baby_dragon_P1")).otherwise(col("baby_dragon_P2")) .as("baby_dragon"),
				when(col("winner").equalTo(lit(0)), col("prince_P1")).otherwise(col("prince_P2")) .as("prince"),
				when(col("winner").equalTo(lit(0)), col("wizard_P1")).otherwise(col("wizard_P2")) .as("wizard"),
				when(col("winner").equalTo(lit(0)), col("mini_pekka_P1")).otherwise(col("mini_pekka_P2")) .as("mini_pekka"),
				when(col("winner").equalTo(lit(0)), col("spear_goblins_P1")).otherwise(col("spear_goblins_P2")) .as("spear_goblins"),
				when(col("winner").equalTo(lit(0)), col("giant_skeleton_P1")).otherwise(col("giant_skeleton_P2")) .as("giant_skeleton"),
				when(col("winner").equalTo(lit(0)), col("hog_rider_P1")).otherwise(col("hog_rider_P2")) .as("hog_rider"),
				when(col("winner").equalTo(lit(0)), col("minion_horde_P1")).otherwise(col("minion_horde_P2")) .as("minion_horde"),
				when(col("winner").equalTo(lit(0)), col("ice_wizard_P1")).otherwise(col("ice_wizard_P2")) .as("ice_wizard"),
				when(col("winner").equalTo(lit(0)), col("royal_giant_P1")).otherwise(col("royal_giant_P2")) .as("royal_giant"),
				when(col("winner").equalTo(lit(0)), col("guards_P1")).otherwise(col("guards_P2")) .as("guards"),
				when(col("winner").equalTo(lit(0)), col("princess_P1")).otherwise(col("princess_P2")) .as("princess"),
				when(col("winner").equalTo(lit(0)), col("dark_prince_P1")).otherwise(col("dark_prince_P2")) .as("dark_prince"),
				when(col("winner").equalTo(lit(0)), col("three_musketeers_P1")).otherwise(col("three_musketeers_P2")) .as("three_musketeers"),
				when(col("winner").equalTo(lit(0)), col("lava_hound_P1")).otherwise(col("lava_hound_P2")) .as("lava_hound"),
				when(col("winner").equalTo(lit(0)), col("ice_spirit_P1")).otherwise(col("ice_spirit_P2")) .as("ice_spirit"),
				when(col("winner").equalTo(lit(0)), col("fire_spirits_P1")).otherwise(col("fire_spirits_P2")) .as("fire_spirits"),
				when(col("winner").equalTo(lit(0)), col("miner_P1")).otherwise(col("miner_P2")) .as("miner"),
				when(col("winner").equalTo(lit(0)), col("sparky_P1")).otherwise(col("sparky_P2")) .as("sparky"),
				when(col("winner").equalTo(lit(0)), col("bowler_P1")).otherwise(col("bowler_P2")) .as("bowler"),
				when(col("winner").equalTo(lit(0)), col("lumberjack_P1")).otherwise(col("lumberjack_P2")) .as("lumberjack"),
				when(col("winner").equalTo(lit(0)), col("battle_ram_P1")).otherwise(col("battle_ram_P2")) .as("battle_ram"),
				when(col("winner").equalTo(lit(0)), col("inferno_dragon_P1")).otherwise(col("inferno_dragon_P2")) .as("inferno_dragon"),
				when(col("winner").equalTo(lit(0)), col("ice_golem_P1")).otherwise(col("ice_golem_P2")) .as("ice_golem"),
				when(col("winner").equalTo(lit(0)), col("mega_minion_P1")).otherwise(col("mega_minion_P2")) .as("mega_minion"),
				when(col("winner").equalTo(lit(0)), col("dart_goblin_P1")).otherwise(col("dart_goblin_P2")) .as("dart_goblin"),
				when(col("winner").equalTo(lit(0)), col("goblin_gang_P1")).otherwise(col("goblin_gang_P2")) .as("goblin_gang"),
				when(col("winner").equalTo(lit(0)), col("electro_wizard_P1")).otherwise(col("electro_wizard_P2")) .as("electro_wizard"),
				when(col("winner").equalTo(lit(0)), col("elite_barbarians_P1")).otherwise(col("elite_barbarians_P2")) .as("elite_barbarians"),
				when(col("winner").equalTo(lit(0)), col("hunter_P1")).otherwise(col("hunter_P2")) .as("hunter"),
				when(col("winner").equalTo(lit(0)), col("executioner_P1")).otherwise(col("executioner_P2")) .as("executioner"),
				when(col("winner").equalTo(lit(0)), col("bandit_P1")).otherwise(col("bandit_P2")) .as("bandit"),
				when(col("winner").equalTo(lit(0)), col("royal_recruits_P1")).otherwise(col("royal_recruits_P2")) .as("royal_recruits"),
				when(col("winner").equalTo(lit(0)), col("night_witch_P1")).otherwise(col("night_witch_P2")) .as("night_witch"),
				when(col("winner").equalTo(lit(0)), col("bats_P1")).otherwise(col("bats_P2")) .as("bats"),
				when(col("winner").equalTo(lit(0)), col("royal_ghost_P1")).otherwise(col("royal_ghost_P2")) .as("royal_ghost"),
				when(col("winner").equalTo(lit(0)), col("zappies_P1")).otherwise(col("zappies_P2")) .as("zappies"),
				when(col("winner").equalTo(lit(0)), col("rascals_P1")).otherwise(col("rascals_P2")) .as("rascals"),
				when(col("winner").equalTo(lit(0)), col("cannon_cart_P1")).otherwise(col("cannon_cart_P2")) .as("cannon_cart"),
				when(col("winner").equalTo(lit(0)), col("mega_knight_P1")).otherwise(col("mega_knight_P2")) .as("mega_knight"),
				when(col("winner").equalTo(lit(0)), col("skeleton_barrel_P1")).otherwise(col("skeleton_barrel_P2")) .as("skeleton_barrel"),
				when(col("winner").equalTo(lit(0)), col("flying_machine_P1")).otherwise(col("flying_machine_P2")) .as("flying_machine"),
				when(col("winner").equalTo(lit(0)), col("royal_hogs_P1")).otherwise(col("royal_hogs_P2")) .as("royal_hogs"),
				when(col("winner").equalTo(lit(0)), col("magic_archer_P1")).otherwise(col("magic_archer_P2")) .as("magic_archer"),
				when(col("winner").equalTo(lit(0)), col("cannon_P1")).otherwise(col("cannon_P2")) .as("cannon"),
				when(col("winner").equalTo(lit(0)), col("goblin_hut_P1")).otherwise(col("goblin_hut_P2")) .as("goblin_hut"),
				when(col("winner").equalTo(lit(0)), col("mortar_P1")).otherwise(col("mortar_P2")) .as("mortar"),
				when(col("winner").equalTo(lit(0)), col("inferno_tower_P1")).otherwise(col("inferno_tower_P2")) .as("inferno_tower"),
				when(col("winner").equalTo(lit(0)), col("bomb_tower_P1")).otherwise(col("bomb_tower_P2")) .as("bomb_tower"),
				when(col("winner").equalTo(lit(0)), col("barbarian_hut_P1")).otherwise(col("barbarian_hut_P2")) .as("barbarian_hut"),
				when(col("winner").equalTo(lit(0)), col("tesla_P1")).otherwise(col("tesla_P2")) .as("tesla"),
				when(col("winner").equalTo(lit(0)), col("elixir_collector_P1")).otherwise(col("elixir_collector_P2")) .as("elixir_collector"),
				when(col("winner").equalTo(lit(0)), col("x_bow_P1")).otherwise(col("x_bow_P2")) .as("x_bow"),
				when(col("winner").equalTo(lit(0)), col("tombstone_P1")).otherwise(col("tombstone_P2")) .as("tombstone"),
				when(col("winner").equalTo(lit(0)), col("furnace_P1")).otherwise(col("furnace_P2")) .as("furnace"),
				when(col("winner").equalTo(lit(0)), col("fireball_P1")).otherwise(col("fireball_P2")) .as("fireball"),
				when(col("winner").equalTo(lit(0)), col("arrows_P1")).otherwise(col("arrows_P2")) .as("arrows"),
				when(col("winner").equalTo(lit(0)), col("rage_P1")).otherwise(col("rage_P2")) .as("rage"),
				when(col("winner").equalTo(lit(0)), col("rocket_P1")).otherwise(col("rocket_P2")) .as("rocket"),
				when(col("winner").equalTo(lit(0)), col("goblin_barrel_P1")).otherwise(col("goblin_barrel_P2")) .as("goblin_barrel"),
				when(col("winner").equalTo(lit(0)), col("freeze_P1")).otherwise(col("freeze_P2")) .as("freeze"),
				when(col("winner").equalTo(lit(0)), col("mirror_P1")).otherwise(col("mirror_P2")) .as("mirror"),
				when(col("winner").equalTo(lit(0)), col("lightning_P1")).otherwise(col("lightning_P2")) .as("lightning"),
				when(col("winner").equalTo(lit(0)), col("zap_P1")).otherwise(col("zap_P2")) .as("zap"),
				when(col("winner").equalTo(lit(0)), col("poison_P1")).otherwise(col("poison_P2")) .as("poison"),
				when(col("winner").equalTo(lit(0)), col("graveyard_P1")).otherwise(col("graveyard_P2")) .as("graveyard"),
				when(col("winner").equalTo(lit(0)), col("the_log_P1")).otherwise(col("the_log_P2")) .as("the_log"),
				when(col("winner").equalTo(lit(0)), col("tornado_P1")).otherwise(col("tornado_P2")) .as("tornado"),
				when(col("winner").equalTo(lit(0)), col("clone_P1")).otherwise(col("clone_P2")) .as("clone"),
				when(col("winner").equalTo(lit(0)), col("barbarian_barrel_P1")).otherwise(col("barbarian_barrel_P2")) .as("barbarian_barrel"),
				when(col("winner").equalTo(lit(0)), col("heal_P1")).otherwise(col("heal_P2")) .as("heal"),
				when(col("winner").equalTo(lit(0)), col("giant_snowball_P1")).otherwise(col("giant_snowball_P2")) .as("giant_snowball"),
				when(col("winner").equalTo(lit(0)), col("human_deck_P1")).otherwise(col("human_deck_P2")) .as("human_deck")
						 
				);
		
		
		showTopCards(dsWinners, dsLoosers);
		
		//showTopCards(dsLoosers);
		
		/*List<CardEnum> deck =  Arrays.asList(
                CardEnum.arrows,  CardEnum.ice_spirit, CardEnum.bats,  CardEnum.zap, CardEnum.minion_horde, CardEnum.spear_goblins,
                CardEnum.mega_minion, CardEnum.royal_hogs, CardEnum.battle_ram, CardEnum.hog_rider,
                CardEnum.wizard,  CardEnum.clone, CardEnum.prince,  CardEnum.giant_skeleton, CardEnum.mirror, CardEnum.barbarian_barrel, CardEnum.inferno_dragon, CardEnum.graveyard,
                CardEnum.lava_hound, CardEnum.magic_archer
                );
                */
        /*List<CardEnum> deck =  Arrays.asList(
                        CardEnum.arrows,  CardEnum.archers, CardEnum.knight, CardEnum.fire_spirits,
                        CardEnum.elite_barbarians,  CardEnum.goblins, CardEnum.barbarians, CardEnum.spear_goblins,
                        CardEnum.musketeer,  CardEnum.mega_minion, CardEnum.heal, CardEnum.valkyrie,
                        CardEnum.fireball, CardEnum.elixir_collector, CardEnum.three_musketeers, CardEnum.executioner,
                        CardEnum.lightning,  CardEnum.tornado, CardEnum.dark_prince,  CardEnum.skeleton_army, CardEnum.freeze, 
                        CardEnum.poison, CardEnum.sparky, CardEnum.ice_wizard,
                        CardEnum.magic_archer, CardEnum.the_log
                        );                
			
		getTopWarDayDeck(deck,   dsWinners);
		
		getTopWarDayDeck(deck,  dsLoosers);
		*/

		
		/*getTopDecksWitTheseCards(
				Arrays.asList(
						CardEnum.furnace
						),
				dsWinners);
				*/
		/*getTopWarDayDeck(
				Arrays.asList(
						CardEnum.arrows,  CardEnum.archers,  CardEnum.knight, CardEnum.fire_spirits, CardEnum.goblins,
						CardEnum.minion_horde, CardEnum.barbarians,  CardEnum.rocket, CardEnum.mega_minion, CardEnum.ice_golem,
						CardEnum.goblin_hut, CardEnum.furnace,  CardEnum.valkyrie, CardEnum.goblin_barrel, CardEnum.pekka,CardEnum.lightning,
						CardEnum.balloon, CardEnum.skeleton_army,  CardEnum.freeze, CardEnum.inferno_dragon, CardEnum.electro_wizard,
						CardEnum.hunter, CardEnum.sparky
						),
				dsWinners);*/
		
		/*getTopWarDayDeck(
				Arrays.asList(
						CardEnum.ice_spirit, CardEnum.zap, CardEnum.skeleton_barrel, CardEnum.minion_horde, CardEnum.tesla,  
						CardEnum.bats, CardEnum.royal_giant,
						CardEnum.knight,  CardEnum.fireball, CardEnum.mini_pekka, CardEnum.royal_hogs,
						CardEnum.dart_goblin, CardEnum.wizard,  CardEnum.giant, CardEnum.flying_machine, CardEnum.golem,
						CardEnum.prince, CardEnum.giant_skeleton,  CardEnum.dark_prince, CardEnum.mirror, CardEnum.x_bow,
						CardEnum.poison, CardEnum.bandit,  CardEnum.royal_ghost, CardEnum.lava_hound, CardEnum.mega_knight, CardEnum.the_log
						),
				dsWinners);*/
			
		
	}
	
	private void getTopDecksWitTheseCards(List<CardEnum> cards, Dataset<Row> dsWinners) {
		
		
		// First filter all deck without cards
		for (CardEnum cardEnum : cards) {
			dsWinners = dsWinners.filter(
				col(cardEnum.name()).equalTo(lit(1)));
		}
		
		System.out.println("Filtered size: " + dsWinners.count());
		
		// now show top decks on this short list
		showTopDecks(dsWinners);
		
	}
	
	
	private void getTopWarDayDeck(List<CardEnum> warCards, Dataset<Row> dsWinners) {
		
		List<CardEnum> cardNotPossible = new ArrayList<>();
		for (CardEnum cardEnum : CardEnum.values()) {
			if ( !warCards.contains(cardEnum) ) {
				cardNotPossible.add(cardEnum);
			}
		}
		
		// First filter all deck without cards
		for (CardEnum cardEnum : cardNotPossible) {
			dsWinners = dsWinners.filter(
				col(cardEnum.name()).equalTo(lit(0)));
		}
		
		System.out.println("Filtered size: " + dsWinners.count());
		
		// now show top decks on this short list
		showTopDecks(dsWinners);
		
		
		
	}
	
	private void showTopDecks(Dataset<Row> dsWinners) {
		Dataset<Row> mostPlayed = 
				dsWinners.groupBy("deckid")
				.agg(
					count("*").as("count"),
					first(col("human_deck")).as("human_deck")
					/*first(col("knight")).as("knight"),
					first(col("archers")).as("archers"),
					first(col("goblins")).as("goblins"),
					first(col("giant")).as("giant"),
					first(col("pekka")).as("pekka"),
					first(col("minions")).as("minions"),
					first(col("balloon")).as("balloon"),
					first(col("witch")).as("witch"),
					first(col("barbarians")).as("barbarians"),
					first(col("golem")).as("golem"),
					first(col("skeletons")).as("skeletons"),
					first(col("valkyrie")).as("valkyrie"),
					first(col("skeleton_army")).as("skeleton_army"),
					first(col("bomber")).as("bomber"),
					first(col("musketeer")).as("musketeer"),
					first(col("baby_dragon")).as("baby_dragon"),
					first(col("prince")).as("prince"),
					first(col("wizard")).as("wizard"),
					first(col("mini_pekka")).as("mini_pekka"),
					first(col("spear_goblins")).as("spear_goblins"),
					first(col("giant_skeleton")).as("giant_skeleton"),
					first(col("hog_rider")).as("hog_rider"),
					first(col("minion_horde")).as("minion_horde"),
					first(col("ice_wizard")).as("ice_wizard"),
					first(col("royal_giant")).as("royal_giant"),
					first(col("guards")).as("guards"),
					first(col("princess")).as("princess"),
					first(col("dark_prince")).as("dark_prince"),
					first(col("three_musketeers")).as("three_musketeers"),
					first(col("lava_hound")).as("lava_hound"),
					first(col("ice_spirit")).as("ice_spirit"),
					first(col("fire_spirits")).as("fire_spirits"),
					first(col("miner")).as("miner"),
					first(col("sparky")).as("sparky"),
					first(col("bowler")).as("bowler"),
					first(col("lumberjack")).as("lumberjack"),
					first(col("battle_ram")).as("battle_ram"),
					first(col("inferno_dragon")).as("inferno_dragon"),
					first(col("ice_golem")).as("ice_golem"),
					first(col("mega_minion")).as("mega_minion"),
					first(col("dart_goblin")).as("dart_goblin"),
					first(col("goblin_gang")).as("goblin_gang"),
					first(col("electro_wizard")).as("electro_wizard"),
					first(col("elite_barbarians")).as("elite_barbarians"),
					first(col("hunter")).as("hunter"),
					first(col("executioner")).as("executioner"),
					first(col("bandit")).as("bandit"),
					first(col("royal_recruits")).as("royal_recruits"),
					first(col("night_witch")).as("night_witch"),
					first(col("bats")).as("bats"),
					first(col("royal_ghost")).as("royal_ghost"),
					first(col("zappies")).as("zappies"),
					first(col("rascals")).as("rascals"),
					first(col("cannon_cart")).as("cannon_cart"),
					first(col("mega_knight")).as("mega_knight"),
					first(col("skeleton_barrel")).as("skeleton_barrel"),
					first(col("flying_machine")).as("flying_machine"),
					first(col("royal_hogs")).as("royal_hogs"),
					first(col("magic_archer")).as("magic_archer"),
					first(col("cannon")).as("cannon"),
					first(col("goblin_hut")).as("goblin_hut"),
					first(col("mortar")).as("mortar"),
					first(col("inferno_tower")).as("inferno_tower"),
					first(col("bomb_tower")).as("bomb_tower"),
					first(col("barbarian_hut")).as("barbarian_hut"),
					first(col("tesla")).as("tesla"),
					first(col("elixir_collector")).as("elixir_collector"),
					first(col("x_bow")).as("x_bow"),
					first(col("tombstone")).as("tombstone"),
					first(col("furnace")).as("furnace"),
					first(col("fireball")).as("fireball"),
					first(col("arrows")).as("arrows"),
					first(col("rage")).as("rage"),
					first(col("rocket")).as("rocket"),
					first(col("goblin_barrel")).as("goblin_barrel"),
					first(col("freeze")).as("freeze"),
					first(col("mirror")).as("mirror"),
					first(col("lightning")).as("lightning"),
					first(col("zap")).as("zap"),
					first(col("poison")).as("poison"),
					first(col("graveyard")).as("graveyard"),
					first(col("the_log")).as("the_log"),
					first(col("tornado")).as("tornado"),
					first(col("clone")).as("clone"),
					first(col("barbarian_barrel")).as("barbarian_barrel"),
					first(col("heal")).as("heal"),
					first(col("giant_snowball")).as("giant_snowball")*/
					)
				.orderBy(desc("count"));
		
		mostPlayed.show(false);
		System.out.println("most:" + mostPlayed.count() );

	}
	
	private void showTopCards(Dataset<Row> dsWinners, Dataset<Row> dsLoosers) {
		
		long countWinners = dsWinners.count(); 
		long countLoosers = dsLoosers.count();
		
		Dataset<Row> sumCardsWin = sumCards(dsWinners);  
		Dataset<Row> sumCardsLoose = sumCards(dsLoosers);
		
		/*Dataset<Row> sumCards = dsWinners.select(
				sum(col("knight")).as("knight"),
				sum(col("archers")).as("archers"),
				sum(col("goblins")).as("goblins"),
				sum(col("giant")).as("giant"),
				sum(col("pekka")).as("pekka"),
				sum(col("minions")).as("minions"),
				sum(col("balloon")).as("balloon"),
				sum(col("witch")).as("witch"),
				sum(col("barbarians")).as("barbarians"),
				sum(col("golem")).as("golem"),
				sum(col("skeletons")).as("skeletons"),
				sum(col("valkyrie")).as("valkyrie"),
				sum(col("skeleton_army")).as("skeleton_army"),
				sum(col("bomber")).as("bomber"),
				sum(col("musketeer")).as("musketeer"),
				sum(col("baby_dragon")).as("baby_dragon"),
				sum(col("prince")).as("prince"),
				sum(col("wizard")).as("wizard"),
				sum(col("mini_pekka")).as("mini_pekka"),
				sum(col("spear_goblins")).as("spear_goblins"),
				sum(col("giant_skeleton")).as("giant_skeleton"),
				sum(col("hog_rider")).as("hog_rider"),
				sum(col("minion_horde")).as("minion_horde"),
				sum(col("ice_wizard")).as("ice_wizard"),
				sum(col("royal_giant")).as("royal_giant"),
				sum(col("guards")).as("guards"),
				sum(col("princess")).as("princess"),
				sum(col("dark_prince")).as("dark_prince"),
				sum(col("three_musketeers")).as("three_musketeers"),
				sum(col("lava_hound")).as("lava_hound"),
				sum(col("ice_spirit")).as("ice_spirit"),
				sum(col("fire_spirits")).as("fire_spirits"),
				sum(col("miner")).as("miner"),
				sum(col("sparky")).as("sparky"),
				sum(col("bowler")).as("bowler"),
				sum(col("lumberjack")).as("lumberjack"),
				sum(col("battle_ram")).as("battle_ram"),
				sum(col("inferno_dragon")).as("inferno_dragon"),
				sum(col("ice_golem")).as("ice_golem"),
				sum(col("mega_minion")).as("mega_minion"),
				sum(col("dart_goblin")).as("dart_goblin"),
				sum(col("goblin_gang")).as("goblin_gang"),
				sum(col("electro_wizard")).as("electro_wizard"),
				sum(col("elite_barbarians")).as("elite_barbarians"),
				sum(col("hunter")).as("hunter"),
				sum(col("executioner")).as("executioner"),
				sum(col("bandit")).as("bandit"),
				sum(col("royal_recruits")).as("royal_recruits"),
				sum(col("night_witch")).as("night_witch"),
				sum(col("bats")).as("bats"),
				sum(col("royal_ghost")).as("royal_ghost"),
				sum(col("zappies")).as("zappies"),
				sum(col("rascals")).as("rascals"),
				sum(col("cannon_cart")).as("cannon_cart"),
				sum(col("mega_knight")).as("mega_knight"),
				sum(col("skeleton_barrel")).as("skeleton_barrel"),
				sum(col("flying_machine")).as("flying_machine"),
				sum(col("royal_hogs")).as("royal_hogs"),
				sum(col("magic_archer")).as("magic_archer"),
				sum(col("cannon")).as("cannon"),
				sum(col("goblin_hut")).as("goblin_hut"),
				sum(col("mortar")).as("mortar"),
				sum(col("inferno_tower")).as("inferno_tower"),
				sum(col("bomb_tower")).as("bomb_tower"),
				sum(col("barbarian_hut")).as("barbarian_hut"),
				sum(col("tesla")).as("tesla"),
				sum(col("elixir_collector")).as("elixir_collector"),
				sum(col("x_bow")).as("x_bow"),
				sum(col("tombstone")).as("tombstone"),
				sum(col("furnace")).as("furnace"),
				sum(col("fireball")).as("fireball"),
				sum(col("arrows")).as("arrows"),
				sum(col("rage")).as("rage"),
				sum(col("rocket")).as("rocket"),
				sum(col("goblin_barrel")).as("goblin_barrel"),
				sum(col("freeze")).as("freeze"),
				sum(col("mirror")).as("mirror"),
				sum(col("lightning")).as("lightning"),
				sum(col("zap")).as("zap"),
				sum(col("poison")).as("poison"),
				sum(col("graveyard")).as("graveyard"),
				sum(col("the_log")).as("the_log"),
				sum(col("tornado")).as("tornado"),
				sum(col("clone")).as("clone"),
				sum(col("barbarian_barrel")).as("barbarian_barrel"),
				sum(col("heal")).as("heal"),
				sum(col("giant_snowball")).as("giant_snowball")
				);
		
		sumCards.show();*/
		
		String[] columns = sumCardsWin.columns();
		List<CardCount> cards = new ArrayList<>();
		Map<String, CardCount> cardsMap = new HashMap<>();
		
		Row row = sumCardsWin.takeAsList(1).get(0);
		for ( int i = 0; i < row.size(); i++) {
			CardCount card = new CardCount(columns[i], row.getDouble(i));
			cards.add(card);
			cardsMap.put(columns[i], card);
		}
		
		row = sumCardsLoose.takeAsList(1).get(0);
		for ( int i = 0; i < row.size(); i++) {
			CardCount card = cardsMap.get(columns[i]);
			card.setLooseCount(row.getDouble(i));
		}
		
		
		
		Collections.sort(cards);
		
		for ( CardCount cardCount : cards ) {
			System.out.println(cardCount.toString(countWinners));
		}
	}

	
	private SparkSession initSparkSesion() {
		System.setProperty("hadoop.home.dir", "c:\\devtools\\hadoop-common-2.2.0-bin-master\\");
		
		return  SparkSession.builder().master("local[8]").appName("CRAnalyse")
				.config("spark.sql.warehouse.dir", "file:///C:/temp/working").getOrCreate();
	}


	private Dataset<Row> loadDataset(SparkSession spark) {
		return  spark.read().option("header", "true").option("delimiter", "^").csv(CR_DATA_FILE);
	}
	
	private Dataset<Row> sumCards(Dataset<Row> ds ) {
		
		Column[] cols = new Column[CardEnum.values().length];
		int idx = 0;
		for ( CardEnum card : CardEnum.values()) {
			cols[idx++] = sum(col(card.name())).as(card.name());
		}
		
		return ds.select(cols);
		
	}
	public static void main(String[] args) {
		TopCardAndDeck topCardAndDeck = new TopCardAndDeck();
		topCardAndDeck.doWork2();
	}
	
	 
}
