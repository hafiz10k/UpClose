package entity;

import game.Game;
import game.Game.STATE;
import game.GameObject;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Player implements GameObject {

	private Rectangle playerRectangle;
	private int speed = 4;
	
	// 0 - Down, 1 - Left, 2 - Right, 3 - Up
	private int direction = 0;
	
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	
	
	public Player(Sprite sprite) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite; 
		}
		
		updateDirection();
		playerRectangle = new Rectangle(32, 16, 16, 32);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
	}
	
	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimationRange(direction * 4, (direction * 4) + 4);
		}
	}
	
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// renderer.renderRectangle(playerRectangle, xZoom, yZoom);
		if(animatedSprite != null) {
		renderer.renderSprite(animatedSprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		}
		else if(sprite != null) {
			renderer.renderSprite(sprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(playerRectangle, xZoom, yZoom, false);
		}
		
	}

	// 0 - Down, 1 - Left, 2 - Right, 3 - Up
	public void update(Game game) {
		KeyBoardListener keyListener = game.getKeyListener();
		
		boolean didMove = false;
		int newDirection = direction;
		
		if(keyListener.left()) {
			newDirection = 1;
			didMove = true;
			playerRectangle.x -= speed;			
		}
		
		if(keyListener.right()) {
			newDirection = 2;
			didMove = true;
			playerRectangle.x += speed;			
		}
		
		if(keyListener.up()) {
			newDirection = 3;
			didMove = true;
			playerRectangle.y -= speed;			
		}
		
		if(keyListener.down()) {
			newDirection = 0;
			didMove = true;
			playerRectangle.y += speed;			
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		
		if(!didMove) {
			animatedSprite.reset();
		}
				
		updateCamera(game.getRenderer().getCamera());
		
		if (didMove) {
			animatedSprite.update(game);
		}
		
	}
	
	public void updateCamera(Rectangle camera) {
		camera.x = playerRectangle.x - (camera.w / 2);
		camera.y = playerRectangle.y - (camera.h / 2);
			
	
		if (camera.x >= 600) {
			playerRectangle.x = 1100;
			Game.State = STATE.MENU;
		}
		
		if (camera.y <= -652) {
			playerRectangle.y = -252;
		}
		
		System.out.println(camera.y + " " + playerRectangle.y);
		
	}
	
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

}
