package graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Game;
import toolbox.data.GameInformation;

public class GUI {

	private GUI(){}
	
	private final static BufferedImage HEART = Texture.HEART_8X8;
	private final static BufferedImage AMMO = Texture.PLAYER_PROJECTILE_8X8;
	private final static Font DEAD_FONT = new Font("TimesRoman", Font.BOLD, 16);
	
	public static void renderPlayerLives(Graphics2D g, byte playerLife){
		int sw = GameInformation.WIDTH;
		int sh = GameInformation.HEIGHT;
		
		int startX = sw * (7 >> 3) - 4;
		int startY = sh * (7 >> 3) + 4;
		
		for(byte b = 0; b < playerLife; b++){
			startX += 9;
			BasicRenderer.textureRender(g, HEART, startX, startY);
		}
	}
	
	public static void renderPlayerAmmo(Graphics2D g, byte playerAmmo){
		int sw = GameInformation.WIDTH;
		int sh = GameInformation.HEIGHT;
		
		int startX = sw * (7 >> 3) - 4;
		int startY = sh * (7 >> 3) + 14;
		
		for(byte b = 0; b < playerAmmo; b++){
			startX += 9;
			BasicRenderer.textureRender(g, AMMO, startX, startY);
		}
	}
	
	public static void renderEndGame(Graphics2D g){
		int sw = GameInformation.WIDTH;
		int sh = GameInformation.HEIGHT;
		int x = (sw >> 1) - (sw >> 2);
		int y = (sh >> 1) - (sh >> 4);
		
		long timerTimeLeft = Game.getEndTimer().getSecsLeft();
		String timeBeforeReset = String.valueOf(timerTimeLeft);
		
		Font save = g.getFont();
		if(!save.getFontName().equals(DEAD_FONT.getFontName())){
			g.setFont(DEAD_FONT);
		}
		
		g.drawString("You died !", x, y);
		
		if(timerTimeLeft > 0)
			g.drawString(timeBeforeReset, x + (sw >> 2), y + (sh >> 2));
	}
}
