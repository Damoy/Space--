package physics;

import entities.Entity;
import toolbox.output.Logs;
import world.World;
import world.tiles.Tile;

public class TileCollisionAnalyzer{

	private World world;
	@SuppressWarnings("unused")
	private Entity entity;
	
	public boolean topLeft;
	public boolean topRight;
	public boolean botLeft;
	public boolean botRight;
	
	private int ew;
	private int eh;
	private int tileSize;
	
	public TileCollisionAnalyzer(World world, Entity entity, int tileSize){
		this.world = world;
		this.entity = entity;
		ew = entity.getWidth();
		eh = entity.getHeight();
		this.tileSize = tileSize;
	}
	
	public void computeCollision(float x, float y){
		int leftTile = (int)(x - ew / 2) / tileSize;
		int rightTile = (int)(x + ew / 2 - 1) / tileSize;
		int topTile = (int)(y - eh / 2) / tileSize;
		int bottomTile = (int)(y + eh / 2 - 1) / tileSize;
		
		if(topTile < 0 || bottomTile >= world.getMaxRows() ||
			leftTile < 0 || rightTile >= world.getMaxCols()){
			topLeft = topRight = botLeft = botRight = false;
			return;
		}
		
		int tl = world.getTileType(topTile, leftTile);
		int tr = world.getTileType(topTile, rightTile);
		int bl = world.getTileType(bottomTile, leftTile);
		int br = world.getTileType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		botLeft = bl == Tile.BLOCKED;
		botRight = br == Tile.BLOCKED;
		
		// printCollisions();
	}
	
	public void computeCollision(int row, int col){
		int leftTile = col - 1;
		int rightTile = col + 1;
		int topTile = row - 1;
		int bottomTile = row + 1;
		
		if(topTile < 0 || bottomTile >= world.getMaxRows() ||
			leftTile < 0 || rightTile >= world.getMaxCols()){
			topLeft = topRight = botLeft = botRight = false;
			return;
		}
		
		int tl = world.getTileType(topTile, leftTile);
		int tr = world.getTileType(topTile, rightTile);
		int bl = world.getTileType(bottomTile, leftTile);
		int br = world.getTileType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		botLeft = bl == Tile.BLOCKED;
		botRight = br == Tile.BLOCKED;
		
	}
	
	@SuppressWarnings("unused")
	private void printCollisions(){
		if(topLeft) Logs.println("topleft : " + topLeft);
		if(topRight) Logs.println("topRight : " + topRight);
		if(botLeft) Logs.println("botLeft : " + botLeft);
		if(botRight) Logs.println("botRight : " + botRight);
	}
	
	public boolean checkCollision(float fx, float fy, int dx, int dy) {
		int x = (int) fx;
		int y = (int) fy;
		
		int xt = ((x + dx) + (3 % 2 * 2) * 5) >> 4;
		int yt = ((y + dy) + (3 / 2 * 2 - 4) * 2) >> 4;
		
		return world.getTileAt(xt, yt + 1).isBlocked();
	}

	public boolean checkCollision2(float fx, float fy, int dx, int dy) {
		int x = (int) fx;
		int y = (int) fy;
		
		int xt = ((x + dx) + (3 % 2 * 2 - 1) * 4) >> 4;
		int yt = ((y + dy) + (3 / 2 * 2 - 1) * 4) >> 4;
		
		return world.getTileAt(xt, yt).isBlocked();
	}

//	public void analyze(int row, int col){
//		
//	}
//	
//	public void analyze(float x, float y){
//		
//	}
	
	

	
}
