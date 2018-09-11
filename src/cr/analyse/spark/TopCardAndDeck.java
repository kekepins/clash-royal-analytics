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
	
	private static final String CR_DATA_FILE = "C:\\temp\\cr_data_1536416383148\\cr_data_1536416383148.csv";
	//private static final String CR_DATA_FILE = "D:\\temp\\cr_data_1536416383148.csv";
	
	
	private void doWork() {
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
		//showTopDecks(ds);

		//showTopCards(ds);
		/*List<CardEnum> warCards =  Arrays.asList(
				//CardEnum.valkyrie, CardEnum.skeleton_army,
                CardEnum.skeleton_army,  CardEnum.skeletons, CardEnum.mini_pekka, CardEnum.fire_spirits, CardEnum.bomber,
                CardEnum.skeleton_barrel, CardEnum.minion_horde, CardEnum.royal_giant, CardEnum.tesla, CardEnum.inferno_dragon,
                CardEnum.witch,  CardEnum.giant, CardEnum.zap,  CardEnum.poison, CardEnum.heal, CardEnum.musketeer, CardEnum.zappies, CardEnum.wizard,
                CardEnum.rocket, 
                CardEnum.goblin_barrel, CardEnum.freeze, CardEnum.x_bow, CardEnum.lightning, CardEnum.bandit, CardEnum.sparky, CardEnum.mega_knight
                );*/
		
		/*List<CardEnum> warCards =  Arrays.asList(
				//CardEnum.valkyrie, CardEnum.skeleton_army,
				CardEnum.witch, CardEnum.tombstone, CardEnum.mini_pekka, CardEnum.valkyrie, 
                CardEnum.giant,  CardEnum.poison, CardEnum.minions, CardEnum.archers,
                CardEnum.ice_spirit, CardEnum.goblin_gang, CardEnum.cannon, CardEnum.arrows, CardEnum.barbarians,
                CardEnum.minion_horde,  CardEnum.royal_giant, CardEnum.dart_goblin, CardEnum.battle_ram, 
                CardEnum.flying_machine, CardEnum.barbarian_hut, CardEnum.rage,
                CardEnum.dark_prince,  CardEnum.freeze, CardEnum.cannon_cart, CardEnum.giant_skeleton, CardEnum.golem,
                CardEnum.miner, CardEnum.bandit, CardEnum.royal_ghost, CardEnum.lumberjack, CardEnum.mega_knight
                );*/
		
		List<CardEnum> warCards =  Arrays.asList(
				CardEnum.bomber, CardEnum.arrows, CardEnum.minions, CardEnum.cannon, 
                CardEnum.knight,  CardEnum.spear_goblins, CardEnum.goblins, CardEnum.tombstone, CardEnum.furnace,
                CardEnum.barbarian_hut, CardEnum.hog_rider, CardEnum.inferno_tower, CardEnum.heal, CardEnum.goblin_barrel,
                CardEnum.cannon_cart,  CardEnum.golem, CardEnum.prince, CardEnum.skeleton_army, 
                CardEnum.poison, CardEnum.inferno_dragon, CardEnum.lumberjack,
                CardEnum.magic_archer,  CardEnum.princess
                );
		
		getTopWarDayDeck(warCards, ds);
		
		/*List<CardEnum> warCards =  Arrays.asList(
                CardEnum.arrows,  CardEnum.ice_spirit, CardEnum.bats,  CardEnum.zap, CardEnum.minion_horde, CardEnum.spear_goblins,
                CardEnum.mega_minion, CardEnum.royal_hogs, CardEnum.battle_ram, CardEnum.hog_rider,
                CardEnum.wizard,  CardEnum.clone, CardEnum.prince,  CardEnum.giant_skeleton, CardEnum.mirror, CardEnum.barbarian_barrel, CardEnum.inferno_dragon, CardEnum.graveyard,
                CardEnum.lava_hound, CardEnum.magic_archer
                );
		getTopWarDayDeck2(warCards, ds);*/
		
		
		/*getTopDecksWitTheseCards2(
				Arrays.asList(
						CardEnum.furnace
						),
				ds);*/
		//return ds.select(cols);
		
		
		
	}

	
	
	private void getTopWarDayDeck(List<CardEnum> warCards, Dataset<Row> ds) {
		List<CardEnum> cardNotPossible = new ArrayList<>();
		for (CardEnum cardEnum : CardEnum.values()) {
			if ( !warCards.contains(cardEnum) ) {
				cardNotPossible.add(cardEnum);
			}
		}
		
		// First filter all deck without cards
		for (CardEnum cardEnum : cardNotPossible) {
			ds = ds.filter(	col(cardEnum.name()).equalTo(lit(0)) );
		}
		
		ds = ds.groupBy("deckid").agg(
			count("*").as("count"),
			sum(col("isWinner")).as("winCount"),
			first(col("human_deck")).as("human_deck")).orderBy(desc("count"));
		
		ds.show(false);
		
	}
	
	private void getTopDecksWitTheseCards(List<CardEnum> cards, Dataset<Row> ds) {
	
		// First filter all deck without cards
		for (CardEnum cardEnum : cards) {
			ds = ds.filter(
				col(cardEnum.name()).equalTo(lit(1)));
		}
		
		System.out.println("Filtered size: " + ds.count());
		
		
		ds = ds.groupBy("deckid").agg(
			count("*").as("count"),
			sum(col("isWinner")).as("winCount"),
			first(col("human_deck")).as("human_deck")).orderBy(desc("count"));;
		
		ds.show(false);

	}


	private void showTopCards(Dataset<Row> ds) {
		long count = ds.count();
		
		
		System.out.println("Total with refactor " + ds.count() );

		// Top cards
		// Sum cards
		List<Column> cols = new ArrayList<>();
		for ( CardEnum card : CardEnum.values()) {
			cols.add(sum(col(card.name())).as(card.name()));
			cols.add(sum(when(col("isWinner").equalTo(lit(1)), col(card.name()) )).as(card.name() + "win"));
			cols.add(sum(when(col("isWinner").equalTo(lit(0)), col(card.name()) )).as(card.name() + "lost"));
			cols.add( sum(col(card.name())).as(card.name()).divide(lit(count)).as(card.name() + "pct") );
		}
		
		ds = ds.select(cols.toArray(new Column[cols.size()]));
		
		for ( CardEnum card : CardEnum.values()) {
			ds = ds.withColumn(card.name() + "pctwin", col(card.name() + "win").divide(col(card.name())));
		}
		
		ds.show();
		
		Row row = ds.takeAsList(1).get(0);
		List<CardPlayStat> cardStats = new ArrayList<>();
		
		for ( CardEnum card : CardEnum.values() ) {
			
			cardStats.add( new CardPlayStat(
				card.name(),
				row.getDouble(row.fieldIndex(card.name())),
				row.getDouble(row.fieldIndex(card.name()+ "win")),
				row.getDouble(row.fieldIndex(card.name()+ "lost")),
				row.getDouble(row.fieldIndex(card.name()+ "pct")),
				row.getDouble(row.fieldIndex(card.name()+ "pctwin"))
					) );

		}
		
		Collections.sort(cardStats);
		
		for ( CardPlayStat cardStat : cardStats ) {
			System.out.println(cardStat.toString());
		}

	}
	
	private void showTopDecks(Dataset<Row> ds) {
		ds.groupBy(col("deckId")).agg(
				count("*").as("count"),
				first("human_deck"),
				sum(col("isWinner")))
		.orderBy(desc("count"))
		.show(false);
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
