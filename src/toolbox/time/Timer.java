package toolbox.time;

/**
 * Timer class
 */
public class Timer {

	private long startTime;
	private long currentTime;
	private long leftTime;
	private long spentTime;
	
	// max time in milliseconds
	private long delay;
	private boolean alive;
	
	public Timer(long millis) {
		delay = millis;
		startTime = System.currentTimeMillis();
		currentTime = startTime;
		alive = true;
	}
	
	/**
	 * If the timer is still alive then update the timer
	 */
	private void update() {
		if(!alive) return;
		currentTime = System.currentTimeMillis();
		spentTime = currentTime - startTime;
		leftTime = delay - spentTime;
		checkState();
	}
	
	/**
	 * Reset the timer
	 */
	public void reset(){
		startTime = System.currentTimeMillis();
		currentTime = startTime;
		alive = true;
		leftTime = 0L;
		spentTime = 0L;
	}
	
	/**
	 * @return spent time in ms
	 */
	public long getElapsedMillis() {
		update();
		return spentTime;
	}
	
	/**
	 * @return spent time in s
	 */
	public long getElapsedSecs() {
		long millis = getElapsedMillis();
		return convertToSecs(millis);
	}
	
	/**
	 * @return left time in ms
	 */
	public long getMillisLeft() {
		update();
		return leftTime;
	}
	
	/**
	 * @return left time in s
	 */
	public long getSecsLeft() {
		long millis = getMillisLeft();
		return convertToSecs(millis);
	}
	
	/**
	 * check and update if the timer end
	 */
	private void checkState() {
		if(leftTime <= 0)
			alive = false;
	}
	
	public boolean isStopped() {
		update();
		return !alive;
	}
	
	public static long convertToSecs(long millis) {
		return millis / 1000;
	}
	
	public static long convertToMs(long secs) {
		return secs * 1000; 
	}
	
}
