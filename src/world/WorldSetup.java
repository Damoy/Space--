package world;

import java.util.List;

import entities.Interaction;
import entities.Mob;
import entities.portals.Portal;
import entities.powerups.Powerup;
import world.tiles.Tile;

public class WorldSetup {

	public Tile[][] map;
	public int playerRow;
	public int playerCol;
	public byte level;
	public List<Mob> levelMobs;
	public List<Interaction> interactions;
	public List<Powerup> powerups;
	public Portal levelPortal;
	public int maxWidth;
	public int maxHeight;
	public int maxRows;
	public int maxCols;
	public TiledCoordinates coordinates;
	
	public WorldSetup(byte level, Tile[][] map, int playerRow, int playerCol,
			List<Mob> levelMobs, List<Interaction> interactions, List<Powerup> powerups, Portal levelPortal,
			int maxW, int maxH, int maxR, int maxCols){
		this.level = level;
		this.map = map;
		this.playerRow = playerRow;
		this.playerCol = playerCol;
		this.levelMobs = levelMobs;
		this.interactions = interactions;
		this.powerups = powerups;
		this.levelPortal = levelPortal;
		this.maxWidth = maxW;
		this.maxHeight = maxH;
		this.maxRows = maxR;
		this.maxCols = maxCols;
	}
	
	public WorldSetup(byte level, Tile[][] map, int playerRow, int playerCol,
			List<Mob> levelMobs, List<Interaction> interactions, List<Powerup> powerups, Portal levelPortal,
			TiledCoordinates coordinates){
		this.level = level;
		this.map = map;
		this.playerRow = playerRow;
		this.playerCol = playerCol;
		this.levelMobs = levelMobs;
		this.interactions = interactions;
		this.powerups = powerups;
		this.levelPortal = levelPortal;
		this.coordinates = coordinates;
	}
	
	
}
