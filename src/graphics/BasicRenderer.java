package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BasicRenderer {

	public static void textureRender(Graphics2D g, BufferedImage texture, float x, float y) {
		g.drawImage(texture, (int) x, (int) y, null);
	}

	public static void stringRender(Graphics2D g, String s, float x, float y) {
		g.drawString(s, x, y);
	}
}
