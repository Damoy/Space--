package world.tiles;

import graphics.Texture;
import toolbox.time.TickCounter;
import world.World;

public class GroundTile extends Tile {

	private TickCounter ticks;

	public GroundTile(World world, int row, int col) {
		super(world, "Ground", Texture.getRandomGroundTexture(), row, col, Tile.NORMAL);
	}

	@Override
	public void update() {
		ticks.increment();
		updateTexture();
	}

	private void updateTexture() {

	}

}
