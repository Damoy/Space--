package entities.powerups;

import java.util.ArrayList;
import java.util.List;

import graphics.Texture;
import world.World;

public class Ammo extends Powerup {

	public Ammo(World world, int row, int col) {
		super(world, Texture.AMMO_6X6, row, col);
	}

	@Override
	public PowerupBundle interact() {
		if (!alive)
			return null;
		alive = false;
		List<Object> bonuss = new ArrayList<>();
		bonuss.add(getValue());
		return new PowerupBundle(bonuss);
	}

	@Override
	public byte getValue() {
		if (!alive)
			return (byte) 0;
		return (byte) 1;
	}

}
