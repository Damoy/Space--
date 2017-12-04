package toolbox.data;

import world.TiledCoordinates;
import world.World;

public class GameAnalyzer{
	
	private static TiledCoordinates reusableTC = new TiledCoordinates();
	private static boolean haveToResetTC;
	
	public static final TiledCoordinates getWorldTiledSizes(World world){
		if(world == null) return null;
		haveToResetTC = true;
		reusableTC.row = world.getMaxRows();
		reusableTC.col = world.getMaxCols();
		return reusableTC;
	}

	public static void update() {
		if(haveToResetTC) reusableTC.reset();
	}
	
	
}
