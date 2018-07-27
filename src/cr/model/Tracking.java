package cr.model;

public class Tracking {
	private String tag; 
	private boolean active;
	private boolean avalaible;
	private int snapshotCount;
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isAvalaible() {
		return avalaible;
	}
	public void setAvalaible(boolean avalaible) {
		this.avalaible = avalaible;
	}
	public int getSnapshotCount() {
		return snapshotCount;
	}
	public void setSnapshotCount(int snapshotCount) {
		this.snapshotCount = snapshotCount;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

}
