package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.portals.Portal;
import entities.powerups.Ammo;
import entities.powerups.Powerup;
import entities.projectiles.PlayerProjectile;
import game.Game;
import graphics.BasicRenderer;
import graphics.GUI;
import graphics.Texture;
import input.Keys;
import physics.Collision;
import sounds.Sound;
import toolbox.data.GameInformation;
import toolbox.enums.Direction;
import toolbox.interfaces.Animated;
import toolbox.output.Logs;
import toolbox.time.TickCounter;
import world.World;
import world.tiles.Tile;

public class Player extends Entity implements Animated {

	// the player knows in which world he is
	private World world;
	// and he gets its information as the world position
	private float xWorld;
	private float yWorld;

	// to count the updates
	private TickCounter ticks;

	// position variation
	private int dx;
	private int dy;

	// move speed
	private float ms;

	protected byte armor;
	protected byte life;
	protected byte dmg;
	protected byte ammo;
	protected boolean dead;

	public Player(World world, int initRow, int initCol) {
		super(world, Texture.PLAYER_DOWN_1_8X8, 11, 11);
		init(world, initRow, initCol);
		armor = 1;
		life = 7;
		dmg = 2;
		ammo = 5;
		dead = false;
	}

	public Player(World world, int initRow, int initCol, byte life, byte ammo) {
		super(world, Texture.PLAYER_DOWN_1_8X8, 11, 11);
		init(world, initRow, initCol);
		armor = 1;
		this.life = life;
		dmg = 2;
		this.ammo = ammo;
		dead = false;
	}

	private void init(World world, int initRow, int initCol) {
		this.world = world;
		xWorld = 0f;
		yWorld = 0f;
		dx = 0;
		dy = 0;
		ms = 1;
		ticks = new TickCounter();

		if (initRow >= 0)
			row = initRow;
		if (initCol >= 0)
			col = initCol;
	}

	private short framesInvicibility = 0;
	private boolean invicible = false;

	public boolean hurt(byte dmg) {
		if (invicible)
			return false;
		boolean hitted = computeDmg(dmg);
		invicible = true;
		framesInvicibility = 0;
		return hitted;
	}

	private byte accumlatedDmg = 0;

	protected boolean computeDmg(byte dmg) {
		byte computedDmg = (byte) (dmg << 1);
		computedDmg -= armor;
		byte lifeCpy = life;
		// life -= computedDmg;
		accumlatedDmg += computedDmg;
		if (accumlatedDmg > 0) {
			life--;
			accumlatedDmg = 0;
		}
		Logs.println("Player took: " + computedDmg + " dmg and lost 1 life !");
		checkState();
		boolean hitted = life != lifeCpy;

		// if(hitted)
		Sound.HIT.play();
		return hitted;
	}

	private void checkState() {
		if (life <= 0) {
			dead = true;
			world.getGame().setState(Game.MENU);
		}
	}

	@Override
	public void update() {
		if (dead)
			return;
		updateMapPos();
		handleInput();
		updateInvincibility();
		updateMovement();
		updateTexture();
		updateProjectiles();
		checkProjectiles();
	}

