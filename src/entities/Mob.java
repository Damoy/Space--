package entities;

import java.awt.image.BufferedImage;

import entities.behaviors.MobBehavior;
import sounds.Sound;
import toolbox.output.Logs;
import world.World;

public abstract class Mob extends Entity {

	protected byte armor;
	protected byte life;
	protected byte dmg;
	protected boolean dead;

	// and he gets its information as the world position
	protected float xWorld;
	protected float yWorld;

	protected byte dx;
	protected byte dy;

	protected MobBehavior behavior;

	public Mob(World world, MobBehavior behavior, BufferedImage texture, int row, int col) {
		super(world, texture, row, col);
		life = getLife();
		armor = getArmor();
		dmg = getDmg();
		dead = false;
		xWorld = world.getX();
		yWorld = world.getY();
		dx = (byte) 0;
		dy = (byte) 0;
		this.behavior = behavior;
	}

	@Override
	public void update() {
		if (dead)
			return;
		resetPosVariations();
		updateMapPos();

		behavior.behave(x, y, dmg);
		supdate();
	}

	private void resetPosVariations() {
		if (dx != 0)
			dx = 0;
		if (dy != 0)
			dy = 0;
	}

	private void updateMapPos() {
		if (world == null)
			throw new IllegalStateException("The player is in the void");
		xWorld = world.getX();
		yWorld = world.getY();
	}

	protected abstract void supdate();

	public boolean hurt(byte dmg) {
		return computeDmg(dmg);
	}

	protected boolean computeDmg(byte dmg) {
		byte computedDmg = (byte) (dmg << 1);
		computedDmg -= armor;
		byte lifeCpy = life;
		life -= computedDmg;
		Logs.println("Mob hurt: " + computedDmg);
		checkState();
		boolean hitted = life != lifeCpy;
		if (hitted)
			Sound.HIT.play();
		return hitted;
	}

	protected void checkState() {
		if (life <= 0)
			dead = true;
	}

	public abstract byte getLife();

	public abstract byte getArmor();

	public abstract byte getDmg();

	public boolean isDead() {
		return dead;
	}

}
