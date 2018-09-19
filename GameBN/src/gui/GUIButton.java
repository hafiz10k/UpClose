package gui;

import entity.Rectangle;
import entity.Sprite;
import game.Game;
import game.GameObject;
import handler.RenderHandler;

public abstract class GUIButton implements GameObject{

	protected Sprite sprite;
	protected Rectangle rect;
	protected boolean fixed;

	public GUIButton(Sprite sprite, Rectangle rect, boolean fixed) {
		this.sprite = sprite;	
		this.rect = rect;
		this.fixed = fixed; 
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {}

	public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect) {
		renderer.renderSprite(sprite, rect.x + interfaceRect.x, rect.y + interfaceRect.y, xZoom, yZoom, fixed);
	}

	@Override
	public void update(Game game) {}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		if(mouseRectangle.intersects(rect)) {
			activate();
			return true;
		}
		
		return false;
	}

	public abstract void activate();
	
	@Override
	public int getLayer() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public Rectangle getRectangle() {
		return rect;
	}

}