	private void updateTexture() {
		computeFrame(dx, dy);
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

	private void updateInvincibility() {
		if (invicible) {
			framesInvicibility++;
			if (framesInvicibility > GameInformation.UPS) {
				invicible = false;
				framesInvicibility = 0;
			}
		}
	}

	private List<PlayerProjectile> projectiles = new ArrayList<>();

	private boolean canShoot = true;
	private byte frameBeforeShoot = 0;

	private Direction lastProjDir = Direction.DOWN;

	private byte accumaltedAmmoUsed = 0;

	private void handleInput() {
		resetPosVariations();

		if (Keys.upKeyPressed()) {
			dy -= ms;
			lastProjDir = Direction.UP;
		}
		if (Keys.downKeyPressed()) {
			dy += ms;
			lastProjDir = Direction.DOWN;
		}
		if (Keys.leftKeyPressed()) {
			dx -= ms;
			lastProjDir = Direction.LEFT;
		}
		if (Keys.rightKeyPressed()) {
			dx += ms;
			lastProjDir = Direction.RIGHT;
		}

		if (Keys.isPressed(Keys.SPACE)) {
			if (canShoot && ammo > 0) {
				Sound.PLAYER_HITTING.play();
				PlayerProjectile newProjectile = new PlayerProjectile(lastProjDir, world, this, x + 4, y + 4);
				projectiles.add(newProjectile);
				canShoot = false;
				frameBeforeShoot = 0;

				accumaltedAmmoUsed++;
				if (accumaltedAmmoUsed > 2) {
					ammo--;
					accumaltedAmmoUsed = 0;
				}

			} else {
				frameBeforeShoot++;
				if (frameBeforeShoot > (GameInformation.UPS >> 4)) {
					frameBeforeShoot = 0;
					canShoot = true;
				}
			}
		}

		if (Keys.isPressed(Keys.KEY_R)) {
			world.generateLevel(world.getCurrentLevel(), false);
		}

		if (Keys.isPressed(Keys.ESCAPE))
			world.getGame().setState(Game.MENU);
	}

	private void checkProjectiles() {
		Iterator<PlayerProjectile> iterator = projectiles.iterator();

		while (iterator.hasNext()) {
			PlayerProjectile next = iterator.next();
			if (!next.isAlive())
				iterator.remove();
		}
	}

	@SuppressWarnings("unused")
	private boolean portalInteracted = false;

	private void updateMovement() {
		if (dx != 0 || dy != 0) {

			int newX = dx + x;
			int newY = dy + y;
			int row = newY >> 3;
			int col = newX >> 3;

			try {
				Portal portal = world.getCurrentPortal();
				Tile nextTile = world.getTileAt(row, col);
				Tile portalTile = world.getTileAt(portal.getRow(), portal.getCol());

				if (nextTile.equals(portalTile)) {
					portal.interact(world);
					portalInteracted = true;
					return;
				} else {
					portalInteracted = false;
				}
			} catch (Exception e) {

			}

			// powerups
			List<Powerup> powerups = world.getPowerups();
			if (powerups != null) {
				for (Powerup powerup : powerups) {
					if (Collision.boxCollide(newX, newY, GameInformation.TILE_SIZE, powerup.getX(), powerup.getY(),
							GameInformation.TILE_SIZE)) {
						if (powerup instanceof Ammo) {
							powerup.interact();
							Sound.PORTAL_INTERACTION.play();
							ammo++;
						}
					}
				}
			}

			// easter egg lvl 3
			if (!easterEgg3Took && world.getCurrentLevel() == GameInformation.LEVEL_3) {
				boolean tp = (row == (world.getMaxRows() - 1)) && (col == (world.getMaxCols() - 1));
				if (tp) {
					Sound.PORTAL_INTERACTION.play();
					easterEgg3Took = true;

					int xDest = 28 * GameInformation.TILE_SIZE;
					int yDest = 29 * GameInformation.TILE_SIZE;

					setX(xDest);
					setY(yDest);
					return;
				}
			}

			int oldX = x;
			int oldY = y;
			move(dx, dy);

			if (x == oldX && y == oldY) {
				Sound.WALL_HIT.play();
			}
		}
	}

	private boolean easterEgg3Took = false;

	private byte current;
	private int lastUpdatedY;
	private int lastUpdatedX;

	private BufferedImage[] frames = new BufferedImage[] { Texture.PLAYER_DOWN_1_8X8, Texture.PLAYER_DOWN_2_8X8 };

	private byte steps = 0;

	private void computeFrame(int dx, int dy) {
		BufferedImage frame = texture;

		if (dx != 0)
			steps += Math.abs(dx);
		else if (dy != 0)
			steps += Math.abs(dy);

		if (steps > 7) {
			frame = frames[current];
			steps = 0;
			current++;
			if (current >= frames.length)
				current = 0;
		}

		if (steps < 0)
			steps = 0;

		texture = frame;
	}

	private void updateProjectiles() {
		Iterator<PlayerProjectile> iterator = projectiles.iterator();

		while (iterator.hasNext()) {
			PlayerProjectile next = iterator.next();
			if (next == null || !next.isAlive()) {
				iterator.remove();
				continue;
			}
			next.update();
		}
	}

	@Override
	public void render(Graphics2D g) {
		if (dead) {
			GUI.renderEndGame(g);
			return;
		}

		BasicRenderer.textureRender(g, texture, (int) (x + xWorld), (int) (y + yWorld));

		renderProjectiles(g);
	}

	private void renderProjectiles(Graphics2D g) {
		for (PlayerProjectile proj : projectiles) {
			proj.render(g);
		}
	}

	@Override
	public void generateAnimationFrames() {
	}

	/**
	 * Getters and setters
	 */

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public float getxWorld() {
		return xWorld;
	}

	public float getyWorld() {
		return yWorld;
	}

	public TickCounter getTicks() {
		return ticks;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public float getMs() {
		return ms;
	}

	public byte getArmor() {
		return armor;
	}

	public byte getLife() {
		return life;
	}

	public byte getDmg() {
		return dmg;
	}

	public boolean isDead() {
		return dead;
	}

	public short getFramesInvicibility() {
		return framesInvicibility;
	}

	public boolean isInvicible() {
		return invicible;
	}

	public List<PlayerProjectile> getProjectiles() {
		return projectiles;
	}

	public boolean isCanShoot() {
		return canShoot;
	}

	public byte getFrameBeforeShoot() {
		return frameBeforeShoot;
	}

	public byte getAmmo() {
		return ammo;
	}

	public byte getAccumlatedDmg() {
		return accumlatedDmg;
	}

	public Direction getLastProjDir() {
		return lastProjDir;
	}

	public byte getAccumaltedAmmoUsed() {
		return accumaltedAmmoUsed;
	}

	public byte getCurrent() {
		return current;
	}

	public int getLastUpdatedY() {
		return lastUpdatedY;
	}

	public int getLastUpdatedX() {
		return lastUpdatedX;
	}

	public BufferedImage[] getFrames() {
		return frames;
	}

	public byte getSteps() {
		return steps;
	}

	public void incLife(byte qt) {
		life += qt;
	}

	public void incAmmo(byte qt) {
		ammo += qt;
	}

}
