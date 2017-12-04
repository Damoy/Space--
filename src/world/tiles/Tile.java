package world.tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.BasicRenderer;
import toolbox.data.GameInformation;
import toolbox.data.GameMemory;
import toolbox.errors.Exceptions;
import toolbox.interfaces.G2DRenderable;
import toolbox.interfaces.Labelable;
import toolbox.interfaces.Updatable;
import world.World;

/**
 * A simple Tile class. Contains tiled coordinates and classic coordinates.
 * 
 * @author Damoy
 */
public abstract class Tile implements G2DRenderable, Updatable, Labelable {

	public static final byte NORMAL = 0;
	public static final byte BLOCKED = 1;
	private byte state;

	// a tile knows its position on the world
	protected int row;
	protected int col;
	protected float x;
	protected float y;

	protected BufferedImage texture;
	protected String tileTypeLabel;
	protected String label;

	protected World world;

	public Tile(World world, String tileTypeLabel, BufferedImage texture, int row, int col, byte state) {
		this.world = world;
		init(texture, row, col, state);
		setTileLabel(tileTypeLabel);
	}

	private void init(BufferedImage tex, int r, int c, byte state) {
		initState(state);
		initTexture(tex);
		initPos(r, c);
	}

	private void initState(byte state) {
		if (state != NORMAL && state != BLOCKED) {
			Exceptions.throwIllegalArgument("state should be either NORMAL or BLOCKED");
		}
		this.state = state;
	}

	private void initTexture(BufferedImage texture) {
		this.texture = texture;
	}

	private void initPos(int row, int col) {
		this.row = row;
		this.col = col;
		this.x = col * GameInformation.TILE_SIZE;
		this.y = row * GameInformation.TILE_SIZE;
	}

	/**
	 * We did not use tile's method on world. But I implement it because it can
	 * be pretty useful :)
	 */
	@Override
	public void render(Graphics2D g) {
		BasicRenderer.textureRender(g, texture, x, y);
	}

	/**
	 * Getters and setters
	 */

	public BufferedImage getTexture() {
		return texture;
	}

	public int getType() {
		return state;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setState(byte state) {
		if (state != NORMAL && state != BLOCKED)
			return;
		this.state = state;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	public void setTileLabel(String tileTypeLabel) {
		this.tileTypeLabel = tileTypeLabel;
	}

	private static String toString(byte state) {
		if (state == NORMAL)
			return "NORMAL";
		else if (state == BLOCKED)
			return "BLOCKED";
		throw new IllegalArgumentException();
	}

	public boolean isBlocked() {
		return state == BLOCKED;
	}

	@Override
	public String toString() {
		StringBuffer b = GameMemory.OUTPUT_STRING_BUFFER;
		if (tileTypeLabel == null || tileTypeLabel.equals(""))
			b.append("Abstract ");
		else
			b.append(tileTypeLabel);

		b.append(' ');

		if (label == null)
			b.append("tile");
		else
			b.append(label);

		b.append(' ');
		b.append(GameInformation.TILE_SIZE);
		b.append('x');
		b.append(GameInformation.TILE_SIZE);
		b.append(": [row:");
		b.append(row);
		b.append(", col:");
		b.append(col);
		b.append(", x:");
		b.append(x);
		b.append(", y:");
		b.append(y);
		b.append(", state:");
		b.append(toString(state));
		b.append("]");
		return GameMemory.getOutputBufferContentAndReset();
	}
}
