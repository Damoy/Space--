package entities.portals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.Interaction;
import graphics.BasicRenderer;
import sounds.Sound;
import toolbox.data.GameInformation;
import toolbox.output.Logs;
import world.World;

public abstract class Portal extends Interaction{

	private byte destLevel;
	protected boolean alive;
	
	public Portal(World world, byte destLevel,  BufferedImage texture, int row, int col) {
		super(world, texture, row, col);
		this.destLevel = destLevel;
		alive = true;
	}

	@Override
	public void render(Graphics2D g) {
		if(!alive) return;
		float renderX = x + world.getX();
		float renderY = y + world.getY();
		BasicRenderer.textureRender(g, texture, renderX, renderY);
	}
	
	public void interact(World world){
		if(world.getCurrentLevel() == GameInformation.LEVEL_5) return;
		Logs.println("Interacted with portal !");
		Sound.PORTAL_INTERACTION.play();
		alive = false;
		byte playerLife = world.getPlayer().getLife();
		byte playerAmmo = world.getPlayer().getAmmo();
		world.getGame().resetWorld(destLevel, playerLife, playerAmmo);
	}
}
