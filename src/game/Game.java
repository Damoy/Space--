package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import graphics.BasicRenderer;
import graphics.Texture;
import graphics.Window;
import sounds.Sound;
import toolbox.data.GameAnalyzer;
import toolbox.data.GameInformation;
import toolbox.data.GameMemory;
import toolbox.interfaces.G2DRenderable;
import toolbox.interfaces.Updatable;
import toolbox.listeners.GameListener;
import toolbox.output.Logs;
import toolbox.time.Timer;
import world.World;

public class Game extends JPanel implements Runnable, Updatable, G2DRenderable {

	// generated serial ID
	private static final long serialVersionUID = -7471530975520076940L;

	// listeners
	private GameListener gameListener;
	// game thread
	private Thread thread;
	// is the game running ?
	private boolean running;
	// screen and image
	private BufferedImage image;
	// the JPanel's (Game) JFrame (Window)
	private Window window;
	// the graphics 2D used everywhere
	private Graphics2D g;
	// the game world
	private World world;

	public Game() {
		super();
		setComponent();
		init();
	}

	private void setComponent() {
		gameListener = new GameListener(this);
		setPreferredSize(new Dimension(GameInformation.WIDTH * GameInformation.SCALE,
				GameInformation.HEIGHT * GameInformation.SCALE));
		setFocusable(true);
		requestFocus();
		addMouseListener(gameListener);
	}

	public byte state;

	public final static byte MENU = (byte) 8;
	public final static byte ABOUT = (byte) 9;
	public final static byte IN_GAME = (byte) 10;

	public byte selected = 0;

	public void incSelected() {
		selected++;
	}

	public void decSelected() {
		selected--;
	}

	public void resetLowBoundSelected() {
		selected = 0;
	}

	public void resetHigherBoundSeleted() {
		selected = 1;
	}

	public void setState(byte state) {
		if (state == MENU) {
			world = null;
		}
		if (this.state == IN_GAME) {
			resetLowBoundSelected();
		}

		Sound.stopBGMusic();
		this.state = state;

		if (state == IN_GAME) {
			initFollowing();
		}
	}

	private void initMenu() {
		state = MENU;
	}

	private void init() {
		initGraphics();
		initMenu();
	}

	public void initFollowing() {
		initWorld();
		Sound.startBgMusic();
	}

	private static Timer endTimer;

	public static Timer getEndTimer() {
		if (endTimer == null)
			endTimer = new Timer(5000);
		return endTimer;
	}

	public static boolean isEndTimerStopped() {
		return endTimer != null && endTimer.isStopped();
	}

	private void initWorld() {
		world = new World(this);
	}

	public void resetWorld(byte level, byte playerLife, byte playerAmmo) {
		world = new World(this, level, playerLife, playerAmmo);
	}

	private void initGraphics() {
		image = new BufferedImage(GameInformation.WIDTH, GameInformation.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		window = new Window(GameInformation.GAME_TITLE, this);
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / GameInformation.UPS;
		@SuppressWarnings("unused")
		int frames = 0;

		@SuppressWarnings("unused")
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		requestFocus();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (unprocessed >= 1) {
				ticks++;
				update();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render(g);
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				frames = 0;
				ticks = 0;
			}
		}
	}

	@Override
	public void update() {
		if (state == IN_GAME) {
			updateSystems();
			updateWorld();
		}
	}

	private void updateWorld() {
		if (isEndTimerStopped()) {
			world = new World(this);
			endTimer = null;
		}

		if (world != null)
			world.update();
	}

	private void updateSystems() {
		GameMemory.update();
		GameAnalyzer.update();
	}

	@Override
	public void render(Graphics2D g) {
		if (state == MENU) {
			renderMenu(g);
		}

		if (state == ABOUT) {
			renderInfos(g);
		}

		if (state == IN_GAME) {
			if (world != null) {
				world.render(g);
				if (world.endGame())
					renderEndGame(g);
			}
		}

		renderToScreen();
	}

	private Font endGame = new Font("Tahoma", Font.BOLD, 14);

