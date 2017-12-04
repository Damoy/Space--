package world.tiles;

import graphics.Texture;
import toolbox.time.TickCounter;
import world.World;

public class ObstacleTile1 extends Tile {

	private TickCounter ticks;

	public ObstacleTile1(World world, int row, int col) {
		super(world, "Obstacle", Texture.getRandomObstacleTexture(), row, col, Tile.BLOCKED);
	}

	@Override
	public void update() {
		ticks.increment();
		updateTexture();
	}

	private void updateTexture() {

	}

}
