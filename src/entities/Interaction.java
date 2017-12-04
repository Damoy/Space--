package entities;

import java.awt.image.BufferedImage;

import world.World;

public abstract class Interaction extends Entity{

	public Interaction(World world, BufferedImage texture, int row, int col) {
		super(world, texture, row, col);
	}

}
