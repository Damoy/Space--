package entities.powerups;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.Entity;
import graphics.BasicRenderer;
import world.World;

public abstract class Powerup extends Entity {

	protected float xWorld;
	protected float yWorld;
	protected boolean alive;

	public Powerup(World world, BufferedImage texture, int row, int col) {
		super(world, texture, row, col);
		alive = true;
	}

	@Override
	public void render(Graphics2D g) {
		if (!alive)
			return;
		BasicRenderer.textureRender(g, texture, x + xWorld, y + yWorld);
	}

	@Override
	public void update() {
		if (!alive)
			return;
		xWorld = world.getX();
		yWorld = world.getY();
	}

	public boolean isDead() {
		return !alive;
	}

	public abstract PowerupBundle interact();

	public abstract byte getValue();

}
