package entity;

import java.awt.Graphics;

import game.Game;
import game.GameObject;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Player implements GameObject {

	private Rectangle playerRectangle;
	private Rectangle collisionRectangle;
	
	private int speed = 5;

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

	public void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimationRange(direction * 4, (direction * 4) + 4);
		}
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		 
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



		if(didMove) {


			collisionRectangle.x += xCollisionOffset;
			collisionRectangle.y += yCollisionOffset;

			Rectangle axisCheck = new Rectangle(collisionRectangle.x, playerRectangle.y + yCollisionOffset, collisionRectangle.w, collisionRectangle.h);

			//Check the X axis
			if(!game.getMap().checkCollision(axisCheck, layer, game.getXZoom(), game.getYZoom()) && 
					!game.getMap().checkCollision(axisCheck, layer + 1, game.getXZoom(), game.getYZoom())) {
				playerRectangle.x = collisionRectangle.x - xCollisionOffset;
			}

			axisCheck.x = playerRectangle.x + xCollisionOffset;
			axisCheck.y = collisionRectangle.y;
			axisCheck.w = collisionRectangle.w;
			axisCheck.h = collisionRectangle.h;
			//axisCheck = new Rectangle(playerRectangle.x, collisionRectangle.y, collisionRectangle.w, collisionRectangle.h);

			//Check the Y axis
			if(!game.getMap().checkCollision(axisCheck, layer, game.getXZoom(), game.getYZoom()) && 
					!game.getMap().checkCollision(axisCheck, layer + 1, game.getXZoom(), game.getYZoom())) {
				playerRectangle.y = collisionRectangle.y - yCollisionOffset;
			}


			animatedSprite.update(game);
		}


		updateCamera(game.getRenderer().getCamera());

	}

	public void updateCamera(Rectangle camera) {

		System.out.println("x: " + playerRectangle.x + "\n" + "y: " + playerRectangle.y);
		camera.x = playerRectangle.x - (camera.w / 2);
		camera.y = playerRectangle.y - (camera.h / 2);

		if(camera.x <= -590) {
			camera.x = -590;
			if(playerRectangle.x <= -606) {
				playerRectangle.x = -606;
			}
		}

		if(camera.y <= -1344) {
			camera.y = -1344;
			if(playerRectangle.y <= -1360) {
				playerRectangle.y = -1360;
			}
		}

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