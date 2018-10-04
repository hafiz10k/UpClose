package entity;

import game.Game;
import game.GameObject;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Player implements GameObject {

	private Rectangle playerRectangle;
	private Rectangle collisionRectangle;

	private int speed = 4;

	// 0 - Down, 1 - Left, 2 - Right, 3 - Up
	private int direction = 0;

	private int layer = 0;

	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	
	private final int xCollisionOffset = 15;
	private final int yCollisionOffset = 35;


	public Player(Sprite sprite, int xZoom, int yZoom) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite; 
		}

		updateDirection();
		playerRectangle = new Rectangle(-90, 0, 24, 32);
		playerRectangle.generateGraphics(3, 0xFF00FF90);

		collisionRectangle = new Rectangle(0, 0, 15*xZoom, 25*yZoom);
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

		collisionRectangle.x = playerRectangle.x;
		collisionRectangle.y = playerRectangle.y;

		if(keyListener.left()) {
			newDirection = 1;
			didMove = true;
			collisionRectangle.x -= speed;			
		}

		if(keyListener.right()) {
			newDirection = 2;
			didMove = true;
			collisionRectangle.x += speed;			
		}

		if(keyListener.up()) {
			newDirection = 3;
			didMove = true;
			collisionRectangle.y -= speed;			
		}

		if(keyListener.down()) {
			newDirection = 0;
			didMove = true;
			collisionRectangle.y += speed;			
		}

		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}

		if(!didMove) {
			animatedSprite.reset();
		}



		if (didMove) {
			
			collisionRectangle.x += xCollisionOffset;
			collisionRectangle.y += yCollisionOffset;
			
			Rectangle axisCheck = new Rectangle(collisionRectangle.x, playerRectangle.y + yCollisionOffset, collisionRectangle.w, collisionRectangle.h);
			
			//checkXaxis
			if(!game.getMap().checkCollision(axisCheck, layer, game.getXZoom(), game.getYZoom()) &&
					!game.getMap().checkCollision(axisCheck, layer + 1, game.getXZoom(), game.getYZoom())) {
				playerRectangle.x = collisionRectangle.x - xCollisionOffset;
			}
			
			axisCheck.x = playerRectangle.x + xCollisionOffset;
			axisCheck.y = collisionRectangle.y;
			axisCheck.w = collisionRectangle.w;
			axisCheck.h = collisionRectangle.h;
//			axisCheck = new Rectangle(playerRectangle.x, collisionRectangle.y, collisionRectangle.w, collisionRectangle.h);
			
			//checkYaxis
			if(!game.getMap().checkCollision(axisCheck, layer, game.getXZoom(), game.getYZoom()) &&
					!game.getMap().checkCollision(axisCheck, layer + 1, game.getXZoom(), game.getYZoom())) {
				playerRectangle.y = collisionRectangle.y - yCollisionOffset;
			}
			
			animatedSprite.update(game);

		}
		
		updateCamera(game.getRenderer().getCamera());

	}

	public void updateCamera(Rectangle camera) {
		if(playerRectangle.x >= 1000) {
			camera.x = 500;
			if(playerRectangle.x >= 1500) {
				Game.State = Game.STATE.CINFO;
			}
		} else {
			camera.x = playerRectangle.x - (camera.w / 2);
		}
		if(playerRectangle.x <= 32) {
			camera.x = -468;
			if(playerRectangle.x <= -550) {
				Game.State = Game.STATE.MENU;
			}
		} else {
			camera.x = playerRectangle.x - (camera.w / 2);
		}

		//		if(playerRectangle.x >= -488 && playerRectangle.x <= 400 && playerRectangle.y >= -392 && playerRectangle.y <= -268) {
		//			playerRectangle.x = 200;
		//			playerRectangle.y = -200;
		//		}

		//		System.out.println(playerRectangle.x + " " + playerRectangle.y);

		camera.y = -384;

		//		camera.y = playerRectangle.y - (camera.h / 2);

	}

	@Override
	public int getLayer() {
		return layer;
	}


	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

	public void changeSprite(Sprite sprite) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite; 
		}
	}

	@Override
	public Rectangle getRectangle() {
		return playerRectangle;
	}


}
