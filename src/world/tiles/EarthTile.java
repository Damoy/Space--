package world.tiles;

import graphics.Texture;
import toolbox.data.GameInformation;
import toolbox.time.TickCounter;
import world.World;

public class EarthTile extends Tile {

	private TickCounter ticks;

	public EarthTile(World world, int row, int col) {
		super(world, GameInformation.EARTH_LABEL_TILE, Texture.GREEN_TERRAIN_8X8, row, col, Tile.NORMAL);
		ticks = new TickCounter();
	}

	@Override
	public void update() {
		ticks.increment();
		updateTexture();
	}

	private void updateTexture() {

	}

}
