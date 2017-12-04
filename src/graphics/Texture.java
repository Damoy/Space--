package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import toolbox.computing.Maths;
import toolbox.data.GameInformation;

public class Texture {

	public final static BufferedImage PLAYER_DOWN_1_8X8 = getSubTexture8x8(0, 0, 8, 8);
	public final static BufferedImage PLAYER_DOWN_2_8X8 = getSubTexture8x8(0, 8, 8, 8);
	public final static BufferedImage GREEN_TERRAIN_8X8 = getSubTexture8x8(8, 0, 8, 8);
	public final static BufferedImage WATER_8X8 = getSubTexture8x8(16, 0, 8, 8);
	public final static BufferedImage WATER2_8X8 = getSubTexture8x8(24, 0, 8, 8);

	public final static BufferedImage TP_8X8 = getSubTexture8x8(32, 16, 8, 8);

	public final static BufferedImage GROUND_1_TEXTURE_8x8 = getSubTexture8x8(32, 0, 8, 8);
	public final static BufferedImage GROUND_2_TEXTURE_8x8 = getSubTexture8x8(40, 0, 8, 8);
	public final static BufferedImage GROUND_3_TEXTURE_8x8 = getSubTexture8x8(48, 0, 8, 8);

	public final static BufferedImage OBSTACLE_1_TEXTURE_8x8 = getSubTexture8x8(56, 0, 8, 8);
	public final static BufferedImage OBSTACLE_2_TEXTURE_8x8 = getSubTexture8x8(64, 0, 8, 8);
	public final static BufferedImage OBSTACLE_3_TEXTURE_8x8 = getSubTexture8x8(56, 8, 8, 8);
	public final static BufferedImage OBSTACLE_4_TEXTURE_8x8 = getSubTexture8x8(64, 8, 8, 8);
	public final static BufferedImage OBSTACLE_5_TEXTURE_8x8 = getSubTexture8x8(56, 16, 8, 8);

	public final static BufferedImage GROUND_4_TEXTURE_8x8 = getSubTexture8x8(32, 8, 8, 8);
	public final static BufferedImage GROUND_5_TEXTURE_8x8 = getSubTexture8x8(40, 8, 8, 8);
	public final static BufferedImage GROUND_6_TEXTURE_8x8 = getSubTexture8x8(48, 8, 8, 8);

	public final static BufferedImage PORTAL_1_TEXTURE_8x8 = getSubTexture8x8(72, 0, 8, 8);
	public final static BufferedImage PORTAL_2_TEXTURE_8x8 = getSubTexture8x8(80, 0, 8, 8);
	public final static BufferedImage PORTAL_3_TEXTURE_8x8 = getSubTexture8x8(88, 0, 8, 8);
	public final static BufferedImage PORTAL_4_TEXTURE_8x8 = getSubTexture8x8(96, 0, 8, 8);

	public final static BufferedImage PORTAL_5_TEXTURE_8x8 = getSubTexture8x8(72, 8, 8, 8);
	public final static BufferedImage PORTAL_6_TEXTURE_8x8 = getSubTexture8x8(80, 8, 8, 8);
	public final static BufferedImage PORTAL_7_TEXTURE_8x8 = getSubTexture8x8(88, 8, 8, 8);
	public final static BufferedImage PORTAL_8_TEXTURE_8x8 = getSubTexture8x8(96, 8, 8, 8);

	public final static BufferedImage PORTAL_9_TEXTURE_8x8 = getSubTexture8x8(72, 16, 8, 8);
	public final static BufferedImage PORTAL_10_TEXTURE_8x8 = getSubTexture8x8(80, 16, 8, 8);
	public final static BufferedImage PORTAL_11_TEXTURE_8x8 = getSubTexture8x8(88, 16, 8, 8);
	public final static BufferedImage PORTAL_12_TEXTURE_8x8 = getSubTexture8x8(96, 16, 8, 8);

	public final static BufferedImage PORTAL_13_TEXTURE_8x8 = getSubTexture8x8(72, 24, 8, 8);
	public final static BufferedImage PORTAL_14_TEXTURE_8x8 = getSubTexture8x8(80, 24, 8, 8);
	public final static BufferedImage PORTAL_15_TEXTURE_8x8 = getSubTexture8x8(88, 24, 8, 8);
	public final static BufferedImage PORTAL_16_TEXTURE_8x8 = getSubTexture8x8(96, 24, 8, 8);

	public final static BufferedImage ZOMBIE_DOWN_1_8X8 = getSubTexture8x8(0, 16, 8, 8);
	public final static BufferedImage ZOMBIE_DOWN_2_8X8 = getSubTexture8x8(0, 24, 8, 8);

	public final static BufferedImage SLEEP_ZOMBIE_DOWN_1_8X8 = getSubTexture8x8(8, 16, 8, 8);
	public final static BufferedImage SLEEP_ZOMBIE_DOWN_2_8X8 = getSubTexture8x8(8, 24, 8, 8);

	public final static BufferedImage HEART_8X8 = getSubTexture8x8(120, 0, 8, 8); // 112

	public final static BufferedImage PLAYER_PROJECTILE_4X4 = getSubTexture8x8(17, 16, 3, 3);
	public final static BufferedImage PLAYER_PROJECTILE_8X8 = getSubTexture8x8(16, 8, 8, 8);
	public final static BufferedImage AMMO_6X6 = getSubTexture8x8(24, 16, 6, 6);

	public final static BufferedImage LOGO = getSubTexture("./resources/icons/spacepm_26x26.png", 0, 0, 26, 26);
	public final static BufferedImage LOGO2 = getSubTexture("./resources/textures/spacepm2_26x26.png", 0, 0, 26, 26);

	public static BufferedImage getRandomGroundTexture() {
		int rand = Maths.irand(3);
		if (rand == 0)
			return GROUND_1_TEXTURE_8x8;
		if (rand == 1)
			return GROUND_2_TEXTURE_8x8;
		if (rand == 2)
			return GROUND_3_TEXTURE_8x8;

		return GROUND_1_TEXTURE_8x8;
	}

	public static BufferedImage getRandomObstacleTexture() {
		int rand = Maths.irand(4);
		if (rand == 0)
			return OBSTACLE_1_TEXTURE_8x8;
		if (rand == 1)
			return OBSTACLE_2_TEXTURE_8x8;
		if (rand == 2)
			return OBSTACLE_3_TEXTURE_8x8;
		if (rand == 3)
			return OBSTACLE_4_TEXTURE_8x8;

		return OBSTACLE_1_TEXTURE_8x8;
	}

	public static BufferedImage getSubTexture8x8(int xp, int yp, int width, int height) {
		return getSubTexture(GameInformation.LD_40_TEX_8x8, xp, yp, width, height);
	}

	public static BufferedImage getSubTexture(String filePath, int xp, int yp, int width, int height) {
		BufferedImage tileImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			tileImage = ImageIO.read(new FileInputStream(filePath));
			tileImage = tileImage.getSubimage(xp, yp, width, height);

			int w = tileImage.getWidth();
			int h = tileImage.getHeight();

			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					int argb = tileImage.getRGB(x, y);
					// removes the MAGENTA color from the texture
					if (argb == Color.MAGENTA.getRGB()) {
						tileImage.setRGB(x, y, 0);
					}
				}
			}
			return tileImage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
