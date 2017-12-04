package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.behaviors.MobBehavior;
import entities.behaviors.MobBehaviorBundle;
import graphics.BasicRenderer;
import graphics.Texture;
import toolbox.time.TickCounter;
import world.World;

public class Zombie extends Mob {

	private TickCounter ticks;
	private byte current;
	private int lastUpdatedY;
	private int lastUpdatedX;

	private BufferedImage[] frames = new BufferedImage[] { Texture.ZOMBIE_DOWN_1_8X8, Texture.ZOMBIE_DOWN_2_8X8 };
	private BufferedImage[] sleepFrames = new BufferedImage[] { Texture.SLEEP_ZOMBIE_DOWN_1_8X8,
			Texture.SLEEP_ZOMBIE_DOWN_2_8X8 };

	public Zombie(World world, MobBehavior behavior, int row, int col) {
		super(world, behavior, Texture.ZOMBIE_DOWN_1_8X8, row, col);
		ticks = new TickCounter();
		current = (byte) 0;
	}

	private boolean sleeping = false;

	@Override
	protected void supdate() {
		ticks.increment();

		MobBehaviorBundle bundle = behavior.behave(x, y, dmg);
		this.dx = bundle.dx;
		this.dy = bundle.dy;
		this.sleeping = bundle.sleeping;

		if (dx != 0 || dy != 0) {
			move(dx, dy);
			computeFrame(dx, dy);
		}
	}

	public void setPosVariations(byte dx, byte dy) {
		this.dx = dx;
		this.dy = dy;
	}

	private void computeFrame(int dx, int dy) {
		BufferedImage frame = texture;

		boolean xChange = Math.abs((x + dx) - (lastUpdatedX + 8)) > 16;
		boolean yChange = Math.abs((y + dy) - (lastUpdatedY + 8)) > 16;

		if (xChange) {
			lastUpdatedX = x;

			if (!sleeping)
				frame = frames[current];
			if (sleeping)
				frame = sleepFrames[current];

			current++;

			if (current >= frames.length)
				current = 0;

		} else if (yChange) {
			lastUpdatedY = y;

			if (!sleeping)
				frame = frames[current];
			if (sleeping)
				frame = sleepFrames[current];

			current++;

			if (current >= frames.length)
				current = 0;
		}

		texture = frame;
	}

	private static final Color CIRCLE_COLOR = new Color(100, 10, 10, 100);

	@Override
	public void render(Graphics2D g) {
		if (dead)
			return;

		int xx = (int) (x + xWorld);
		int yy = (int) (y + yWorld);

		BasicRenderer.textureRender(g, texture, xx, yy);

		Color save = g.getColor();
		g.setColor(CIRCLE_COLOR);
		g.fillOval(xx - 20, yy - 20, 64, 64);
		g.setColor(save);
	}

	@Override
	public byte getLife() {
		return (byte) 5;
	}

	@Override
	public byte getArmor() {
		return (byte) 1;
	}

	@Override
	public byte getDmg() {
		return (byte) 1;
	}

}
