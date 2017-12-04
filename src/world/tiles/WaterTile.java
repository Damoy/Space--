package world.tiles;

import java.awt.image.BufferedImage;

import graphics.Texture;
import toolbox.data.GameInformation;
import toolbox.interfaces.TextureUpdatable;
import toolbox.time.TickCounter;
import world.World;

public class WaterTile extends Tile implements TextureUpdatable {

	private TickCounter ticks;
	private BufferedImage[] textures;
	private boolean timeToChangeTexture;

	public WaterTile(World world, int row, int col) {
		super(world, "Water", Texture.WATER_8X8, row, col, Tile.BLOCKED);
		ticks = new TickCounter();
		textures = new BufferedImage[] { Texture.WATER_8X8, Texture.WATER2_8X8 };
	}

	@Override
	public void update() {
		ticks.increment();
		updateTexture();
	}

	@Override
	public void updateTexture() {
		if (ticks.getTicks() > GameInformation.UPS) {
			ticks.reset();
			timeToChangeTexture = true;
			changeTexture();
		} else {
			timeToChangeTexture = false;
		}
	}

	private void changeTexture() {
		if (texture == textures[0])
			texture = textures[1];
		else if (texture == textures[1])
			texture = textures[0];
		else
			throw new IllegalStateException("Wrong texture");
	}

	@Override
	public boolean timeToChangeTexture() {
		return timeToChangeTexture;
	}

}
