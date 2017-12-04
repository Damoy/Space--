package world;

import toolbox.data.GameMemory;

public class TiledCoordinates {

	public int row;
	public int col;
	
	public TiledCoordinates(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public TiledCoordinates(){
		this(0, 0);
	}
	
	public void reset(){
		row = 0;
		col = 0;
	}
	
	@Override
	public String toString(){
		StringBuffer b = GameMemory.OUTPUT_STRING_BUFFER;
		b.append("Tiled coordinates: ");
		b.append("[row:");
		b.append(row);
		b.append(", col:");
		b.append(col);
		b.append(']');
		return GameMemory.getOutputBufferContentAndReset();
	}

}
