package cr.analyse.spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

public class TopCardAndDeck {
	
	private static final String CR_DATA_FILE = "C:\\temp\\cr_data_1535565684337\\cr_data_1535565684337.csv";
	
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
		
		System.out.println(ds.count());
		
		
		/*Dataset<Row> dsWinners = ds
				.withColumn("deckid", when(col("winner").equalTo(lit(1)), col("deckid_P1")).otherwise(col("deckid_P2")) );
				*/ 
		
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
				when(col("winner").equalTo(lit(1)), col("goblin_hut_P1")).otherwise(col("goblin_hut_P2")) .as("goblin_hut"),
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
				when(col("winner").equalTo(lit(1)), col("giant_snowball_P1")).otherwise(col("giant_snowball_P2")) .as("giant_snowball")
						 
				);
			dsWinners.show();
			
			dsWinners.groupBy("deckid").agg(count("*").as("count")).orderBy(desc("count")).show();
		
	}

	
	private SparkSession initSparkSesion() {
		System.setProperty("hadoop.home.dir", "c:\\devtools\\hadoop-common-2.2.0-bin-master\\");
		
		return  SparkSession.builder().master("local[8]").appName("CRAnalyse")
				.config("spark.sql.warehouse.dir", "file:///C:/temp/working").getOrCreate();
	}


	private Dataset<Row> loadDataset(SparkSession spark) {
		return  spark.read().option("header", "true").option("delimiter", "^").csv(CR_DATA_FILE);
	}
	public static void main(String[] args) {
		TopCardAndDeck topCardAndDeck = new TopCardAndDeck();
		topCardAndDeck.doWork();
	}
}
