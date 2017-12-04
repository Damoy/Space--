package toolbox.listeners;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import game.Game;
import input.Keys;

public class GameListener implements KeyListener, MouseListener{

	private Game game;
	private int mouseClicX;
	private int mouseClicY;
	
	public GameListener(Game game){
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePoint, game);
		
		mouseClicX = mousePoint.x;
		mouseClicY = mousePoint.y;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(game.state == Game.MENU){
			handleMenu(e);
		} else if(game.state == Game.ABOUT){
			handleInfos(e);
		} else {
			Keys.keySet(e.getKeyCode(), true);
		}
	}
	
	private boolean up(KeyEvent e){
		int kc = e.getKeyCode();
		return kc == KeyEvent.VK_Z || kc == KeyEvent.VK_W || kc == KeyEvent.VK_UP;
	}
	
	private boolean down(KeyEvent e){
		int kc = e.getKeyCode();
		return kc == KeyEvent.VK_S || kc == KeyEvent.VK_DOWN;
	}
	
	private void handleInfos(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE){
			game.setState(Game.MENU);
		}
	}
	
	private void handleMenu(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
		
		if(up(e)){
			game.decSelected();
		}
		if(down(e)){
			game.incSelected();
		}
		if(game.selected < 0)
			game.resetLowBoundSelected();
		if(game.selected > 1)
			game.resetHigherBoundSeleted();
			
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(game.selected == 0){
				game.setState(Game.IN_GAME);
			} else {
				game.setState(Game.ABOUT);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getMouseClicX() {
		return mouseClicX;
	}

	public void setMouseClicX(int mouseClicX) {
		this.mouseClicX = mouseClicX;
	}

	public int getMouseClicY() {
		return mouseClicY;
	}

	public void setMouseClicY(int mouseClicY) {
		this.mouseClicY = mouseClicY;
	}

	
	

}
