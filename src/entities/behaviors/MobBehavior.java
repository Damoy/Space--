package entities.behaviors;

import entities.Player;

public abstract class MobBehavior {

	protected Player player;

	public MobBehavior(Player player) {
		if (player == null)
			throw new IllegalArgumentException("Player is null");
		this.player = player;
	}

	public abstract MobBehaviorBundle behave(int x, int y, byte dmg);
}
