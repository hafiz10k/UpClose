package handler;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Game;

public class KeyBoardListener implements KeyListener, FocusListener{

	public boolean[] keys = new boolean[120];
	
	private Game game;
	
	public KeyBoardListener (Game game) {
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = true;
		}
//		if(keys[KeyEvent.VK_CONTROL]) {
//			game.handleCTRL(keys);
//		}

	}

	@Override
	public void keyReleased(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = false;
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		for(int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent event) {}

	@Override
	public void focusGained(FocusEvent event) {}

	public boolean up() {
		return keys[KeyEvent.VK_UP];
	}

	public boolean down() {
		return keys[KeyEvent.VK_DOWN];
	}

	public boolean left() {
		return keys[KeyEvent.VK_LEFT];
	}

	public boolean right() {
		return keys[KeyEvent.VK_RIGHT];
	}
	
	public boolean enter() {
		return keys[KeyEvent.VK_ENTER];
	}
	
	public boolean a() {
		return keys[KeyEvent.VK_A];
	}
	
	public boolean esc() {
		return keys[KeyEvent.VK_ESCAPE];
	}





}
