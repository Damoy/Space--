package entities.projectiles;

import java.util.List;

import entities.Interaction;
import entities.Mob;
import entities.Player;
import graphics.Texture;
import physics.Collision;
import toolbox.data.GameInformation;
import toolbox.enums.Direction;
import world.World;

public class PlayerProjectile extends Projectile {

	public PlayerProjectile(Direction dir, World world, Player player, float x, float y) {
		super(dir, 1, world, player, Texture.PLAYER_PROJECTILE_4X4, x, y);
	}

	@Override
	public void update() {
		super.update();

		float dx = 0.0f;
		float dy = 0.0f;

		switch (direction) {
		case LEFT:
			dx = -speed;
			break;
		case RIGHT:
			dx = speed;
			break;
		case UP:
			dy = -speed;
			break;
		case DOWN:
			dy = speed;
			break;
		}

		List<Interaction> interactions = world.getInteractions();
		List<Mob> mobs = world.getMobs();

		for (Interaction interaction : interactions) {
			float ix = interaction.getX();
			float iy = interaction.getY();
			int size = GameInformation.TILE_SIZE;

			if (Collision.boxCollide(x, y, 4, ix, iy, size)) {
				alive = false;
			}
		}

		for (Mob mob : mobs) {
			float mx = mob.getX();
			float my = mob.getY();
			int size = GameInformation.TILE_SIZE;

			if (Collision.boxCollide(x, y, 3, mx, my, size)) {
				mob.hurt(playerDmg);
				alive = false;
			}
		}

		if (!alive)
			return;

		x += dx;
		y += dy;

		if (x < 0 || x > world.getMaxWidth() || y < 0 || y > world.getMaxHeight()) {
			alive = false;
		}
	}

}
