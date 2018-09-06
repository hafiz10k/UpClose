package game;

import entity.Rectangle;
import handler.RenderHandler;

public interface GameObject {
	
	// call every time physically possible
	public void render(RenderHandler renderer, int xZoom, int yZoom);
	
	// call at 60fps rate
	public void update(Game game);
	
	//Call when mouse click on canvas
	//return true if stop checking other clicks
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);

}
