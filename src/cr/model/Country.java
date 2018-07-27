package cr.model;

public enum Country {
	
	_EU("Europe", false),
	_NA("North America", false),
	_AS("Asia", false),
	_AF("Africa", false),
	DE("Germany", true),
	FR("France", true),
	KR("South Korea", true),
	US("United States", true);
	
	
	private final String name;
	private final boolean isCountry;
	
	private Country(String name, boolean isCountry) {
		this.name = name;
		this.isCountry = isCountry;
	}

	public String getName() {
		return name;
	}

	public boolean isCountry() {
		return isCountry;
	}
	
	
}
