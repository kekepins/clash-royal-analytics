package cr.model;

public class Card {
	//private String name;
	private CardEnum cardEnum;
	
	private String key;
	
	private int level;
	private int maxLevel;
	private int count;
	private String rarity;
	//private int requiredForUpgrade;
	private int leftToUpgrade;
	private String icon;
	private String type;
	private int arena;
	private String description;
	private long id;
	public CardEnum getCardEnum() {
		return cardEnum;
	}
	public void setCardEnum(CardEnum cardEnum) {
		this.cardEnum = cardEnum;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	/*public int getRequiredForUpgrade() {
		return requiredForUpgrade;
	}
	public void setRequiredForUpgrade(int requiredForUpgrade) {
		this.requiredForUpgrade = requiredForUpgrade;
	}*/
	public int getLeftToUpgrade() {
		return leftToUpgrade;
	}
	public void setLeftToUpgrade(int leftToUpgrade) {
		this.leftToUpgrade = leftToUpgrade;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
		
		this.cardEnum = CardEnum.fromKey(key);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getArena() {
		return arena;
	}
	public void setArena(int arena) {
		this.arena = arena;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
