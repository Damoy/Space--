package entities.behaviors;

public class MobBehaviorBundle {

	public byte dx;
	public byte dy;
	public boolean sleeping;

	public MobBehaviorBundle(byte dx, byte dy, boolean sleeping) {
		this.dx = dx;
		this.dy = dy;
		this.sleeping = sleeping;
	}
}
