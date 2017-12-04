package toolbox.time;


public class TickCounter {

	private final static long MAX = Long.MAX_VALUE;
	private long tick;
	
	public TickCounter(){
		tick = 0;
	}
	
	public void increment(){
		try{
			tick++;
		}
		catch(Exception e){
			if(tick > MAX) tick = 0;
			else throw new IllegalStateException();
		}
	}
	
	public long getTicks(){
		return tick;
	}
	
	public void reset(){
		tick = 0;
	}
}
