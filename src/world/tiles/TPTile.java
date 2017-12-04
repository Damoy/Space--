package world.tiles;

import graphics.Texture;
import toolbox.time.TickCounter;
import world.World;

public class TPTile extends Tile {

	private TickCounter ticks;

	public TPTile(World world, int row, int col) {
		super(world, "Teleport", Texture.TP_8X8, row, col, Tile.NORMAL);
	}

	@Override
	public void update() {
		ticks.increment();
		updateTexture();
	}

	private void updateTexture() {

	}

}
