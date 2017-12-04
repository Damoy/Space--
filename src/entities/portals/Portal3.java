package entities.portals;

import java.awt.image.BufferedImage;

import graphics.Texture;
import toolbox.data.GameInformation;
import world.World;

public class Portal3 extends Portal {

	private BufferedImage[] frames = new BufferedImage[] { Texture.PORTAL_9_TEXTURE_8x8, Texture.PORTAL_10_TEXTURE_8x8,
			Texture.PORTAL_11_TEXTURE_8x8, Texture.PORTAL_12_TEXTURE_8x8 };

	private byte current = 0;
	private byte count = 0;

	public Portal3(World world, int row, int col) {
		super(world, GameInformation.LEVEL_4, Texture.PORTAL_9_TEXTURE_8x8, row, col);
	}

	@Override
	public void update() {
		texture = nextFrame();
	}

	private BufferedImage nextFrame() {
		BufferedImage frame = frames[current];
		count++;
		if (count >= GameInformation.UPS >> 1) {
			current++;
			count = (byte) 0;
		}

		if (current >= frames.length)
			current = (byte) 0;
		return frame;
	}

}
