package input;

import java.awt.event.KeyEvent;



public class Keys{
	
	public static final int NUM_KEYS = 32;
	
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int ENTER = 8;
	public static final int ESCAPE = 9;
	public static final int SPACE = 10;
	public static final int KEY_P = 11;
	public static final int KEY_Z = 12;
	public static final int KEY_W = 13;
	public static final int KEY_Q = 14;
	public static final int KEY_A = 15;
	public static final int KEY_S = 16;
	public static final int KEY_D = 17; 
	public static final int KEY_O = 18; 
	public static final int KEY_L = 19; 
	public static final int KEY_M = 20; 
	public static final int KEY_E = 21; 
	public static final int KEY_R = 22; 
	
	
	public static void keySet(int i, boolean b) {
		if(i == KeyEvent.VK_UP) keyState[UP] = b;
		else if(i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
		else if(i == KeyEvent.VK_DOWN) keyState[DOWN] = b;
		else if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
		else if(i == KeyEvent.VK_SPACE) keyState[SPACE] = b;
		else if(i == KeyEvent.VK_P) keyState[KEY_P] = b;
		else if(i == KeyEvent.VK_Z) keyState[KEY_Z] = b;
		else if(i == KeyEvent.VK_W) keyState[KEY_W] = b;
		else if(i == KeyEvent.VK_Q) keyState[KEY_Q] = b;
		else if(i == KeyEvent.VK_A) keyState[KEY_A] = b;
		else if(i == KeyEvent.VK_S) keyState[KEY_S] = b;
		else if(i == KeyEvent.VK_D) keyState[KEY_D] = b;
		else if(i == KeyEvent.VK_O) keyState[KEY_O] = b;
		else if(i == KeyEvent.VK_L) keyState[KEY_L] = b;
		else if(i == KeyEvent.VK_M) keyState[KEY_M] = b;
		else if(i == KeyEvent.VK_E) keyState[KEY_E] = b;
		else if(i == KeyEvent.VK_R) keyState[KEY_R] = b;
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}
	
	public static boolean anyKeyPress() {
		for(int i = 0; i < NUM_KEYS; i++) {
			if(keyState[i]) return true;
		}
		return false;
	}
	
	public static boolean upKeyPressed(){
		return isPressed(UP) || isPressed(KEY_Z) || isPressed(KEY_W);
	}
	
	public static boolean downKeyPressed(){
		return isPressed(DOWN) || isPressed(KEY_S);
	}
	
	public static boolean leftKeyPressed(){
		return isPressed(LEFT) || isPressed(KEY_Q) || isPressed(KEY_A);
	}
	
	public static boolean rightKeyPressed(){
		return isPressed(RIGHT) || isPressed(KEY_D);
	}
	
}
