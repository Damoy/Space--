package world;

public class ClassicCoordinates {

	public float fx, fy;
	public int x, y;
	
	private boolean floatSet;
	
	public ClassicCoordinates(float x, float y){
		fx = x;
		fy = y;
		floatSet = true;
	}
	
	public ClassicCoordinates(int x, int y){
		this.x = x;
		this.y = y;
		floatSet = false;
	}
	
	public boolean isFloatCoordinates(){
		return floatSet;
	}
	
	public boolean isIntCoordinates(){
		return !floatSet;
	}
}
