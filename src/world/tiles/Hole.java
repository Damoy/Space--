package world.tiles;

import graphics.Texture;
import world.World;

public class Hole extends Tile {

	public Hole(World world, int row, int col) {
		super(world, "Hole", Texture.OBSTACLE_5_TEXTURE_8x8, row, col, Tile.BLOCKED);
	}

	@Override
	public void update() {

	}

}
