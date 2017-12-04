package entities;

import java.awt.image.BufferedImage;

import physics.AABB;
import physics.TileCollisionAnalyzer;
import toolbox.computing.Maths;
import toolbox.data.GameInformation;
import toolbox.data.GameMemory;
import toolbox.errors.Exceptions;
import toolbox.interfaces.G2DRenderable;
import toolbox.interfaces.Labelable;
import toolbox.interfaces.Updatable;
import world.World;
import world.tiles.Tile;

public abstract class Entity implements G2DRenderable, Updatable, Labelable {

	protected int row;
	protected int col;
	protected int w;
	protected int h;

	protected int x;
	protected int y;

	protected boolean moved;
	protected boolean canMove = true;

	protected BufferedImage texture;
	protected AABB box;
	protected String label;
	protected TileCollisionAnalyzer tileCollisionAnalyzer;
	protected World world;

	public Entity(World world, BufferedImage texture, int row, int col) {
		this.texture = texture;
		this.row = row;
		this.col = col;
		this.x = col * GameInformation.TILE_SIZE;
		this.y = row * GameInformation.TILE_SIZE;
		this.box = new AABB(this);
		this.world = world;
		tileCollisionAnalyzer = new TileCollisionAnalyzer(world, this, GameInformation.TILE_SIZE);
		updateSizes(texture.getWidth(), texture.getHeight());
	}

	/**
	 * Collisions against tiles are checked dynamically while moving.
	 */

	// offsets
	private final static int xr = GameInformation.TILE_SIZE - 1;
	private final static int yr = GameInformation.TILE_SIZE - 1;
	// shift value
	private final static int sv = Maths.log2(GameInformation.TILE_SIZE);

	protected boolean move(int dx, int dy) {
		checkCoordsBounds();
		if (dx != 0 || dy != 0) {
			boolean stopped = true;
			if (dx != 0 && move2(dx, 0))
				stopped = false;
			if (dy != 0 && move2(0, dy))
				stopped = false;
			return !stopped;
		}
		return true;
	}

	protected boolean move2(int xa, int ya) {
		if (xa != 0 && ya != 0)
			throw new IllegalArgumentException("Move2 can only move along one axis at a time!");

		int colTo0 = ((x)) >> sv;
		int rowTo0 = ((y)) >> sv;
		int colTo1 = ((x) + xr) >> sv;
		int rowTo1 = ((y) + yr) >> sv;

		int col0 = ((x + xa)) >> sv;
		int row0 = ((y + ya)) >> sv;
		int col1 = ((x + xa) + xr) >> sv;
		int row1 = ((y + ya) + yr) >> sv;

		boolean blocked = false;

		for (int row = row0; row <= row1; row++)
			for (int col = col0; col <= col1; col++) {
				if (col >= colTo0 && col <= colTo1 && row >= rowTo0 && row <= rowTo1)
					continue;

				if (row < 0 || row >= world.getMaxRows() || col < 0 || col >= world.getMaxCols()) {
					continue;
				}

				Tile t = world.getTileAt(row, col);
				if (t != null && t.isBlocked()) {
					blocked = true;
					return false;
				}
			}

		if (blocked)
			return false;

		moved = true;
		x += xa;
		y += ya;

		assert (getWidth() == GameInformation.TILE_SIZE >> 1);
		assert (getHeight() == GameInformation.TILE_SIZE >> 1);

		checkCoordsBounds();
		updateTilePos();
		checkTiledCoordsBounds();
		return true;
	}

	protected void checkCoordsBounds() {
		if (x < 0)
			x = 0;
		if (x + getWidth() >= world.getMaxWidth())
			x = world.getMaxWidth() - getWidth();
		if (y < 0)
			y = 0;
		if (y + getHeight() >= world.getMaxHeight())
			y = world.getMaxHeight() - getHeight();
	}

	protected void checkTiledCoordsBounds() {
		if (row < 0)
			row = 0;
		if (row >= world.getMaxRows())
			row = world.getMaxRows() - 1;
		if (col < 0)
			col = 0;
		if (col >= world.getMaxCols())
			col = world.getMaxCols() - 1;
	}

	public void setX(int x) {
		this.x = x;
		if (!moved)
			moved = true;
	}

	public void setY(int y) {
		this.y = y;
		if (!moved)
			moved = true;
	}

	public void setPos(int x, int y) {
		setX(x);
		setY(y);
	}

	public void incX(float x) {
		this.x += x;
		if (!moved)
			moved = true;
	}

	public void incY(float y) {
		this.y += y;
		if (!moved)
			moved = true;
	}

	public void updateSizes(int w, int h) {
		setWidth(w);
		setHeight(h);
	}

	protected void updateTilePos() {
		if (moved) {
			row = (int) y / GameInformation.TILE_SIZE;
			col = (int) x / GameInformation.TILE_SIZE;
			moved = false;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Entity)) {
			return false;
		}
		Entity e = (Entity) o;
		return (e.getX() == x && e.getY() == y && e.getRow() == row && e.getCol() == col);
	}

	@Override
	public void setLabel(String label) {
		if (label == null)
			return;
		if (label.equals(this.label))
			return;
		this.label = label;
	}

	@Override
	public String toString() {
		GameMemory.resetOutputStringBuffer();
		StringBuffer b = GameMemory.OUTPUT_STRING_BUFFER;
		if (label == null)
			b.append("Entity");
		else
			b.append(label);

		b.append(": [x:");
		b.append(x);
		b.append(", y:");
		b.append(y);
		b.append(", row:");
		b.append(row);
		b.append(", col:");
		b.append(col);
		b.append(", w:");
		b.append(getWidth());
		b.append(", h:");
		b.append(getHeight());
		b.append(", ");

		StringBuffer buffer = GameMemory.resetOutputStringBuffer(true);
		buffer.append(box.toString());

		return buffer.toString();
	}

	public void updateBox(boolean updateSizes) {
		box.update(x, y, getWidth(), getHeight(), updateSizes);
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public AABB getBox() {
		return box;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return texture.getWidth();
	}

	public int getHeight() {
		return texture.getHeight();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setWidth(int w) {
		if (w < 0)
			Exceptions.throwIllegalArgument("Width < 0 !");
		this.w = w;
	}

	public void setHeight(int h) {
		if (h < 0)
			Exceptions.throwIllegalArgument("Height < 0 !");
		this.h = h;
	}

	public void setRow(int row) {
		this.row = row;
		y = row * GameInformation.TILE_SIZE;
	}

	public void setCol(int col) {
		this.col = col;
		x = col * GameInformation.TILE_SIZE;
	}

}
