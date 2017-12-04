package entities.behaviors;

import entities.Player;
import toolbox.computing.Maths;
import toolbox.data.GameInformation;

public class ZombieBehavior extends MobBehavior {

	private byte dx;
	private byte dy;
	private byte offSetMove = (byte) 0;
	private boolean moveLast = false;
	// TODO careful if objects inside
	private MobBehaviorBundle bundle = new MobBehaviorBundle((byte) 0, (byte) 0, false);

	public ZombieBehavior(Player player) {
		super(player);
	}

	private byte framesDontSeePlayer = 0;
	private boolean sleeping = false;

	@Override
	public MobBehaviorBundle behave(int x, int y, byte dmg) {
		int px = (int) player.getX();
		int py = (int) player.getY();

		int distX = Math.abs(px - x);
		int distY = Math.abs(py - y);

		if (sleeping) {
			framesDontSeePlayer++;

			if (framesDontSeePlayer > (GameInformation.UPS << 1)) {
				framesDontSeePlayer = 0;
				sleeping = false;
			}
		}

		offSetMove++;
		if (distX <= 24 && distY <= 24 && !sleeping) {
			if (offSetMove >= 2) {
				moveLast = false;
				offSetMove = 0;
			}

			if (!moveLast) {
				dx = (byte) ((px > x) ? 1 : (px == x) ? 0 : -1);
				dy = (byte) ((py > y) ? 1 : (py == y) ? 0 : -1);
				moveLast = true;
			}

			int dpx = px + dx;
			int dpy = py + dy;
			distX = Math.abs(dpx - x);
			distY = Math.abs(dpy - y);
			if (distX <= 8 && distY <= 8) {
				dx = 0;
				dy = 0;
				player.hurt(dmg);
				sleeping = true;
			}

		} else {
			if (offSetMove >= 4) {
				moveLast = false;
				offSetMove = 0;
			}

			if (!moveLast) {
				boolean ndx = Maths.brand();
				boolean ndy = Maths.brand();

				dx = (byte) Maths.irand(2);
				if (ndx)
					dx = (byte) -dx;
				dy = (byte) Maths.irand(2);
				if (ndy)
					dy = (byte) -dy;
				moveLast = true;
			}
		}

		bundle.dx = this.dx;
		bundle.dy = this.dy;
		bundle.sleeping = sleeping;

		return bundle;
	}

}
