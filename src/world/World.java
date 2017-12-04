package world;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

import entities.Interaction;
import entities.Mob;
import entities.Player;
import entities.portals.Portal;
import entities.powerups.Powerup;
import game.Game;
import graphics.BasicRenderer;
import graphics.GUI;
import toolbox.data.GameInformation;
import toolbox.errors.Exceptions;
import toolbox.interfaces.G2DRenderable;
import toolbox.interfaces.TextureUpdatable;
import toolbox.interfaces.Updatable;
import toolbox.output.Logs;
import toolbox.time.Timer;
import world.tiles.Tile;

/**
 * A simple World: map of the game.
 * @author Damoy
 */
public class World implements G2DRenderable, Updatable{

	// objects
	private Game game;
	private Player player;
	private WorldGenerator worldGenerator;
	private Tile[][] tiles;

	// sizes
	private int maxWidth;
	private int maxHeight;
	private int maxRows;
	private int maxCols;

	// position
	private int x;
	private int y;
	private int rowOffset;
	private int colOffset;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// others
	private float tween;

	private List<Mob> mobs;
	private List<Interaction> interactions;
	private List<Powerup> powerups;
	private Player player2;
	
	
	/**
	 * To distinguish between the number of rows and columns (typed integer),
	 * we initialize the world with appropriate objects.
	 * @param game the current main game
	 * @param player the current main player
	 * @param tiledCoordinates
	 */
	public World(Game game, TiledCoordinates tiledCoordinates){
		this.player = new Player(this, 0, 0);
		byte level = GameInformation.LEVEL_1;
		currentLevel = level;
		
		init(game);
		initSizes(tiledCoordinates);
		generateWorld(currentLevel);
		setWorld();
	}
	
	public World(Game game, ClassicCoordinates classicCoordinates){
		this.player = new Player(this, 0, 0);
		byte level = GameInformation.LEVEL_1;
		currentLevel = level;
		
		init(game);
		initSizes(classicCoordinates);
		generateWorld(currentLevel);
		setWorld();
	}
	
	public World(Game game){
		this.player = new Player(this, 0, 0);
		byte level = GameInformation.LEVEL_1;
		currentLevel = level;
		
		init(game);
		generateLevel(level);
		setWorld();
	}
	
	public World(Game game, byte level){
		this.player = new Player(this, 0, 0);
		currentLevel = level;
		
		init(game);
		generateLevel(level);
		setWorld();
	}
	
	public World(Game game, byte level, byte playerLife, byte playerAmmo){
		this.player = new Player(this, 0, 0, playerLife, playerAmmo);
		currentLevel = level;
		
		init(game);
		generateLevel(level);
		setWorld();
	}
	
	
	/**
	 * --- Initialization methods ---
	 */
	
	private int playerInitRow;
	private int playerInitCol;
	
	public int getPlayerInitRow() {
		return playerInitRow;
	}

	public int getPlayerInitCol() {
		return playerInitCol;
	}

	private void init(Game game){
		this.game = game;
		this.x = 0;
		this.y = 0;
		this.rowOffset = 0;
		this.colOffset = 0;
		this.worldGenerator = new WorldGenerator(this, player);
	}
	
	private void setWorld(){
		setPosition(0, 0);

		setBounds(maxWidth + (GameInformation.WIDTH >> 1),
				maxHeight + (GameInformation.HEIGHT >> 1) - (3 * GameInformation.TILE_SIZE) + (GameInformation.TILE_SIZE >> 1)
				, 0, 0);
		
		setTween(GameInformation.WORLD_TWEEN);
	}
	
	public void initSizes(TiledCoordinates tiledSizes){
		this.maxWidth = tiledSizes.col * GameInformation.TILE_SIZE;
		this.maxHeight = tiledSizes.row * GameInformation.TILE_SIZE;
		this.maxRows = tiledSizes.row;
		this.maxCols = tiledSizes.col;
	}
	
	public void initSizes(ClassicCoordinates classicSizes){
		if(classicSizes.isFloatCoordinates()){
			this.maxWidth = (int) classicSizes.fx;
			this.maxHeight = (int) classicSizes.fy;
		}
		
		else{
			this.maxWidth = classicSizes.x;
			this.maxHeight = classicSizes.y;
		}
		
		this.maxRows = maxHeight / GameInformation.TILE_SIZE;
		this.maxCols = maxWidth / GameInformation.TILE_SIZE;
	}
	
	private byte currentLevel = GameInformation.LEVEL_1;
	
	public void setLevel(byte level){
		player = new Player(this, 0, 0);
		this.x = 0;
		this.y = 0;
		this.rowOffset = 0;
		this.colOffset = 0;
		this.worldGenerator = new WorldGenerator(this, player);
		generateLevel(level);
		setWorld();
	}
	
