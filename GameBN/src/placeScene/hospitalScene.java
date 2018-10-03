package placeScene;

import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Player;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.RenderHandler;
import menuComponents.Gender;

public class hospitalScene {

	public Gender gender;
	
	private BufferedImage land;

	public hospitalScene(Game game) {
		// land
		land = game.loadImage("/hospital_scene.png");

	}

	public void update(Game game) {
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom, Game game) {
		renderer.renderImage(land, 10, 150, xZoom, yZoom, true);

	}

}
