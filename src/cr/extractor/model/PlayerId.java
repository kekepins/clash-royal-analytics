package cr.extractor.model;

public class PlayerId {
	private String name;
	private String tag;
	
	public PlayerId(String tag, String name) {
		this.tag = tag;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}
}