	public void generateLevel(byte level){
		generateLevel(level, true);
	}
	
	public void generateLevel(byte level, boolean incPlayer){
		this.x = 0;
		this.y = 0;
		this.rowOffset = 0;
		this.colOffset = 0;
		
		if(incPlayer){
			player.incLife((byte) 1);
			player.incAmmo((byte) 1);
		}
		
		generateWorld(level);
		setWorld();
	}
	
	private void generateWorld(byte level){
		currentLevel = level;
		WorldSetup worldSetup = worldGenerator.generateWorld(level); 
		tiles = worldSetup.map;
		playerInitRow = worldSetup.playerRow;
		playerInitCol = worldSetup.playerCol;
		mobs = worldSetup.levelMobs;
		interactions = worldSetup.interactions;
		currentLevel = worldSetup.level;
		levelPortal = worldSetup.levelPortal;
		powerups = worldSetup.powerups;

		initSizes(worldSetup.coordinates);
		
		player.setRow(playerInitRow);
		player.setCol(playerInitCol);
		
		Logs.print("tiles count: ");
		Logs.print(maxRows * maxCols);
		Logs.ln(true);

	}
	
	
	/**
	 * Main World update
	 */
	
	private Timer timerEndGame;
	
	@Override
	public void update() {
		setPosition((GameInformation.WIDTH >> 1) - player.getX(), (GameInformation.HEIGHT >> 1) - player.getY());
		updateTiles();
		updateMobs();
		removeMobs();
		updateInteractions();
		updateBonus();
		updatePosition();
		fixBounds();
		updatePlayer();
		updateEndGame();
	}
	
	private boolean endGame = false;
	
	private void updateEndGame(){
		if(currentLevel == GameInformation.LEVEL_5){
			if(timerEndGame != null && timerEndGame.isStopped()){
				game.setState(Game.MENU);
				return;
			} else {
				if(timerEndGame == null){
					endGame = true;
					timerEndGame = new Timer(10000);
					return;
				}
			}
			
			return;
		}
	}
	
	private void updateBonus(){
		if(powerups == null) return;
		for(Powerup powerup  : powerups){
			powerup.update();
		}
		
		Iterator<Powerup> iterator = powerups.iterator();
		
		while(iterator.hasNext()){
			Powerup next = iterator.next();
			if(next.isDead()){
				iterator.remove();
			}
		}
	}
	
	private void removeMobs(){
		Iterator<Mob> iterator = mobs.iterator();
		
		while(iterator.hasNext()){
			Mob next = iterator.next();
			if(next == null || next.isDead())
				iterator.remove();
		}
	}
	
	private void updatePlayer(){
		player.update();
	}
	
	private void updateMobs(){
		for(Mob m : mobs){
			m.update();
		}
	}
	
	private void updateInteractions(){
		for(Interaction i : interactions){
			i.update();
		}
	}
	
	private boolean canSetX = true;
	private boolean canSetY = true;
	
	public void updatePosition(){
		if(player == null) return;
		float xp = player.getX();
		float yp = player.getY();
		
		maxWidth = tiles[0].length * GameInformation.TILE_SIZE;
		maxHeight = tiles[0].length * GameInformation.TILE_SIZE;
		
		canSetX = (xp >= maxWidth - (GameInformation.WIDTH >> 1)) ? false : true;
		canSetY = (yp >= maxHeight - (GameInformation.HEIGHT >> 1)) ? false : true;
		
	}
	
	private void updateTiles(){
		for(int col = 0; col < maxCols; col++){
			for(int row = 0; row < maxRows; row++){
				Tile t = tiles[row][col];
				if(t instanceof TextureUpdatable) t.update();
			}
		}
	}
	
	
	/**
	 * Main World render
	 * @param g the graphics2D used
	 */

	@Override
	public void render(Graphics2D g) {
		renderTiles(g);
		renderInteractions(g);
		GUI.renderPlayerLives(g, getPlayer().getLife());
		GUI.renderPlayerAmmo(g, getPlayer().getAmmo());
		renderBonus(g);
		renderMobs(g);
		renderPlayer(g);
	}
	
	public Timer getEndGameTimer(){
		return timerEndGame;
	}
	
	public boolean endGame(){
		return endGame;
	}
	
	private void renderBonus(Graphics2D g){
		if(powerups == null) return;
		for(Powerup powerup : powerups){
			powerup.render(g);
		}
	}
	
	private void renderPlayer(Graphics2D g){
		player.render(g);
	}
	
	private void renderMobs(Graphics2D g){
		for(Mob m : mobs){
			m.render(g);
		}
	}
	
	private void renderInteractions(Graphics2D g){
		for(Interaction i : interactions){
			i.render(g);
		}
	}
	
