package toolbox.data;

import java.awt.Color;

import javax.swing.JFrame;


public class GameInformation {

	// game and window information
	public final static String GAME_TITLE = "Space+- | Ludum Dare 40";
	
	public static final int HEIGHT = 180; // 80 // 120 // 240
	public static final int WIDTH = 180; // 96 // 160 // 320
	public static final int SCALE = 4;
	public static final int S_WIDTH = WIDTH * SCALE;
	public static final int S_HEIGHT = HEIGHT * SCALE;
	
	
	public static final int UPS = 60;
	
	// flags
	public static final int CLOSE_OPERATION = JFrame.DO_NOTHING_ON_CLOSE;
	public static final boolean RESIZABLE = false;
	
	// textures paths
	public final static String TEXTURES_16X16_PATH = "./resources/textures/textures_data_16x16.png";
	public final static String TEXTURES_8X8_PATH = "./resources/textures/textures_data_8x8.png";
	public final static String ICON_20x20 = "./resources/icons/spacepm_20x20.png";
	public final static String ICON_26x26 = "./resources/icons/spacepm_26x26.png";
	public final static String ICON_28x28 = "./resources/icons/spacepm_28x28.png";
	
	public final static String LD_40_TEX_8x8 = "./resources/textures/ld_tex_8x8.png";
	public final static String LEVEL_1_PATH = "./resources/levels/level1.png";
	public final static String LEVEL_2_PATH = "./resources/levels/level2.png";
	public final static String LEVEL_3_PATH = "./resources/levels/level3.png";
	public final static String LEVEL_4_PATH = "./resources/levels/level4.png";
	public final static String LEVEL_5_PATH = "./resources/levels/level5.png";
	
	// world information
	public final static int TILE_SIZE = 8;
	public static final int SCREEN_MAX_ROWS = HEIGHT / TILE_SIZE;
	public static final int SCREEN_MAX_COLS = WIDTH / TILE_SIZE;
	public final static float INIT_PLAYER_ROW = 5;
	public final static float INIT_PLAYER_COL = 5;
	public final static float WORLD_TWEEN = 1f;
	public final static float WORLD_INIT_X = 0;
	public final static float WORLD_INIT_Y = 0;
	
	// tiles
	public static final String LABEL_TILE = "tile";
	public static final String EARTH_LABEL_TILE = "Earth";
	
	// level generator
	public final static byte LEVEL_ISLAND = (byte) 11;
	public final static byte LEVEL_1 = (byte) 12;
	public final static byte LEVEL_2 = (byte) 13;
	public final static byte LEVEL_3 = (byte) 14;
	public final static byte LEVEL_4 = (byte) 15;
	public final static byte LEVEL_5 = (byte) 16;
	
	// colors
	public final static Color WATER_COLOR = new Color(0, 38, 255);
	public final static Color GROUND_COLOR = new Color(27, 188, 18);
	public final static Color LEVEL1_GROUND_COLOR = new Color(255, 255, 255); // white
	public final static Color LEVEL3_GROUND_COLOR = new Color(200, 200, 200); // white
	public final static Color ZOMBIE_COLOR = new Color(255, 0, 0);
	public final static Color PLAYER_COLOR = new Color(0, 255, 0);
	public final static Color ESCAPE_COLOR = new Color(0, 0, 255);
	public final static Color OBSTACLE_1_COLOR = new Color(48, 48, 48);
	public final static Color OBSTACLE_2_COLOR = new Color(148, 148, 148);
	public final static Color PORTAL1_COLOR = new Color(140, 255, 255);
	public final static Color PORTAL2_COLOR = new Color(141, 255, 255);
	public final static Color PORTAL3_COLOR = new Color(142, 255, 255);
	public final static Color PORTAL4_COLOR = new Color(143, 255, 255);
	public final static Color HOLE_COLOR = new Color(0, 0, 0);
	public final static Color UNKNOWN_COLOR = new Color(255, 0, 110);
	public final static Color AMMO_COLOR = new Color(10, 120, 120);
	public final static Color TP_COLOR = new Color(0, 50, 25);
	
}
