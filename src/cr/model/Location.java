package cr.model;

public class Location {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCountry() {
		return isCountry;
	}
	public void setCountry(boolean isCountry) {
		this.isCountry = isCountry;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private boolean isCountry;
	private String code;
	
}