	private void renderTiles(Graphics2D g){
		assert(GameInformation.SCREEN_MAX_ROWS > 1);
		assert(GameInformation.SCREEN_MAX_COLS > 1);
		for(int row = rowOffset; row < rowOffset + GameInformation.SCREEN_MAX_ROWS + 2; row++){
			if(row >= maxRows) break;
			
			for(int col = colOffset; col < colOffset + GameInformation.SCREEN_MAX_COLS + 2; col++){
				if(col >= maxCols) break;
				
				BasicRenderer.textureRender(g, tiles[row][col].getTexture(),
						x + col * GameInformation.TILE_SIZE, y + row * GameInformation.TILE_SIZE);
			}
		}
	}
	
	private Portal levelPortal;
	
	public Portal getCurrentPortal(){
		return levelPortal;
	}
	
	public void setBounds(int i1, int i2, int i3, int i4) {
		if(canSetX){
			xmin = GameInformation.WIDTH - i1;
			xmax = i3;
		}
		
		if(canSetY){
			ymin = GameInformation.HEIGHT - i2;
			ymax = i4;
		}
	}
	
	public void setPosition(float x, float y) {
		if(canSetX){
			this.x += (x - this.x) * tween;
		}
		
		if(canSetY){
			this.y += (y - this.y) * tween;
		}
		
		fixBounds();
		
		if(canSetX){
			colOffset = (int) -this.x / GameInformation.TILE_SIZE;
		}
		
		if(canSetY){
			rowOffset = (int) -this.y / GameInformation.TILE_SIZE;
		}
	}
	
	public void setX(float x){
		if(!canSetX) return;
		this.x += (x - this.x) * tween;
		fixBounds();
		colOffset = (int) -this.x / GameInformation.TILE_SIZE;
	}
	
	public void setY(float y){
		if(!canSetY) return;
		this.y += (y - this.y) * tween;
		fixBounds();
		rowOffset = (int) -this.y / GameInformation.TILE_SIZE;
	}
	
	public void fixBounds() {
		if(canSetX){
			if(x < xmin) x = xmin;
			if(x > xmax) x = xmax;
		}
		
		if(canSetY){
			if(y < ymin) y = ymin;
			if(y > ymax) y = ymax;
		}
	}
	
	
	/**
	 * ---- Getters and setters ----
	 */
	
	public Tile getTileAt(int row, int col){
		if(row < 0 || row >= maxRows) Exceptions.throwIllegalArgument("Row out of bounds !");
		if(col < 0 || col >= maxCols) Exceptions.throwIllegalArgument("Col out of bounds !");
		return tiles[row][col];
	}

	@Deprecated
	public Tile getTileAt(float x, float y){
		if(x < 0 || x >= maxWidth) throw new IllegalArgumentException("x out of bounds !");
		if(y < 0 || y >= maxHeight) throw new IllegalArgumentException("y out of bounds !");
		return tiles[(int) (y / GameInformation.TILE_SIZE)][(int) (x / GameInformation.TILE_SIZE)];
	}
	
	public int getTileType(int row, int col) {
		if(row < 0 || row >= maxRows) Exceptions.throwIllegalArgument("Row out of bounds !");
		if(col < 0 || col >= maxCols) Exceptions.throwIllegalArgument("Col out of bounds !");
		return tiles[row][col].getType();
	}
	

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getMaxCols() {
		return maxCols;
	}

	public void setMaxCols(int maxCols) {
		this.maxCols = maxCols;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getXmin() {
		return xmin;
	}

	public void setXmin(int xmin) {
		this.xmin = xmin;
	}

	public int getYmin() {
		return ymin;
	}

	public void setYmin(int ymin) {
		this.ymin = ymin;
	}

	public int getXmax() {
		return xmax;
	}

	public void setXmax(int xmax) {
		this.xmax = xmax;
	}

	public int getYmax() {
		return ymax;
	}

	public void setYmax(int ymax) {
		this.ymax = ymax;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getColOffset() {
		return colOffset;
	}

	public void setColOffset(int colOffset) {
		this.colOffset = colOffset;
	}
	
	public void setTween(float tween){
		if(tween < 0f || tween > 1f) return;
		this.tween = tween;
	}

	public WorldGenerator getWorldGenerator() {
		return worldGenerator;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public float getTween() {
		return tween;
	}

	public List<Mob> getMobs() {
		return mobs;
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}

	public Player getPlayer2() {
		return player2;
	}

	public byte getCurrentLevel() {
		return currentLevel;
	}

	public boolean isCanSetX() {
		return canSetX;
	}

	public boolean isCanSetY() {
		return canSetY;
	}

	public List<Powerup> getPowerups() {
		return powerups;
	}

	public Portal getLevelPortal() {
		return levelPortal;
	}
	
	
	
	
	

}

