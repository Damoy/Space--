package entities.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.Player;
import graphics.BasicRenderer;
import toolbox.enums.Direction;
import world.World;

public abstract class Projectile {

	protected Direction direction;
	protected float x;
	protected float y;

	protected float xWorld;
	protected float yWorld;

	protected World world;
	protected BufferedImage texture;
	protected Player player;
	protected float speed;
	protected boolean alive;
	protected byte playerDmg;

	public Projectile(Direction dir, float speed, World world, Player player, BufferedImage texture, float x, float y) {
		this.direction = dir;
		this.world = world;
		this.texture = texture;
		this.speed = speed;
		this.x = x;
		this.y = y;
		alive = true;
		playerDmg = player.getDmg();
	}

	public void update() {
		updateWorldPos();
	}

	private void updateWorldPos() {
		xWorld = world.getX();
		yWorld = world.getY();
	}

	public void render(Graphics2D g) {
		if (!alive)
			return;
		BasicRenderer.textureRender(g, texture, x + xWorld, y + yWorld);
	}

	public boolean isAlive() {
		return alive;
	}

}