	private void renderEndGame(Graphics2D g) {
		// draw end game
		Font save = g.getFont();
		Color saveColor = g.getColor();

		g.setFont(endGame);
		g.setColor(Color.WHITE);

		int x = (GameInformation.WIDTH >> 1) - (GameInformation.WIDTH >> 2) - (GameInformation.WIDTH >> 3);
		int y = (GameInformation.HEIGHT >> 1) - (GameInformation.HEIGHT >> 2) - (GameInformation.HEIGHT >> 6);

		g.drawString("You escaped !", x + ((x >> 1) + (x >> 1)), y);
		g.drawString("Congratulations :) !", x + (x >> 2), y + (y >> 1));

		long secsLeft = world.getEndGameTimer().getSecsLeft();

		if (secsLeft > 0) {
			g.drawString(secsLeft + "s", x + (x << 1) + (x) - (x >> 2), y + y);
		}

		g.setFont(save);
		g.setColor(saveColor);

		Logs.println("End game");
	}

	private Font menuFont1 = new Font("Tahoma", Font.PLAIN, 18);
	private Font menuFont2 = new Font("Tahoma", Font.PLAIN, 14);
	private Font menuFont3 = new Font("Tahoma", Font.PLAIN, 10);

	private void renderMenu(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameInformation.WIDTH, GameInformation.HEIGHT);

		Font save1 = g.getFont();
		g.setFont(menuFont1);

		int x = (GameInformation.WIDTH >> 2) + (GameInformation.WIDTH >> 4);
		int y = (GameInformation.HEIGHT >> 2) - (GameInformation.HEIGHT >> 4);

		Color saveColor = g.getColor();

		g.setColor(Color.WHITE);
		g.drawString("Space+-", x, y);
		g.setFont(menuFont2);

		int x2 = x + (x >> 2) + (x >> 4);
		int y2 = y + (y) + (y >> 2);
		g.drawString("Start", x2, y2);

		int x3 = x2 - (x2 >> 4);
		int y3 = y2 + (y2 >> 1);

		g.drawString("About", x3, y3);

		int x4 = x2 - (x2 >> 1);
		int y4 = y2 + (selected * (y2 >> 1));
		g.drawString(">>", x4, y4);

		g.setFont(menuFont3);
		int x5 = GameInformation.WIDTH * 78 / 100;
		int y5 = GameInformation.HEIGHT * 95 / 100;
		g.drawString("Damoy", x5, y5);

		g.setFont(save1);
		g.setColor(saveColor);

		if (selected == 0)
			BasicRenderer.textureRender(g, Texture.LOGO, (x5 - (x5 >> 1) + (x5 >> 4)) - 2, y5 - (y5 >> 3));
		else
			BasicRenderer.textureRender(g, Texture.LOGO2, (x5 - (x5 >> 1) + (x5 >> 4)) - 2, y5 - (y5 >> 3));
	}

	private void renderInfos(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameInformation.WIDTH, GameInformation.HEIGHT);

		g.setColor(Color.WHITE);
		Font save1 = g.getFont();

		int x = (GameInformation.WIDTH >> 2) + (GameInformation.WIDTH >> 4) + (GameInformation.WIDTH >> 4);
		int y = (GameInformation.HEIGHT >> 2) - (GameInformation.HEIGHT >> 4);

		g.setFont(menuFont1);
		g.drawString("About", x, y);
		g.setFont(menuFont3);

		int x2 = x >> 3;
		int y2 = y + y;
		g.drawString("I made this game in 15h for the ", x2, y2);

		int xx = x2;
		int yy = y2 + (y2 >> 2);
		g.drawString("Ludum Dare 40 compo. :)", xx, yy);

		int x3 = x2;
		int y3 = y2 + (y2 >> 1);
		g.drawString("The theme was 'The more you have,", x3, y3);

		int xxx = x3;
		int yyy = y3 + (y3 >> 3);
		g.drawString("the worse it is'.", xxx, yyy);

		int x4 = x3;
		int y4 = y3 + (y3 >> 1) - (y3 >> 3);
		g.drawString("BG music: Skrjablin", x4, y4);

		int x5 = (x4 << 4) + (x4 << 1);
		int y5 = y4 + (y4 >> 2);
		g.drawString("Damoy", x5, y5);

		g.setFont(save1);

	}

	private void renderToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, GameInformation.S_WIDTH, GameInformation.S_HEIGHT, null);
		g2.dispose();
	}

	public void start() {
		if (running)
			return;
		running = true;
		window.start();
	}

	public void stop() {
		if (!running)
			return;
		running = false;
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(gameListener);
			thread.start();
		}
	}

}
