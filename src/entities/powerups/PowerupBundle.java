package entities.powerups;

import java.util.List;

public class PowerupBundle {

	private List<Object> bonuss;

	public PowerupBundle(List<Object> bonuss) {
		this.bonuss = bonuss;
	}

	public Object get(int index) {
		return bonuss.get(index);
	}

	public List<Object> getBonuss() {
		return bonuss;
	}
}
