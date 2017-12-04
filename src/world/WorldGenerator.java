package world;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Interaction;
import entities.Mob;
import entities.Player;
import entities.Zombie;
import entities.behaviors.MobBehavior;
import entities.behaviors.ZombieBehavior;
import entities.portals.Portal;
import entities.portals.Portal1;
import entities.portals.Portal2;
import entities.portals.Portal3;
import entities.portals.Portal4;
import entities.powerups.Ammo;
import entities.powerups.Powerup;
import toolbox.data.GameInformation;
import toolbox.output.Logs;
import world.tiles.EarthTile;
import world.tiles.GroundTile;
import world.tiles.Hole;
import world.tiles.ObstacleTile1;
import world.tiles.TPTile;
import world.tiles.Tile;
import world.tiles.WaterTile;

public class WorldGenerator {

	private List<WorldSetup> memory;
	private World world;
	private Player player;
	
	public WorldGenerator(World world, Player player){
		memory = new ArrayList<>();
		this.world = world;
		this.player = player;
	}
	
	public WorldSetup generateWorld(byte level){
		if(level == GameInformation.LEVEL_1) return generateLevel(level, GameInformation.LEVEL_1_PATH);
		if(level == GameInformation.LEVEL_2) return generateLevel(level, GameInformation.LEVEL_2_PATH);
		if(level == GameInformation.LEVEL_3) return generateLevel(level, GameInformation.LEVEL_3_PATH);
		if(level == GameInformation.LEVEL_4) return generateLevel(level, GameInformation.LEVEL_4_PATH);
		if(level == GameInformation.LEVEL_5){
			Logs.println("Generated level 5 !");
			return generateLevel(level, GameInformation.LEVEL_5_PATH);
		}
		
		throw new IllegalArgumentException("Wrong level !");
	}
	
	private WorldSetup generateLevel(byte level, String path){
		return generateWorld(level, path);
	}
	
	private WorldSetup generateWorld(byte level, String path){
		List<Mob> mobs = new ArrayList<>();
		List<Interaction> interactions = new ArrayList<>();
		List<Powerup> powerups = new ArrayList<>();
		Portal portal = null;
		
		try {
			BufferedImage world = ImageIO.read(new FileInputStream(path));
			
			int maxCols = world.getWidth();
			int maxRows = world.getHeight();
			
			Tile[][] worldMap = new Tile[maxRows][maxCols];
			
			int prow = 0;
			int pcol = 0;
			
			for(int col = 0; col < maxCols; col++){
				for(int row = 0; row < maxRows; row++){
					int argb = world.getRGB(col, row);
					
					if(argb == GameInformation.GROUND_COLOR.getRGB()){
						worldMap[row][col] = new EarthTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.WATER_COLOR.getRGB()){
						worldMap[row][col] = new WaterTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.PLAYER_COLOR.getRGB()){
						worldMap[row][col] = new GroundTile(this.world, row, col);
						prow = row;
						pcol = col;
						continue;
					}
					
					if(argb == GameInformation.LEVEL1_GROUND_COLOR.getRGB()){
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.LEVEL3_GROUND_COLOR.getRGB()){
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.ZOMBIE_COLOR.getRGB()){
						MobBehavior behavior = new ZombieBehavior(player);
						Mob mob = new Zombie(this.world, behavior, row, col);
						mobs.add(mob);
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.OBSTACLE_1_COLOR.getRGB()){
						worldMap[row][col] = new ObstacleTile1(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.PORTAL1_COLOR.getRGB()){
						portal = new Portal1(this.world, row, col);
						interactions.add(portal);
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.PORTAL2_COLOR.getRGB()){
						portal = new Portal2(this.world, row, col);
						interactions.add(portal);
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.PORTAL3_COLOR.getRGB()){
						portal = new Portal3(this.world, row, col);
						interactions.add(portal);
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.PORTAL4_COLOR.getRGB()){
						portal = new Portal4(this.world, row, col);
						interactions.add(portal);
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.HOLE_COLOR.getRGB()){
						worldMap[row][col] = new Hole(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.UNKNOWN_COLOR.getRGB()){
						worldMap[row][col] = new Hole(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.AMMO_COLOR.getRGB()){
						Powerup ammo = new Ammo(this.world, row, col);
						powerups.add(ammo);
						worldMap[row][col] = new GroundTile(this.world, row, col);
						continue;
					}
					
					if(argb == GameInformation.TP_COLOR.getRGB()){
						worldMap[row][col] = new TPTile(this.world, row, col);
						continue;
					}
					
					Logs.println("Color unknown: " + row + ", " + col);
					throw new IllegalStateException("Unknown tile !");
				}
			}
			
			WorldSetup setup = new WorldSetup(level, worldMap, prow, pcol,
					mobs, interactions, powerups, portal, new TiledCoordinates(maxRows, maxCols));
			memory.add(setup);
			
			Logs.println("World Generator generated initial world's sizes");
			Logs.println("Player init row: " + prow + ", col: " + pcol);
			
			return setup;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public WorldSetup seeGeneratorMemory(int generation){
		if(generation < 0 || generation > memory.size()) return null;
		return memory.get(generation);
	}

	
	
	
}
