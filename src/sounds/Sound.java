package sounds;

import java.applet.Applet;
import java.applet.AudioClip;


public class Sound {

	public static final Sound WALL_HIT = new Sound("/sounds/wall_hit.wav");
	public static final Sound PORTAL_INTERACTION = new Sound("/sounds/portal.wav");
	public static Sound BG_MUSIC = new Sound("/sounds/freddie_swamp.wav", true);
	public static final Sound PLAYER_HITTING = new Sound("/sounds/playerhit.wav");
	public static final Sound HIT = new Sound("/sounds/hit.wav");
	
	private AudioClip clip;
	@SuppressWarnings("unused")
	private String path;
	private boolean loop;

	private Sound(String name, boolean loop) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
			if(loop) clip.loop();
			path = name;
			this.loop = loop;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private Sound(String name) {
		this(name, false);
	}
	
	public void loop(){
		clip.loop();
	}
	
	
	public AudioClip getClip(){
		return clip;
	}
	
	public static void stopBGMusic(){
		try{
			Sound.BG_MUSIC.getClip().stop();
			BG_MUSIC = null;
		} catch(Exception e){
			BG_MUSIC = null;
		}
	}
	
	public static void startBgMusic(){
		Sound.BG_MUSIC = new Sound("/sounds/freddie_swamp.wav", true);
		Sound.BG_MUSIC.loop();
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
					if(loop)
						clip.loop();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}