package entity;

import java.awt.image.BufferedImage;

import game.Game;
import game.GameObject;
import handler.RenderHandler;

public class AnimatedSprite extends Sprite implements GameObject{

	private Sprite[] sprites;
	private int currentSprite = 0;
	private int speed;
	private int counter = 0;
	
	private int startSprite =0;
	private int endSprite;
	
	public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {
		sprites = new Sprite[positions.length];
		this.speed = speed;
		this.endSprite = positions.length - 1;
		
		for(int i = 0; i < positions.length; i++) {
			sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].w, positions[i].h);
		}
	}
	
	public AnimatedSprite(SpriteSheet sheet, int speed) {
		sprites = sheet.getLoadedSprites();
		this.speed = speed;
		this.endSprite = sprites.length - 1;
	
	}
	
	//parameter speed - how many frames pass until sprite changes
	public AnimatedSprite(BufferedImage[] images, int speed) {
		sprites = new Sprite[images.length];
		this.speed = speed;
		this.startSprite = images.length - 1;
		
		for(int i = 0; i < images.length; i++) {
			sprites[i] = new Sprite(images[i]);
		}
	}
	
	
	//render is dealt specifically with the layer class
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
	
	@Override
	public void update(Game game) {
		counter++;
		if(counter >= speed) {
			counter = 0;
			incSprite(); //increment sprite
		}
	}
	
	public void reset() {
		counter = 0;
		currentSprite = startSprite;
	}
	
	public void setAnimationRange(int startSprite, int endSprite) {
		this.startSprite = startSprite;
		this.endSprite = endSprite;
		reset();
	}
	
	public int getWidth() {
		return sprites[currentSprite].getWidth();
	}
	
	public int getHeight() {
		return sprites[currentSprite].getHeight();
	}
	
	public int[] getPixels() {
		return sprites[currentSprite].getPixels();
	}
	
	public void incSprite() {
		currentSprite++;
		if(currentSprite >= endSprite) {
			currentSprite = startSprite;
		}
	}

}
